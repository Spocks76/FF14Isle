package de.web.spo.ff14;

import de.web.spo.ff14.model.*;
import de.web.spo.ff14.service.AgendaService;
import de.web.spo.ff14.service.CycleService;
import de.web.spo.ff14.service.PatternService;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Stream;

public class SimThread implements Runnable{

    private final PatternService patternService;
    private final CycleService cycleService;

    private final AgendaService agendaService;

    private final Map<Integer, GrooveValueAgendaMap> grooveValueAgendaMapMap;
    private final CountDownLatch latch;

    private final Map<String, CycleCombStats> cycleCombStatsMap;

    private final boolean truncStats;

    public SimThread(PatternService patternService, CycleService cycleService, AgendaService agendaService, Map<Integer, GrooveValueAgendaMap> grooveValueAgendaMapMap, CountDownLatch latch, Map<String, CycleCombStats> cycleCombStatsMap, boolean truncStats) {
        this.patternService = patternService;
        this.cycleService = cycleService;
        this.agendaService = agendaService;
        this.grooveValueAgendaMapMap = grooveValueAgendaMapMap;
        this.latch = latch;
        this.cycleCombStatsMap = cycleCombStatsMap;
        this.truncStats = truncStats;
    }

    @Override
    public void run() {
        var productCountMap =  new Hashtable<Product, Integer>();
        Stream.generate(patternService::getRandomWeeklyCycleValuePatternMap)
                .limit(1)
                .forEach(cycleValuePatternMap -> {
                            var valueAgendaCountMaps = agendaService.resortTopAgendaSetForCyclePattern(grooveValueAgendaMapMap, cycleValuePatternMap, productCountMap, 1);
                            Stream.generate(() -> cycleService.createRandomCycleComb(cycleValuePatternMap, new Hashtable<>(productCountMap), valueAgendaCountMaps, 2, 0, false))
                                    .limit(1000)
                                    .forEach(cycleComb -> cycleCombStatsMap.computeIfAbsent(cycleComb.getKey(), cycleCombKey -> new CycleCombStats()).addCycleComb(cycleComb));
                        }
                );

        latch.countDown();
    }
}
