package br.com.cinemaja.Controller;

import br.com.cinemaja.ClientView;
import br.com.cinemaja.Object.Chair;
import br.com.cinemaja.Object.Customer;
import br.com.cinemaja.Object.Session;

import javax.management.timer.Timer;
import java.util.List;

public class CustomerController extends Customer {

    ClientView view;
    private List<Chair> chairList;

    public CustomerController(String nome, Session session) {
        super(nome);
        view = new ClientView("CinemaJA");
        view.setSession(session);
        chairList = view.run(this);
        while (view.isOnline()) {
            try {
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        chairList.forEach(chair -> getAChair(chair));
        proceedToCheckout(session);

    }

    public boolean getAChair(Chair chair) {
        if (!chair.isAvailable()) return false;
        try {
            chair.getMutex();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        chair.setAvailable();
        chair.setRentedBy(super.getThis());
        return true;
    }

    private void proceedToCheckout(Session session) {
        boolean accepted = false;
        long clock = System.currentTimeMillis() + Timer.ONE_MINUTE * 10;
        while (System.currentTimeMillis() < clock && !accepted) {
            accepted = !view.isOnline();
            try {
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        session.removeAvailableChairs();
    }
}
