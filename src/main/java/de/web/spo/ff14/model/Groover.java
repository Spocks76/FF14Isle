package de.web.spo.ff14.model;

import lombok.ToString;

import java.util.Arrays;
@ToString
public class Groover {

    private final int[] grooves = new int[24];

    public Groover(int startGroove) {
        Arrays.fill(grooves, startGroove);
    }

    public synchronized void addProduct(int hour) {
        for(var i = hour; i < 24;  i++) {
            if(grooves[i]<35) {
                grooves[i]++;
            }
        }
    }

    public int getGroove(int hour) {
        return grooves[hour];
    }

    public int getLastGroove() {

        return getGroove(23);
    }

}
