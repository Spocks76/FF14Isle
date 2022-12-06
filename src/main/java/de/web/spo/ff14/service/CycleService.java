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

        Map<Integer, Map<String, AgendaCombStats>> grooveAgendaCombStatsMap = new Hashtable<>();
        Stream.generate(() -> agendaService.createRandomAgendaComb(valueAgendaCountMaps.get(cycle-1), cycleComb.getCycleValuePatternMap(), new Hashtable<>(cycleComb.getProductCountMap()), cycle, groove))
                .limit(1000)
                .forEach(agendaComb -> grooveAgendaCombStatsMap.computeIfAbsent(agendaComb.groover().getLastGroove(), lastGroove -> new Hashtable<>())
                        .computeIfAbsent(agendaComb.getKey(), agendaCombKey -> new AgendaCombStats()).addAgendaComb(agendaComb));

        List<AgendaCombStats> agendaCombStatsList = new ArrayList<>();
        grooveAgendaCombStatsMap.values()
                .forEach(agendaCombatStatsMap -> agendaCombatStatsMap.values().stream().sorted(Comparator.comparing(AgendaCombStats::getAvgValue).reversed())
                                .limit(5)
                                .forEach(agendaCombStatsList::add)
                );
        var agendaCombStats = agendaCombStatsList.get(rand.nextInt(agendaCombStatsList.size()));
        cycleComb.addAgendaCombStats(cycle, agendaCombStats);
        this.createRandomAgendaCombs(valueAgendaCountMaps, cycleComb, cycle + 1,  agendaCombStats.getAgendaComb().groover().getLastGroove(), freeDayDone);

    }
    public CycleComb createRandomCycleComb(Map<Product, CycleValueStatsList> cycleValuePatternMap, Map<Product, Integer> productCountMap, Map<Integer, List<ValueAgendaCountMap>> valueAgendaCountMaps, int cycleStart, int grooveStart, boolean freedayDone) {
        CycleComb cycleComb = new CycleComb(cycleValuePatternMap, new Hashtable<>(productCountMap));
        this.createRandomAgendaCombs(valueAgendaCountMaps, cycleComb, cycleStart, grooveStart, freedayDone);
        return cycleComb;
    }


}
