package de.web.spo.ff14.model;

import lombok.Getter;

import java.util.Comparator;
import java.util.TreeMap;

@Getter
public class GrooveValueAgendaMap {
    private final Integer groove;
    private final TreeMap<Integer, ValueAgendaCountMap> valueAgendaCountMap = new TreeMap<>(Comparator.reverseOrder());

    private final int trimSize;

    public GrooveValueAgendaMap(Integer groove, int trimSize) {
        this.groove = groove;
        this.trimSize = trimSize;
    }

    public void addAgenda(Agenda agenda) {
        valueAgendaCountMap.computeIfAbsent(agenda.getValue(), ValueAgendaCountMap::new).addAgenda(agenda);
        this.trim(this.trimSize);
    }

    public void trim(int maxSize) {
        if(valueAgendaCountMap.size()>maxSize) {
            valueAgendaCountMap.remove(valueAgendaCountMap.lastKey());
        }
    }
}
