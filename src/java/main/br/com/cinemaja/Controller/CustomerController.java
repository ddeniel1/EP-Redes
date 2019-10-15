package br.com.cinemaja.Controller;

import br.com.cinemaja.ClientView;
import br.com.cinemaja.Model.Object.Chair;
import br.com.cinemaja.Model.Object.Customer;
import br.com.cinemaja.Model.Object.Session;
import br.com.cinemaja.Network.Client.Client;

import javax.management.timer.Timer;
import java.util.List;

public class CustomerController extends Customer {
    private static final long serialVersionUID = 5224128923137706204L;

    private ClientView view;
    private List<Chair> chairList;
    private Session session;

    public CustomerController(String nome, Client id) {
        super(nome, id);
    }

    public CustomerController(String nome, Session session, Client id) {
        super(nome, id);
        view = new ClientView("CinemaJA");
        this.session = session;

    }

    @Override
    public void run() {
        super.run();
        view.setSession(session);
        chairList = view.run(this);
        while (!view.isClosed()) {
            try {
                sleep(100);
                if (!view.isOnline()) proceedToCheckout(session, chairList);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        super.id.closeClient();
    }

    public boolean getAChair(Chair chair) {
        if (!chair.isAvailable()) return false;
        try {
            chair.getMutex();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean returnAChair(Chair chair) {
        if (chair.isAvailable() && chair.getMutexPermits() > 0) return false;
        chair.addMutex();
       // if (!chair.isAvailable()) chair.setAvailable();
        return true;
    }

    private void proceedToCheckout(Session session, List<Chair> chairList) {
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
        if (accepted) chairList.forEach(chair -> {
            if (chair.isAvailable()) {
                chair.setAvailable();
                chair.setRentedBy(this);
                session.removeAvailableChairs();
            }
        });
        else {
            chairList.forEach(chair -> {
                chair.addMutex();
            });
        }
        super.id.updateSession(session);
        view.setOnline(true);
        view.displayButtons();

    }

    public void setCurrentSession(Session session) {
        super.id.updateSession(session);
        this.session=session;
        view.setSession(session);
        view.setOnline(true);
        view.displayButtons();
    }

    public void updateSessions() {
        id.updateSession(session);
        view.setOnline(true);
        view.displayButtons();
    }
}
