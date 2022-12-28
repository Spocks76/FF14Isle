package de.web.spo.ff14.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;

@Getter
public class GrooveValueCycleCombMap {
    private final Integer groove;
    private final TreeMap<Integer, List<CycleComb>> cycleCombTreeMap = new TreeMap<>(Comparator.reverseOrder());
    private final TreeMap<Integer, List<CycleComb>> cycleCombTreeRestMap = new TreeMap<>(Comparator.reverseOrder());

    private final int trimSize;

    public GrooveValueCycleCombMap(Integer groove, int trimSize) {
        this.groove = groove;
        this.trimSize = trimSize;
    }

    public void addCycleComb(CycleComb cycleComb) {
        if(cycleComb.isRest()) {
            cycleCombTreeRestMap.computeIfAbsent(cycleComb.getValue(), ArrayList::new).add(cycleComb);
        } else {
            cycleCombTreeMap.computeIfAbsent(cycleComb.getValue(), ArrayList::new).add(cycleComb);
        }
        this.trim(this.trimSize);
    }

    public void trim(int maxSize) {
        if(cycleCombTreeMap.size()>maxSize) {
            cycleCombTreeMap.remove(cycleCombTreeMap.lastKey());
        }
        if(cycleCombTreeRestMap.size()>maxSize) {
            cycleCombTreeRestMap.remove(cycleCombTreeRestMap.lastKey());
        }
    }
}
