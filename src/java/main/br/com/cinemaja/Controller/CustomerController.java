package br.com.cinemaja.Controller;

import br.com.cinemaja.Object.Chair;
import br.com.cinemaja.Object.Customer;
import br.com.cinemaja.Object.Session;

import java.util.Timer;

public class CustomerController extends Customer {

    public CustomerController(String nome) {
        super(nome);
    }

    public boolean getAChair(Session session, Chair chair){
        if (!chair.isAvailable()) return false;
        try {
            chair.getMutex();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        proceedToCheckout(session);
        chair.setAvailable();
        chair.setRentedBy(super.getThis());
        return true;
    }

    private void proceedToCheckout(Session session) {
        Timer clock = new Timer();
//        javax.swing.Timer

        session.removeAvailableChairs();
    }
}
