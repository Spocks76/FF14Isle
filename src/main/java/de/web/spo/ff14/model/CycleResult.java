package de.web.spo.ff14.model;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class CycleResult<R extends Result> {

    private final Integer cycle;
    private final Map<Integer, GrooveAgendaResults<R>> grooveAgendaResultsMap = new HashMap<>();

    public CycleResult(Integer cycle) {
        this.cycle = cycle;
    }

    public void trim(int trimSize) {
        grooveAgendaResultsMap.values().forEach(agendaResults -> agendaResults.trim(trimSize));
    }

    public Map<String, R> getAllResults() {
        var results = new HashMap<String, R>();
        grooveAgendaResultsMap.values()
                .forEach(agendaResultGrooveAgendaResults ->
                        agendaResultGrooveAgendaResults.maxResults().values().forEach(agendaResultMap -> agendaResultMap.values().forEach(agendaResult -> results.put(agendaResult.getProductKey(), agendaResult)))
                );
        return results;
    }
}
