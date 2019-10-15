package br.com.cinemaja.Network.Client;

import br.com.cinemaja.Config.Socket;
import br.com.cinemaja.Controller.CustomerController;
import br.com.cinemaja.Model.Object.Session;
import br.com.cinemaja.Network.Server.Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Client extends Thread implements Serializable {
    private static String ip;
    private int hostPort;
    private Socket socket;


    public Client(String ip, int hostPort) {
        this.ip = ip;
        this.hostPort = hostPort;
    }


    public void run() {
        try {
            initClient();
            ObjectOutputStream objectOut;
            objectOut = new ObjectOutputStream(socket.getOutputStream());
            objectOut.writeUTF(ip + "\n" + socket.getLocalPort());
            objectOut.flush();


            CustomerController customerController = new CustomerController("CinemaJA", getSession(socket), this);
            customerController.run();

            objectOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initClient() {
        try {
            socket = new Socket(ip, hostPort);
            socket.setTcpNoDelay(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private Session getSession(Socket socket) {
        try {
            ObjectInputStream objectIn;
            objectIn = new ObjectInputStream(socket.getInputStream());
            Session session = (Session) objectIn.readObject();
            System.out.println(session.toString());
            return session;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }

    }

    public void closeClient() {
        try {
            new ObjectOutputStream(socket.getOutputStream()).writeObject(null);
            socket.shutdownInput();
            socket.shutdownOutput();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "Client{ " + ip +
                ":" + hostPort +
                " }";
    }

    public void updateSession(Session session) {
        try {
            ObjectOutputStream objectOut;
            objectOut = new ObjectOutputStream(socket.getOutputStream());
            objectOut.writeObject(session);
            objectOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
