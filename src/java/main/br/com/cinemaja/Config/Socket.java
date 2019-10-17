package br.com.cinemaja.Config;

import java.io.IOException;
import java.io.Serializable;

public class Socket extends java.net.Socket implements Serializable {
    private static final long serialVersionUID = 5885668425418536L;

    public Socket(String host, int port) throws IOException {
        super(host, port);
    }

}
