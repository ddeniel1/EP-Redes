package br.com.cinemaja;


import br.com.cinemaja.Model.Object.Session;
import br.com.cinemaja.Network.Client.Client;
import br.com.cinemaja.Network.Server.Server;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class AppStarter{
    public static void main(String[] args) {
        if (args[0].equals("servidor")) initializeServer();
        else if (args[0].equals("client")) initializeClient();
        else
            System.out.println("Inicie o servidor digitando servidor como parametro ou cliente digitando client como parametro.");
    }

    private static void initializeClient() {
        Client client = new Client("127.0.0.1", 3000);
        client.run();
    }

    private static void initializeServer() {
        Session sessao = new Session("Avengers", 5, 5, LocalDateTime.parse("2019-10-10T19:00:00"));
        List<Session> sessionsList = new ArrayList<>();
        sessionsList.add(sessao);
        new Server(3000, sessionsList).start();
        System.out.println(sessao.toString());

    }

    private static void msg(String s) {
        System.out.println(s);
    }


}
