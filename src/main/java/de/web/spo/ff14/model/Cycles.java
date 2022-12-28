package de.web.spo.ff14.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;


public class Cycles {
    @Getter
    private final int startCycle;
    private final List<Cycle> cycles;

    public Cycles(int startCycle) {
        this.startCycle = startCycle;
        cycles = new ArrayList<>(8-startCycle);
        for(var i=startCycle; i<8; i++) {
            cycles.add(new Cycle(i));
        }

    }

    public Cycle getCycle(int cycle) {
        if(cycle<startCycle || cycle>7) {
            throw new IllegalArgumentException("Cycle out of bounds " + cycle);
        }
        return cycles.get(cycle-startCycle);
    }

}
