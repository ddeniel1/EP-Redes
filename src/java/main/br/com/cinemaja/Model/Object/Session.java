package br.com.cinemaja.Model.Object;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Session {

    private String name;
    private int availableChairs;
    private Room room;
    private LocalDateTime dateTime;

    public Session(String name, int roomColl, int roomRaw, LocalDateTime dateTime) {
        this.name = name;
        this.availableChairs = roomColl*roomRaw;
        this.dateTime = dateTime;
        this.room = new Room(name,roomRaw,roomColl);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addAvailableChairs(int availableChairs) {
        this.availableChairs += availableChairs;
    }
    public void removeAvailableChairs(int availableChairs) {
        this.availableChairs -= availableChairs;
    }
    public void removeAvailableChairs() {
        this.availableChairs--;
    }

    public String getName() {
        return name;
    }

    public int getAvailableChairs() {
        return availableChairs;
    }

    public Room getRoom() {
        return room;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    @Override
    public String toString() {
        return "Session: "+
                 name +
                "\navailableChairs: " + availableChairs +
                "\nroom\n" + room.toString() +
                "dateTime\t" + dateTime.format(DateTimeFormatter.ofPattern("dd/MM/uuuu HH:mm"));
    }
}
