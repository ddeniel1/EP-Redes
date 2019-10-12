package br.com.cinemaja.Object;

import java.util.ArrayList;
import java.util.List;

public class Customer extends Thread {
    private String nome;
    private List<Session> sessionsAttending = new ArrayList<>();

    public Customer(String nome) {
        this.nome = nome;
    }
    public Customer getThis(){
        return this;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Session> getSessionsAttending() {
        return sessionsAttending;
    }

    public void addSessionsAttending(Session session) {
        this.sessionsAttending.add(session);
    }

}
