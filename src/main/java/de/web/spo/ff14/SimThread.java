package de.web.spo.ff14;

import de.web.spo.ff14.model.*;
import de.web.spo.ff14.service.AgendaService;
import de.web.spo.ff14.service.CycleService;
import de.web.spo.ff14.service.PatternService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class SimThread implements Runnable{

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

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
        /*var productCountMap =  Map.of(Product.CORN_FLAKES, 6, Product.PICKLED_RADISH, 8, Product.GROWTH_FORMULA, 4, Product.FIRESAND, 3, Product.BOILED_EGG, 3, Product.SHEEPFLUFF_RUG, 12, Product.BED, 6);
        var cycleStart = 4;
        var grooveStart = 18;
        var freeDayDone = false;*/

        //var productCountMap = Map.of(Product.NECKLACE, 9, Product.SPRUCE_ROUND_SHIELD, 12, Product.TOMATO_RELISH, 3, Product.BUTTER, 12, Product.SHEEPFLUFF_RUG, 12);
        var productCountMap = Map.of(Product.CULINARY_KNIFE, 3, Product.BUTTER, 6, Product.SQUID_INK, 6, Product.ISLEFISH_PIE, 6, Product.CARAMELS, 6);
        //var productCountMap = new Hashtable<Product, Integer>();
        var cycleStart = 3;
        var grooveStart = 12;
        var freeDayDone = false;
        AtomicInteger i = new AtomicInteger();

        Stream.generate(patternService::getRandomWeeklyCycleValuePatternMap)
                .limit(100)
                .forEach(cycleValuePatternMap -> {
                            var valueAgendaCountMaps = agendaService.resortTopAgendaSetForCyclePattern(grooveValueAgendaMapMap, cycleValuePatternMap, productCountMap, cycleStart - 1);
                            Stream.generate(() -> cycleService.createRandomCycleComb(cycleValuePatternMap, new Hashtable<>(productCountMap), valueAgendaCountMaps, cycleStart, grooveStart, freeDayDone))
                                    .limit(1000)
                                    .forEach(cycleComb -> cycleCombStatsMap.computeIfAbsent(cycleComb.getKey(), cycleCombKey -> new CycleCombStats()).addCycleComb(cycleComb));
                            if(truncStats) {
                                if(cycleCombStatsMap.size() > 1000) {
                                    var cnt = cycleCombStatsMap.size() - 1000;
                                    new ArrayList<>(cycleCombStatsMap.values()).stream().sorted(Comparator.comparing(CycleCombStats::getMaxValue))
                                            .limit(cnt).forEach(cycleCombStats -> cycleCombStatsMap.remove(cycleCombStats.getCycleComb().getKey()));
                                }
                                LOGGER.info(i.getAndIncrement()+"%, "+cycleCombStatsMap.size());
                            }
                        }
                );

        latch.countDown();
    }
}
