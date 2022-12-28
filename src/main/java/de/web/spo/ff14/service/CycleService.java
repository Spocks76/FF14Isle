package de.web.spo.ff14.service;

import de.web.spo.ff14.model.*;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CycleService {

    private final AgendaService agendaService;

    public CycleService(AgendaService agendaService) {
        this.agendaService = agendaService;
    }

    public void createCycleCombList(PeakCombCycles peakCombCycles, int cycleNr, int groove, boolean restDayDone) {
        var cycle = peakCombCycles.getCycles().getCycle(cycleNr);
        cycle.setCycleCombList(cycle.getAgendaList().stream().map(agenda -> new CycleComb(agendaService.createAgendaComb(cycle, peakCombCycles.getProductPeakMap(), new HashMap<>(peakCombCycles.getProductCountMap()), agenda, groove), restDayDone)).collect(Collectors.toList()));
        if(!restDayDone) {
            cycle.getCycleCombList().add(new CycleComb(new AgendaComb(cycle, new Groover(groove), null, null, null, new HashMap<>(peakCombCycles.getProductCountMap())), true));
        }
    }

    public void addCycleCombList(PeakCombCycles peakCombCycles, int cycleNr) {
        var cycleBefore = peakCombCycles.getCycles().getCycle(cycleNr - 1);
        var cycle = peakCombCycles.getCycles().getCycle(cycleNr);
        var cycleCombGrooveMap = new HashMap<Integer, GrooveValueCycleCombMap>();
        cycleBefore.getCycleCombList().forEach(cycleComb -> {
            if(cycleNr < 7 || cycleComb.isRest()) {
                cycle.getAgendaList().forEach(agenda -> {
                    var cycleCombNew = new CycleComb(cycleComb);
                    cycleCombNew.addAgendaComb(agendaService.createAgendaComb(cycle, peakCombCycles.getProductPeakMap(), new HashMap<>(cycleComb.getLastProductCountMap()), agenda, cycleComb.getLastGroove()));
                    cycleCombGrooveMap.computeIfAbsent(cycleCombNew.getLastGroove(), groove -> new GrooveValueCycleCombMap(groove, 10)).addCycleComb(cycleCombNew);
                });
            }
            if(!cycleComb.isRest()) {
                var cycleCombNew = new CycleComb(cycleComb);
                cycleCombNew.addAgendaComb(new AgendaComb(cycle, new Groover(cycleComb.getLastGroove()), null, null, null, new HashMap<>(cycleComb.getLastProductCountMap())));
                cycleCombGrooveMap.computeIfAbsent(cycleCombNew.getLastGroove(), groove -> new GrooveValueCycleCombMap(groove, 10)).addCycleComb(cycleCombNew);
            }
        });
        var cycleCombList = new ArrayList<CycleComb>();
        cycleCombGrooveMap.values().forEach(grooveValueCycleCombMap -> grooveValueCycleCombMap.getCycleCombTreeMap().values().forEach(cycleCombList::addAll));
        cycleCombGrooveMap.values().forEach(grooveValueCycleCombMap -> grooveValueCycleCombMap.getCycleCombTreeRestMap().values().forEach(cycleCombList::addAll));
        cycle.setCycleCombList(cycleCombList);
    }
}
