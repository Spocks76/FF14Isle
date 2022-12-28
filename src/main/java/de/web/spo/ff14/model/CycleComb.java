package de.web.spo.ff14.model;

import lombok.Getter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@Getter
@ToString
public class CycleComb {
    private Map<Product, Integer> lastProductCountMap = new HashMap<>();

    private final Map<Integer, AgendaComb> agendaCombMap = new HashMap<>();

    private String key;
    private int value = 0;
    private int lastGroove;
    private boolean rest;

    public CycleComb(CycleComb cycleComb) {
        cycleComb.agendaCombMap.values().forEach(this::addAgendaComb);
        this.rest = cycleComb.rest;
        this.lastGroove = cycleComb.lastGroove;
        this.key = cycleComb.key;
        this.value = cycleComb.value;
        this.lastProductCountMap = cycleComb.lastProductCountMap;
    }

    public CycleComb(AgendaComb agendaComb, boolean rest) {
        this.addAgendaComb(agendaComb);
        this.rest = rest;
    }

    public void addAgendaComb(AgendaComb agendaComb) {
        this.lastProductCountMap = agendaComb.productCountMap();
        if(key == null) {
            key = agendaComb.getKey();
        } else {
            this.key+="| " + agendaComb.getKey();
        }
        this.value += agendaComb.getValue();
        this.lastGroove = agendaComb.groover().getLastGroove();
        this.rest = this.rest || agendaComb.isRest();
        agendaCombMap.put(agendaComb.cycle().getCycle(), agendaComb);
    }
}
