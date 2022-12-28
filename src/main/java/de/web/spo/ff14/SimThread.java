package de.web.spo.ff14;

import de.web.spo.ff14.model.*;
import de.web.spo.ff14.service.AgendaService;
import de.web.spo.ff14.service.CycleService;
import de.web.spo.ff14.service.PatternService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SimThread implements Runnable{

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final AgendaService agendaService;
    private final PatternService patternService;
    private final CycleService cycleService;

    private final CountDownLatch latch;

    private final Map<Integer, GrooveValueAgendaMap> grooveValueAgendaMapMap;
    private final Map<String, CycleStartStats> cycleStartStatsMap;

    private final boolean truncStats;

    public SimThread(AgendaService agendaService, PatternService patternService, CycleService cycleService, CountDownLatch latch, Map<Integer, GrooveValueAgendaMap> grooveValueAgendaMapMap, Map<String, CycleStartStats> cycleStartStatsMap, boolean truncStats) {
        this.agendaService = agendaService;
        this.patternService = patternService;
        this.cycleService = cycleService;
        this.latch = latch;
        this.grooveValueAgendaMapMap = grooveValueAgendaMapMap;
        this.cycleStartStatsMap = cycleStartStatsMap;
        this.truncStats = truncStats;
    }

    @Override
    public void run() {

        var startGroove = 0;
        var restDayDone = true;

        var productCountMap = new Hashtable<Product, Integer>();
        //var productCountMap =  Map.of(Product.FIRESAND, 9, Product.GARNET_RAPIER, 12, Product.SQUID_INK, 9, Product.SHARK_OIL, 12, Product.CORN_FLAKES, 9, Product.PICKLED_RADISH, 12);
        var cycleStart = 2;

        var randomPeakCombCyclesMap = Stream.generate(() -> patternService.createRandomPeakCombCycles(cycleStart, productCountMap))
                .limit(100)
                .peek(peakCombCycles -> agendaService.resortTopAgendaSetForCyclePattern(peakCombCycles, grooveValueAgendaMapMap))
                .collect(Collectors.toMap(PeakCombCycles::getProductPeaks, peakCombCycles -> peakCombCycles, (v1, v2) -> v1));
        var peakCombCyclesList = new ArrayList<>(randomPeakCombCyclesMap.values());

        LOGGER.info("Created {} different product peak pattern combinations", peakCombCyclesList.size());

        peakCombCyclesList
                .forEach(peakCombCycles -> {
                    var startCycle = peakCombCycles.getCycles().getStartCycle();
                    cycleService.createCycleCombList(peakCombCycles, startCycle, startGroove, restDayDone);
                    Stream.iterate(startCycle + 1, cycleNr -> cycleNr + 1).limit(8 - startCycle - 1)
                                    .forEach(cycleNr -> cycleService.addCycleCombList(peakCombCycles, cycleNr));
                    peakCombCycles.getCycles().getCycle(7).getCycleCombList().forEach(cycleComb -> {
                        var agendaComb1 =  cycleComb.getAgendaCombMap().get(startCycle);
                        var agendaComb2 =  cycleComb.getAgendaCombMap().get(startCycle+1);
                        var perc1 = agendaComb1.getPercentage();
                        var perc2 = agendaComb2.getPercentage();
                        var startAgendaCombKey = agendaComb1.getKey();
                        if(perc1>= 25 && perc2>=5) {
                            cycleStartStatsMap.computeIfAbsent(startAgendaCombKey, CycleStartStats::new).addCycleComb(agendaComb1, cycleComb);
                        }
                    });
                    if(truncStats) {
                        var cycleStartStatsList = new ArrayList<>(cycleStartStatsMap.values()).stream().sorted(Comparator.comparing(CycleStartStats::getMaxValue).reversed()).toList();
                        if(cycleStartStatsList.size() > 100) {
                            cycleStartStatsList.subList(100, cycleStartStatsList.size()).forEach(cycleStartStats -> cycleStartStatsMap.remove(cycleStartStats.getAgendaCombKey()));
                        }
                        cycleStartStatsMap.values().forEach(cycleStartStats1 -> cycleStartStats1.truncMap(100));
                    }
                });

        LOGGER.info("Package finished");

        latch.countDown();
    }
}
