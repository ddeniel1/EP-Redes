package br.com.cinemaja.Model.Object;

import br.com.cinemaja.Controller.CustomerController;

import java.io.Serializable;
import java.util.concurrent.Semaphore;

public class Chair implements Serializable {
    private static final long serialVersionUID = 6485555842676418536L;

    private char letter;
    private int number;
    private boolean available = true;
    private Semaphore mutex = new Semaphore(1,true);
    private CustomerController rentedBy;

    public void setRentedBy(CustomerController rentedBy) {
        this.rentedBy = rentedBy;
    }

    public Chair(char letter, int number) {
        this.letter = letter;
        this.number = number;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable() {
        this.available = !this.available;
    }

    public void getMutex() throws InterruptedException {
        mutex.acquire();
    }

    public void addMutex() {
        this.mutex.release();
    }

    public int getMutexPermits() {
        return mutex.availablePermits();
    }

    @Override
    public String toString() {
        return "" + letter + number;
    }

    @Override
    public boolean equals(Object s) {
        String compare = s.toString();
        return compare.equals("" + letter + number);
    }
}
