package de.web.spo.ff14;

import de.web.spo.ff14.model.AgendaCombStats;
import de.web.spo.ff14.model.ValueAgendaCountList;
import de.web.spo.ff14.service.AgendaService;
import de.web.spo.ff14.service.PatternService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

public class SimThread implements Runnable{

    private final PatternService patternService;
    private final AgendaService agendaService;
    private final Map<String, AgendaCombStats> agendaCombStatsMap;
    private final List<ValueAgendaCountList> valueAgendaCountLists;
    private final CountDownLatch latch;

    private final boolean truncStats;

    public SimThread(PatternService patternService, AgendaService agendaService, Map<String, AgendaCombStats> agendaCombStatsMap, List<ValueAgendaCountList> valueAgendaCountLists, CountDownLatch latch, boolean truncStats) {
        this.patternService = patternService;
        this.agendaService = agendaService;
        this.agendaCombStatsMap = agendaCombStatsMap;
        this.valueAgendaCountLists = valueAgendaCountLists;
        this.latch = latch;
        this.truncStats = truncStats;
    }

    @Override
    public void run() {
        for(int i0 = 0; i0<10000; i0++) {
            var cycleValuePatternMap = patternService.getRandomWeeklyCycleValuePatternMap();
            for (var i = 0; i < 1000; i++) {
                var agendaComb = agendaService.createRandomAgendaComb(valueAgendaCountLists, cycleValuePatternMap, 2, 9);
                agendaCombStatsMap.computeIfAbsent(agendaComb.getKey(), agendaCombKey -> new AgendaCombStats()).addAgendaComb(agendaComb);
                if(truncStats && agendaCombStatsMap.size()>1000) {
                    List<AgendaCombStats> combStatsList = new ArrayList<>(agendaCombStatsMap.values());
                    combStatsList.sort(Comparator.comparing(AgendaCombStats::getMaxValue).reversed());
                    for(int i2=900; i2<1000; i2++) {
                        agendaCombStatsMap.remove(combStatsList.get(i2).getMinAgendaComb().getKey());
                    }
                }
            }
        }
        latch.countDown();
    }
}
