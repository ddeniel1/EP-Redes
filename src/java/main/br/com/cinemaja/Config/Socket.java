package br.com.cinemaja.Config;

import java.io.IOException;
import java.io.Serializable;
import java.net.*;

public class Socket extends java.net.Socket implements Serializable {
    public Socket() {
    }

    public Socket(Proxy proxy) {
        super(proxy);
    }

    public Socket(SocketImpl impl) throws SocketException {
        super(impl);
    }

    public Socket(String host, int port) throws IOException {
        super(host, port);
    }

    public Socket(InetAddress address, int port) throws IOException {
        super(address, port);
    }

    public Socket(String host, int port, InetAddress localAddr, int localPort) throws IOException {
        super(host, port, localAddr, localPort);
    }

    public Socket(InetAddress address, int port, InetAddress localAddr, int localPort) throws IOException {
        super(address, port, localAddr, localPort);
    }
}
