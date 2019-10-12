package br.com.cinemaja;

import br.com.cinemaja.Controller.CustomerController;
import br.com.cinemaja.Model.Object.Chair;
import br.com.cinemaja.Model.Object.Session;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.List;

public class ClientView extends Frame implements WindowListener, ActionListener {



    protected CustomerController customerController;
    Button button;
    private Session session;
    private List<Chair> chairsList;
    private List<Chair> selectedChairs;
    private boolean online = false;
    private boolean closed = false;

    public boolean isClosed() {
        return closed;
    }

    public ClientView(String title) {
        super(title);
    }

    public static void main(String[] args) {

    }

    public List<Chair> run(CustomerController customerController) {
        this.customerController = customerController;
        displayButtons();
        selectedChairs = new ArrayList<>();
        online = true;
        return selectedChairs;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public void displayButtons() {
        setSize(900, 600);
        removeAll();

        setLayout(new FlowLayout());
        addWindowListener(this);
        chairsList = session.getRoom().getChairs();


        chairsList.forEach(chair -> {
            String chairName = chair.toString();
            button = new Button(chairName);
            button.setSize(10, 10);
            button.setBackground((chair.getMutexPermits() > 0 ? Color.GREEN : chair.isAvailable() ? Color.yellow : Color.red));
            add(button);
            button.addActionListener(this);
        });
        button = new Button("Confirmar compra");
        button.setSize(10, 10);
        button.setBackground(Color.BLUE.darker().darker());
        button.setForeground(Color.WHITE);
        add(button);
        button.addActionListener(this);
        setVisible(true);


    }

    public void actionPerformed(ActionEvent e) {
        input(e.getActionCommand());
    }

    private void input(String actionCommand) {
        if (actionCommand.equals("Confirmar compra")) online = false;
        else {
            online = true;
            Chair chair = session.getRoom().searchChair(actionCommand);
            if (selectedChairs.contains(chair)) {
                selectedChairs.remove(chair);
                customerController.returnAChair(chair);
            } else {
                customerController.getAChair(chair);
                selectedChairs.add(chair);
            }
            displayButtons();
        }

    }

    public void windowClosing(WindowEvent e) {
        dispose();
        closed = true;
    }

    public void windowOpened(WindowEvent e) {
    }

    public void windowActivated(WindowEvent e) {
    }

    public void windowIconified(WindowEvent e) {
    }

    public void windowDeiconified(WindowEvent e) {
    }

    public void windowDeactivated(WindowEvent e) {
    }

    public void windowClosed(WindowEvent e) {
    }


    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean b) {
        online = b;
    }
}
