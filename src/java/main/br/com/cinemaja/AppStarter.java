package br.com.cinemaja;


import br.com.cinemaja.Object.Session;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class AppStarter{
    public static void main(String[] args) {
        if (args[0].equals("server")) initializeServer();
    }

    private static void initializeServer() {
        List<Session> sessionsList = new ArrayList<>();
        Session sessao = new Session("Avengers",14,10, LocalDateTime.parse("2019-10-10T19:00:00"));
        sessionsList.add(sessao);
        sessionsList.forEach(System.out::println);
    }

    private static void msg(String s) {
        System.out.println(s);
    }


}
