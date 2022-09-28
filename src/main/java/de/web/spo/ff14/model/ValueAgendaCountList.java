package de.web.spo.ff14.model;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class ValueAgendaCountList {
    private final Integer value;
    private final Map<String, AgendaCount> agendaCountMap = new HashMap<>();

    public ValueAgendaCountList(Integer value) {
        this.value = value;
    }

    public void addAgenda(Agenda agenda) {
        agendaCountMap.computeIfAbsent(agenda.getProductKey().toString(), productKey -> new AgendaCount(agenda, 0)).incCount();
    }
}
