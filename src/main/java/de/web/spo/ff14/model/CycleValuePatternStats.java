package de.web.spo.ff14.model;

import lombok.Getter;

import java.util.Hashtable;
import java.util.Map;

public class CycleValuePatternStats {
    @Getter
    private final String patternKey;
    private int count = 0;
    private final Map<Supply, Integer> supplyCounterMap = new Hashtable<>(Map.of(Supply.NONEXISTENT, 0, Supply.INSUFFICIENT, 0, Supply.SUFFICIENT, 0));

    public CycleValuePatternStats(String patternKey) {
        this.patternKey = patternKey;
    }

    public void addPattern(Supply supply) {
        count++;
        supplyCounterMap.merge(supply, 1, Integer::sum);
    }

    public int getPercentage(Supply supply) {
        if(count>0) {
            return supplyCounterMap.getOrDefault(supply, 0) * 100 / count;
        }
        return 0;
    }

    public void setCount(int count) {
        if(count>this.count) {
            if(supplyCounterMap.get(Supply.INSUFFICIENT) == 0) {
                this.supplyCounterMap.put(Supply.INSUFFICIENT, count-this.count);
            }
            this.count = count;
        }
    }
}
