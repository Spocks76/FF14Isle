package de.web.spo.ff14.model;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class CycleCombStats {

    private CycleComb cycleComb;
    private CycleComb minCycleComb;
    private CycleComb maxCycleComb;
    private long countCycleComb = 0;
    private long countMinCycleComb = 0;
    private long countMaxCycleComb = 0;
    private long sumAvgCycleCombValue = 0;

    public synchronized void addCycleComb(CycleComb cycleComb) {
        if(this.cycleComb == null) {
            this.cycleComb = cycleComb;
        }
        minCycleComb = minCycleComb == null ? cycleComb : minCycleComb.getMinAgendaCombStatsSum() > cycleComb.getMinAgendaCombStatsSum() ? cycleComb : minCycleComb;
        maxCycleComb = maxCycleComb == null ? minCycleComb : maxCycleComb.getMaxAgendaCombStatsSum() < cycleComb.getMaxAgendaCombStatsSum() ? minCycleComb : maxCycleComb;
        countCycleComb++;
        sumAvgCycleCombValue+=cycleComb.getAvgAgendaCombStatsSum();
        if(cycleComb.getMinAgendaCombStatsSum() ==  minCycleComb.getMinAgendaCombStatsSum()) {
            countMinCycleComb++;
        }
        if(cycleComb.getMaxAgendaCombStatsSum() == maxCycleComb.getMaxAgendaCombStatsSum()) {
            countMaxCycleComb++;
        }
    }

    public int getAvgValue() {
        return (int) (sumAvgCycleCombValue / countCycleComb);
    }
}

