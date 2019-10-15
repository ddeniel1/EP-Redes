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
        int sizeL = session.getRoom().getMaxChairColl();
        int sizeH = session.getRoom().getMaxChairRaw();
        setSize(sizeL*70, sizeH*50);
        removeAll();
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        chairsList = session.getRoom().getChairs();

        char letter = 'a';
        c.gridx = -1;
        c.gridy = 0;
        for (Chair chair : chairsList) {
            String chairName = chair.toString();
            button = new Button(chairName);
            button.setBackground((chair.getMutexPermits() > 0 ? Color.GREEN : chair.isAvailable() ? Color.yellow : Color.red));
            if (letter == chairName.charAt(0)) {
                c.gridx++;
            } else {
                letter = chairName.charAt(0);
                c.gridx = 0;
                c.gridy++;
            }
            c.fill = GridBagConstraints.HORIZONTAL;
            add(button, c);
            button.addActionListener(this);
        }

        button = new Button("Confirmar compra");
        button.setBackground(Color.BLUE.darker().darker());
        button.setForeground(Color.WHITE);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.PAGE_END;
        c.gridx = 0;
        c.gridy = sizeH;
        c.gridwidth = sizeL;
        add(button, c);
        addWindowListener(this);
        pack();
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
            if (selectedChairs.contains(chair) && chair.getRentedBy().equals(customerController)) {
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
