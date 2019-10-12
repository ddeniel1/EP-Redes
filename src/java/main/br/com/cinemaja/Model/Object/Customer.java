package br.com.cinemaja.Model.Object;

import br.com.cinemaja.Network.Client.Client;

import java.util.ArrayList;
import java.util.List;

public class Customer extends Thread {
    private String nome;
    private List<Session> sessionsAttending = new ArrayList<>();
    protected final Client id;

    public Customer(String nome, Client id) {
        this.nome = nome;
        this.id = id;
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
