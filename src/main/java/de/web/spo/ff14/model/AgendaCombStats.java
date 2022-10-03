package de.web.spo.ff14.model;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class AgendaCombStats {

    private AgendaComb agendaComb;
    private AgendaComb minAgendaComb;
    private AgendaComb maxAgendaComb;
    private long countAgendaComb=0;

    private long countMinAgendaComb=0;

    private long countMaxAgendaComb=0;
    private long sumAgendaCombValue=0;

    public synchronized void addAgendaComb(AgendaComb agendaComb) {
        if(this.agendaComb == null) {
            this.agendaComb = agendaComb;
        }
        minAgendaComb = minAgendaComb == null ? agendaComb : minAgendaComb.getValue() > agendaComb.getValue() ? agendaComb : minAgendaComb;
        maxAgendaComb = maxAgendaComb == null ? agendaComb : maxAgendaComb.getValue() < agendaComb.getValue() ? agendaComb : maxAgendaComb;
        countAgendaComb++;
        sumAgendaCombValue+=agendaComb.getValue();
        if(agendaComb.getValue() == minAgendaComb.getValue()) {
            countMinAgendaComb++;
        }
        if(agendaComb.getValue() == maxAgendaComb.getValue()) {
            countMaxAgendaComb++;
        }
    }

    public String getKey() {
        return agendaComb == null ? "F, " : agendaComb.getKey();
    }

    public int getAvgValue() {
        return (int) (sumAgendaCombValue / countAgendaComb);
    }

    public int getMinValue() {
        return minAgendaComb.getValue();
    }

    public int getMaxValue() {
        return maxAgendaComb.getValue();
    }

    public int getMinPercent() {
        return (int) (countMinAgendaComb * 100 / countAgendaComb);
    }

    public int getMaxPercent() {
        return (int) (countMaxAgendaComb * 100 / countAgendaComb);
    }

}
