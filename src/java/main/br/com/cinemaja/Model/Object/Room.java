package br.com.cinemaja.Model.Object;

import java.io.Serializable;
import java.util.ArrayList;

public class Room implements Serializable {

    private String name;
    private int maxChairRaw;
    private int maxChairColl;
    private ArrayList<Chair> chairs = new ArrayList<>();

    public Room(String name, int maxChairRaw, int maxChairColl) {
        this.name = name;
        this.maxChairRaw = maxChairRaw;
        this.maxChairColl = maxChairColl;
        arrangeChairsInRoom(maxChairColl, maxChairRaw);
    }

    private void arrangeChairsInRoom(int maxChairCollum, int maxChairRaw) {
        Chair chair;
        for (int i = 0; i < maxChairRaw; i++)
            for (int k = 0; k < maxChairCollum; k++) {
                chair = new Chair((char) ('a' + i), k + 1);
                chairs.add(chair);
            }
    }

    public ArrayList<Chair> getChairs() {
        return chairs;
    }

    public Chair searchChair(String chairCode) {
        final Chair[] chair1 = new Chair[1];
        chairs.forEach(chair -> {
            if (chair.equals(chairCode)) chair1[0] = chair;
        });
        return chair1[0];
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("\tSala ").append(name).append("\n\n");
        int aux=0;
        for (int i = 0; i < maxChairRaw; i++) {
            for (int k = 0; k < maxChairColl && aux < chairs.size(); k++) {
                s.append(chairs.get(aux).isAvailable() ? chairs.get(aux) : "").append('\t');
                aux++;
            }
            s.append('\n');
        }
        return s.toString();
    }

    private void msg(String s) {
        System.out.println(s);
    }
}
