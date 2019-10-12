package br.com.cinemaja.Network.Client;

import br.com.cinemaja.Controller.CustomerController;
import br.com.cinemaja.Model.Object.Session;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDateTime;

public class Client extends Thread {
    private static String ip;
    private int hostPort;

    public Client(String ip, int hostPort) {
        this.ip = ip;
        this.hostPort = hostPort;
    }


    public void run() {
        try {
            Socket socket = new Socket(ip, hostPort);
            PrintWriter writer = new PrintWriter(socket.getOutputStream());

            writer.print(ip + "\n" + socket.getLocalPort());
            writer.flush();
            writer.close();
            socket = new Socket(ip, hostPort);


            CustomerController customerController = new CustomerController("CinemaJA", getSession(socket), this);
            socket.close();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Session getSession(Socket socket) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Session session = new Session(reader.readLine().trim(), Integer.parseInt(reader.readLine().trim()),
                    Integer.parseInt(reader.readLine().trim()), LocalDateTime.parse(reader.readLine().trim()));
            System.out.println(session.toString());
            reader.close();
            return session;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public String toString() {
        return "Client{" + ip +
                " hostPort=" + hostPort +
                '}';
    }
}
