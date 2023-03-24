package de.web.spo.ff14.model;

import lombok.Getter;
import lombok.Setter;

@Getter
public class SupplyCount {
    private final int supply;
    private int count = 0;
    @Setter
    private int probability;

    public SupplyCount(int supply) {
        this.supply = supply;
    }

    public void inc() {
        count++;
    }

    public void add(int count) {
        this.count+=count;
    }

}
