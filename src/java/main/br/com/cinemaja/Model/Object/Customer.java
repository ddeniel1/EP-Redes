package br.com.cinemaja.Model.Object;

import br.com.cinemaja.Network.Client.Client;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Customer extends Thread implements Serializable {
    private static final long serialVersionUID = -3485746983676418536L;
    private String nome;
    private List<Session> sessionsAttending = new ArrayList<>();
    protected final Client id;

    public Customer(String nome, Client id) {
        this.nome = nome;
        this.id = id;
    }
}
