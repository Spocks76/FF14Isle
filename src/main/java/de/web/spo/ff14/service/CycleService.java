package de.web.spo.ff14.service;

import de.web.spo.ff14.model.*;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Stream;

@Service
public class CycleService {

    private final AgendaService agendaService;
    private final Random rand = new Random();

    public CycleService(AgendaService agendaService) {
        this.agendaService = agendaService;
    }

    public void createRandomAgendaCombs(Map<Integer, List<ValueAgendaCountMap>> valueAgendaCountMaps, CycleComb cycleComb, int cycle, int groove, boolean freeDayDone) {
        if(cycle > 7 || cycle == 7 && !freeDayDone) {
            return;
        }
        if(!freeDayDone && rand.nextInt(8-cycle) == 0) {
            this.createRandomAgendaCombs(valueAgendaCountMaps, cycleComb, cycle + 1, groove, true);
            return;
        }

        Map<String, AgendaCombStats> agendaCombStatsMap = new Hashtable<>();
        Stream.generate(() -> agendaService.createRandomAgendaComb(valueAgendaCountMaps.get(cycle-1), cycleComb.getCycleValuePatternMap(), new Hashtable<>(cycleComb.getProductCountMap()), cycle, groove))
                .limit(10000)
                .forEach(agendaComb -> agendaCombStatsMap.computeIfAbsent(agendaComb.getKey(), agendaCombKey -> new AgendaCombStats()).addAgendaComb(agendaComb));

        agendaCombStatsMap.values().stream()
                .sorted(Comparator.comparing(AgendaCombStats::getAvgValue).reversed())
                .skip(rand.nextInt(5))
                .findFirst()
                .ifPresent(agendaCombStats -> {
                    cycleComb.addAgendaCombStats(cycle, agendaCombStats);
                    this.createRandomAgendaCombs(valueAgendaCountMaps, cycleComb, cycle + 1,  agendaCombStats.getMaxAgendaComb().groover().getGroove(20), freeDayDone);
                });
    }
    public CycleComb createRandomCycleComb(Map<Product, CycleValuePattern> cycleValuePatternMap, Map<Product, Integer> productCountMap, Map<Integer, List<ValueAgendaCountMap>> valueAgendaCountMaps, int cycleStart, int grooveStart, boolean freedayDone) {
        CycleComb cycleComb = new CycleComb(cycleValuePatternMap, new Hashtable<>(productCountMap));
        this.createRandomAgendaCombs(valueAgendaCountMaps, cycleComb, cycleStart, grooveStart, freedayDone);
        return cycleComb;
    }


}
