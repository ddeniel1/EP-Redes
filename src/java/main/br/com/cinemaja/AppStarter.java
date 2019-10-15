package br.com.cinemaja;


import br.com.cinemaja.Model.Object.Session;
import br.com.cinemaja.Network.Client.Client;
import br.com.cinemaja.Network.Server.Server;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class AppStarter{
    public static void main(String[] args) {
        if (args[0].equals("server")) initializeServer();
        else initializeClient();
    }

    private static void initializeClient() {
        Client client = new Client("127.0.0.1", 3000);
        client.run();
        // CustomerController customer = new CustomerController("Paulo", new Session("Avengers", 14, 10, LocalDateTime.parse("2019-10-10T19:00:00")));


    }

    private static void initializeServer() {
        Session sessao = new Session("Avengers",14,10, LocalDateTime.parse("2019-10-10T19:00:00"));
        List<Session> sessionsList = new ArrayList<>();
        sessionsList.add(sessao);
        new Server(3000, sessionsList).start();
//        sessionsList.forEach(System.out::println);

//        CustomerController client = new CustomerController("Paulo", sessao);

//        ClientView cv = new ClientView("CinemaJA");
//        cv.setSession(sessao);
//        cv.run();

        System.out.println(sessao.toString());

    }

    private static void msg(String s) {
        System.out.println(s);
    }


}
