package br.com.cinemaja.Model.Object;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Session implements Serializable {
    private static final long serialVersionUID = 2488584742676418536L;
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

    public void removeAvailableChairs() {
        this.availableChairs--;
    }

    public Room getRoom() {
        return room;
    }

    @Override
    public String toString() {
        return "Session: "+
                 name +
                "\navailableChairs: " + availableChairs +
                "\nroom\n" + room.toString() +
                "dateTime\t" + dateTime.format(DateTimeFormatter.ofPattern("dd/MM/uuuu HH:mm"));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Session session = (Session) o;
        return name.equals(session.name) &&
                dateTime.equals(session.dateTime);
    }
}
