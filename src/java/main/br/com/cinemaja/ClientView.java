package br.com.cinemaja;

import br.com.cinemaja.Controller.CustomerController;
import br.com.cinemaja.Object.Chair;
import br.com.cinemaja.Object.Session;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.List;

public class ClientView extends Frame implements WindowListener, ActionListener {



    TextField text = new TextField(20);
    protected CustomerController customerController;
    Button button;
    private Session session;
    private List<Chair> chairsList;
    private List<Chair> selectedChairs;
    private boolean online = false;

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

    private void displayButtons() {
        setSize(900, 600);
        removeAll();

        setLayout(new FlowLayout());
        addWindowListener(this);
        chairsList = session.getRoom().getChairs();
//        System.out.println(chairsList.size());

        chairsList.forEach(chair -> {
            String chairName = chair.toString();
            button = new Button(chairName);
            button.setSize(10, 10);
            button.setBackground((chair.isAvailable() ? Color.GREEN : Color.red));
            add(button);
            button.addActionListener(this);
        });
        button = new Button("executar");
        button.setSize(10, 10);
        button.setBackground(Color.MAGENTA);
        add(button);
        button.addActionListener(this);
        setVisible(true);


    }

    public void actionPerformed(ActionEvent e) {
        input(e.getActionCommand());
    }

    private void input(String actionCommand) {
        if (actionCommand.equals("executar")) online = false;
        else {
            online = true;
            Chair chair = session.getRoom().searchChair(actionCommand);
            if (selectedChairs.contains(chair)) {
                selectedChairs.remove(chair);
                button = new Button(chair.toString());
                button.setBackground(Color.GREEN);
                add(button);
                setVisible(true);
            } else {
                customerController.getAChair(chair);
                selectedChairs.add(chair);
            }
            displayButtons();
        }

    }

    public void windowClosing(WindowEvent e) {
        dispose();
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
}
