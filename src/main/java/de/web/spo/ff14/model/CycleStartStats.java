package de.web.spo.ff14.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.Map;

@Getter
public class CycleStartStats {

    private final String agendaCombKey;
    private final AgendaCombStats startAgendaCombStats = new AgendaCombStats();
    private final Map<String, CycleCombStats> cycleCombStatsMap = new Hashtable<>();

    public CycleStartStats(String agendaCombKey) {
        this.agendaCombKey = agendaCombKey;
    }

    public synchronized void addCycleComb(AgendaComb startAgendaComb, CycleComb cycleComb) {
        if(startAgendaComb!=null) {
            startAgendaCombStats.addAgendaComb(startAgendaComb);
        }
        cycleCombStatsMap.computeIfAbsent(cycleComb.getKey(), cycleCombKey -> new CycleCombStats()).addCycleComb(cycleComb);
    }

    public int getMaxValue() {
        return new ArrayList<>(cycleCombStatsMap.values()).stream().max(Comparator.comparing(CycleCombStats::getMaxValue)).orElse(new CycleCombStats()).getMaxValue();
    }

    public int getAvgValue() {
        return (int) cycleCombStatsMap.values().stream().mapToInt(CycleCombStats::getMaxValue).average().orElse(0);
    }

    public void truncMap(int truncSize) {
        if(cycleCombStatsMap.size() > truncSize) {
            var cnt = cycleCombStatsMap.size() - truncSize;
            new ArrayList<>(cycleCombStatsMap.values()).stream().sorted(Comparator.comparing(CycleCombStats::getMaxValue))
                    .limit(cnt).forEach(cycleCombStats -> cycleCombStatsMap.remove(cycleCombStats.getCycleComb().getKey()));
        }
    }

}
