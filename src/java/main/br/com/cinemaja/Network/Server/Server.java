package br.com.cinemaja.Network.Server;

import br.com.cinemaja.Model.Object.Session;
import br.com.cinemaja.Network.Client.Client;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class Server extends Thread {
    int connectionCount;
    List<Client> clients = new ArrayList<>();
    List<Session> sessions = new ArrayList<>();


    public Server(int port, List<Session> sessions) {
        connectionCount = 0;
        this.sessions = sessions;

        try {
            msg("Server is starting... ");
            ServerSocket ss = new ServerSocket(port);

            while (true) {


                msg("waiting for incoming connections...");
                Socket connection = ss.accept();

                msg("Client connected successfully!");
                connectionCount++;
                clients.add(getClient(connection.getInputStream()));
                clients.forEach(System.out::println);
                connection = ss.accept();
                sendSession(sessions.get(0), connection);
            }
        } catch (IOException e) {
            System.out.println("Unable to listen to port.");
            e.printStackTrace();
        } finally {
        }
    }

    private void sendSession(Session session, Socket connection) throws SocketException {
        String send = "Avengers" + "\n14" + "\n10" + "\n2019-10-10T19:00:00";
        try {
            PrintWriter writer = new PrintWriter(connection.getOutputStream());
            System.out.println(send);
            writer.print(send);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        connection.setKeepAlive(true);

    }

    private Client getClient(InputStream inputStream) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        try {
            Client client = new Client(reader.readLine().trim(), Integer.parseInt(reader.readLine().trim()));
            reader.close();
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
}
