package br.com.cinemaja.Object;

import java.util.ArrayList;

public class Room {

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

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("\tSala ").append(name).append("\n\n");
        int aux=0;
        for (int i = 0; i < maxChairRaw; i++) {
            for (int k = 0; k < maxChairColl; k++) {
                s.append(chairs.get(aux)).append('\t');
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
