package br.com.cinemaja.Network.Server;

import br.com.cinemaja.Model.Object.Session;
import br.com.cinemaja.Network.Client.Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server extends Thread implements Serializable {
    static int connectionCount;
    static List<Client> clients = new ArrayList<>();
    static List<Session> sessions;
    private Socket socket;


    public Server(int port, List<Session> sessions) {
        connectionCount = 0;
        this.sessions = sessions;

        try {
            msg("Server is starting... ");
            ServerSocket ss = new ServerSocket(port);

            while (true) {
                msg("waiting for incoming connections...");
                Socket connection = ss.accept();
                new Server(connection).start();
            }
        } catch (IOException e) {
            System.out.println("Unable to listen to port.");
            e.printStackTrace();
        } finally {
        }
    }

    public Server(Socket socket) {
        this.socket = socket;
        try {
            this.socket.setTcpNoDelay(true);
            msg("Client connected successfully: " + socket.getInetAddress() + ":" + socket.getPort());
            connectionCount++;
            msg("Clientes conectados: " + connectionCount);
            run();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            clients.add(getClient());
            sendSession(sessions.get(0));
            Session session;
            while ((session = (Session) new ObjectInputStream(socket.getInputStream()).readObject()) != null) {
                sessionAtualization(session);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void sessionAtualization(Session session) throws IOException {

            sessions.set(sessions.indexOf(session), session);



    }

    private void sendSession(Session session) throws IOException {
        ObjectOutputStream objectOut;
        objectOut = new ObjectOutputStream(socket.getOutputStream());
        objectOut.writeObject(session);
        objectOut.flush();

    }

    private Client getClient() {
        try {
            ObjectInputStream objectIn;
            objectIn = new ObjectInputStream(socket.getInputStream());
            String[] get = objectIn.readUTF().split("\n");
            Client client = new Client(get[0], Integer.parseInt(get[1]));
            return client;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void msg(String s) {
        System.out.println(s);
    }

    public void addSession(Session sessao) {
        sessions.add(sessao);
    }

    public void close() {
        try {
            socket.close();
            connectionCount--;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
