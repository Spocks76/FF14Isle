package de.web.spo.ff14.model;

import lombok.Getter;

@Getter
public class AgendaCombStats {

    private AgendaComb minAgendaComb;
    private AgendaComb maxAgendaComb;
    private int countAgendaComb=0;

    private int countMinAgendaComb=0;

    private int countMaxAgendaComb=0;
    private int sumAgendaCombValue=0;

    public void addAgendaComb(AgendaComb agendaComb) {
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

    public int getAvgValue() {
        return sumAgendaCombValue / countAgendaComb;
    }

    public int getMinValue() {
        return minAgendaComb.getValue();
    }

    public int getMaxValue() {
        return maxAgendaComb.getValue();
    }

    public int getMinPercent() {
        return countMinAgendaComb * 100 / countAgendaComb;
    }

    public int getMaxPercent() {
        return countMaxAgendaComb * 100 / countAgendaComb;
    }

}
