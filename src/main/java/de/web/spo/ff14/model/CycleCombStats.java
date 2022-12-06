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

    private CycleComb getMinCycleComb(CycleComb cycleComb1, CycleComb cycleComb2) {
        if(cycleComb1 == null) {
            return cycleComb2;
        }
        if(cycleComb2 == null) {
            return cycleComb1;
        }
        if(cycleComb1.getMinAgendaCombStatsSum() <= cycleComb2.getMinAgendaCombStatsSum()) {
            return cycleComb1;
        }
        return cycleComb2;
    }

    private CycleComb getMaxCycleComb(CycleComb cycleComb1, CycleComb cycleComb2) {
        if(cycleComb1 == null) {
            return cycleComb2;
        }
        if(cycleComb2 == null) {
            return cycleComb1;
        }
        if(cycleComb1.getMaxAgendaCombStatsSum() >= cycleComb2.getMaxAgendaCombStatsSum()) {
            return cycleComb1;
        }
        return cycleComb2;
    }

    public synchronized void addCycleComb(CycleComb cycleComb) {
        if(this.cycleComb == null) {
            this.cycleComb = cycleComb;
        }
        minCycleComb = getMinCycleComb(minCycleComb, cycleComb);
        maxCycleComb = getMaxCycleComb(maxCycleComb, cycleComb);
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

    public int getMaxValue() {
        return cycleComb.getMaxAgendaCombStatsSum();
    }
}

