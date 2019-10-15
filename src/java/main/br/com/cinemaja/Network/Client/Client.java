package br.com.cinemaja.Network.Client;

import br.com.cinemaja.Config.Socket;
import br.com.cinemaja.Controller.CustomerController;
import br.com.cinemaja.Model.Object.Session;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Client extends Thread implements Serializable {
    private static final long serialVersionUID = -3411466216776411404L;

    private static String ip;
    private int hostPort;
    private static Socket socket;
    private static CustomerController customerController;


    public Client(String ip, int hostPort) {
        this.ip = ip;
        this.hostPort = hostPort;
    }

    @Override
    public void run() {
        if (this.getName().equals("listener")) {
            while (!socket.isInputShutdown()){
                try {
                    customerController.setCurrentSession((Session)new ObjectInputStream(socket.getInputStream()).readObject());
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } else {
            try {
                initClient();
                ObjectOutputStream objectOut;
                objectOut = new ObjectOutputStream(socket.getOutputStream());
                objectOut.writeUTF(ip + "\n" + socket.getLocalPort());
                objectOut.flush();

                Client thread = new Client(ip,hostPort);


                customerController = new CustomerController("CinemaJA", getSession(socket), this);
                customerController.run();
                thread.setName("listener");
                thread.run();
                objectOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
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
