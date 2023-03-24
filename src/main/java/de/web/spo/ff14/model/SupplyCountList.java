package de.web.spo.ff14.model;

import lombok.Getter;

import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;

public class SupplyCountList {

    private int count = 0;

    private final TreeMap<Integer, SupplyCount> supplyCountMap = new TreeMap<>();

    @Getter
    private SupplyCount medianSupply;

    public void addSupply(int supply) {
        supplyCountMap.computeIfAbsent(supply, SupplyCount::new).inc();
        count++;
    }

    public void addSupply(int supply, int count) {
        supplyCountMap.computeIfAbsent(supply, SupplyCount::new).add(count);
        this.count+=count;
    }

    public SupplyCount getMinSupply() {
        return supplyCountMap.firstEntry().getValue();
    }

    public SupplyCount getMaxSupply() {
        return supplyCountMap.lastEntry().getValue();
    }

    public void calculateProbabilities() {
        if(this.count == 0) {
            return;
        }
        AtomicInteger count = new AtomicInteger();
        supplyCountMap.values().forEach(supplyCount -> {
            count.addAndGet(supplyCount.getCount());
            var prob = count.get() * 100 / this.count;
            if(prob >= 50 && medianSupply == null) {
                medianSupply = supplyCount;
            }
            supplyCount.setProbability(prob);
        });
    }
}
