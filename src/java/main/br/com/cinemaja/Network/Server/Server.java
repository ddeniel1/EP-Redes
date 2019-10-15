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
    private static List<Socket> socketList = new ArrayList<>();


    public Server(int port, List<Session> sessions) {
        connectionCount = 0;
        this.sessions = sessions;

        try {
            msg("Servidor inicializando... ");
            ServerSocket ss = new ServerSocket(port);

            while (true) {
                msg("Esperando conexoes...");

                new Server(ss.accept()).start();

//                this.start();
            }
        } catch (IOException e) {
            System.out.println("Nao foi possivel conectar-se com o cliente");
            e.printStackTrace();
        }
    }

    public Server(Socket socket) {
        this.socket = socket;
        socketList.add(this.socket);
        try {
            this.socket.setTcpNoDelay(true);
            msg("Client conectado com sucesso: " + socket.getInetAddress() + ":" + socket.getPort());
            connectionCount++;
            msg("Clientes conectados: " + connectionCount);
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
            while (!socket.isInputShutdown() && (session = (Session) new ObjectInputStream(socket.getInputStream()).readObject()) != null) {
                sessionAtualization(session);
            }
            close();
            this.finalize();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private void sessionAtualization(Session session) throws IOException {

            sessions.set(sessions.indexOf(session), session);

        socketList.stream().filter(socket1 -> !socket1.isOutputShutdown()).forEach(socket1 -> {
            try {
                new ObjectOutputStream(socket1.getOutputStream()).writeObject(session);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });



    }

    private void sendSession(Session session) throws IOException {
        ObjectOutputStream objectOut;
        if (!socket.isOutputShutdown())objectOut = new ObjectOutputStream(socket.getOutputStream());
        else return;
        objectOut.writeObject(session);
        objectOut.flush();
    }

    private Client getClient() {
        try {
            ObjectInputStream objectIn;
            if (!socket.isInputShutdown())objectIn = new ObjectInputStream(socket.getInputStream());
            else return null;
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
            if (!socket.isInputShutdown())socket.shutdownInput();
            if (!socket.isOutputShutdown())socket.shutdownOutput();
            connectionCount--;
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
