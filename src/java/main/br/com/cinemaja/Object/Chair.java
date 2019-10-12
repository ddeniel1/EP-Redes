package br.com.cinemaja.Object;

import java.util.concurrent.Semaphore;

public class Chair {

    private char letter;
    private int number;
    private boolean available = true;
    private Semaphore mutex = new Semaphore(1,true);
    private Customer rentedBy;

    public Customer getRentedBy() {
        return rentedBy;
    }

    public void setRentedBy(Customer rentedBy) {
        this.rentedBy = rentedBy;
    }

    public Chair(char letter, int number) {
        this.letter = letter;
        this.number = number;
    }

    public char getLetter() {
        return letter;
    }
    
    public int getNumber() {
        return number;
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

    public void setMutex(Semaphore mutex) {
        this.mutex.release();
    }

    @Override
    public String toString() {
        return  available?""+ letter + number:"\t";
    }
}
