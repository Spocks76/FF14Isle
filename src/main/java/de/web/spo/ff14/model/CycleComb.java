package de.web.spo.ff14.model;

import lombok.Getter;
import lombok.ToString;

import java.util.*;
import java.util.stream.Collectors;

@Getter
@ToString
public class CycleComb {
    private final Map<Product, CycleValueStatsList> cycleValuePatternMap;
    private Map<Product, Integer> productCountMap;

    private final AgendaCombStats[] agendaCombStatsCycles = new AgendaCombStats[7];

    private String key;

    private int minAgendaCombStatsSum = 0;

    private int avgAgendaCombStatsSum = 0;
    private int maxAgendaCombStatsSum = 0;

    public CycleComb(Map<Product, CycleValueStatsList> cycleValuePatternMap, Map<Product, Integer> productCountMap) {
        this.cycleValuePatternMap = cycleValuePatternMap;
        this.productCountMap = new Hashtable<>(productCountMap);
    }

    public void addAgendaCombStats(int cycle, AgendaCombStats agendaCombStats) {
        agendaCombStatsCycles[cycle - 1] = agendaCombStats;
        minAgendaCombStatsSum += agendaCombStats.getMinValue();
        avgAgendaCombStatsSum += agendaCombStats.getAvgValue();
        maxAgendaCombStatsSum += agendaCombStats.getMaxValue();
        this.productCountMap = agendaCombStats.getAgendaComb().productCountMap();
        this.key = Arrays.stream(agendaCombStatsCycles).map(agendaCombStats1 -> Optional.ofNullable(agendaCombStats1).orElse(new AgendaCombStats()).getKey()).collect(Collectors.joining("| "));
    }
}
