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

    private final Map<String, CycleStartStats> cycleStartStatsMap;

    private final boolean truncStats;

    public SimThread(PatternService patternService, CycleService cycleService, AgendaService agendaService, Map<Integer, GrooveValueAgendaMap> grooveValueAgendaMapMap, CountDownLatch latch, Map<String, CycleStartStats> cycleStartStatsMap, boolean truncStats) {
        this.patternService = patternService;
        this.cycleService = cycleService;
        this.agendaService = agendaService;
        this.grooveValueAgendaMapMap = grooveValueAgendaMapMap;
        this.latch = latch;
        this.cycleStartStatsMap = cycleStartStatsMap;
        this.truncStats = truncStats;
    }

    @Override
    public void run() {
        /*
        var cycleStart = 4;
        var grooveStart = 18;
        var freeDayDone = false;*/

        //var productCountMap =  Map.of(Product.CORN_FLAKES, 6, Product.PICKLED_RADISH, 8, Product.GROWTH_FORMULA, 4, Product.FIRESAND, 3, Product.BOILED_EGG, 3, Product.SHEEPFLUFF_RUG, 12, Product.BED, 6);
        //var productCountMap = Map.of(Product.NECKLACE, 9, Product.SPRUCE_ROUND_SHIELD, 12, Product.TOMATO_RELISH, 3, Product.BUTTER, 12, Product.SHEEPFLUFF_RUG, 12);
        //var productCountMap = Map.of(Product.CULINARY_KNIFE, 6, Product.BUTTER, 8, Product.SQUID_INK, 8, Product.ISLEFISH_PIE, 8, Product.CARAMELS, 6, Product.EARRINGS, 6, Product.SCALE_FINGERS, 6, Product.SPRUCE_ROUND_SHIELD, 8, Product.FIRESAND, 9, Product.GARNET_RAPIER, 12);
        //var productCountMap =  Map.of(Product.EARRINGS, 9, Product.SILVER_EAR_CUFFS, 12, Product.PUMPKIN_PUDDING, 9, Product.SWEET_POPOTO, 12);
        //var productCountMap =  Map.of(Product.CULINARY_KNIFE, 3, Product.SHARK_OIL, 12, Product.BRUSH, 6, Product.POTION, 9, Product.GROWTH_FORMULA, 12);
        //var productCountMap =  Map.of(Product.BRUSH, 9, Product.SPRUCE_ROUND_SHIELD, 12, Product.BUTTER, 3, Product.BOILED_EGG, 6, Product.ONION_SOUP, 12, Product.PARSNIP_SALAD, 6, Product.ROPE, 3, Product.CAVALIERS_HAT, 12, Product.SCALE_FINGERS, 6);
        //var productCountMap =  Map.of(Product.EARRINGS, 18, Product.SCALE_FINGERS, 12, Product.BAKED_PUMPKIN, 3, Product.BOILED_EGG, 12, Product.SHEEPFLUFF_RUG, 12, Product.SILVER_EAR_CUFFS, 12);
        //var productCountMap =  Map.of(Product.BOILED_EGG, 15, Product.EARRINGS, 6, Product.HORN, 12, Product.BRUSH, 6, Product.BUTTER, 3, Product.SHEEPFLUFF_RUG, 12, Product.SPRUCE_ROUND_SHIELD, 9, Product.SCALE_FINGERS, 6);
        //var productCountMap =  Map.of(Product.BOILED_EGG, 15, Product.HORA, 12, Product.ROPE, 3, Product.SHARK_OIL, 12, Product.BRUSH, 6);
        //var cycleStart = 5;
        //var grooveStart = 30;

        var productCountMap = new Hashtable<Product, Integer>();
        //var productCountMap =  Map.of(Product.EARRINGS, 9, Product.SILVER_EAR_CUFFS, 12, Product.FIRESAND, 9, Product.GROWTH_FORMULA, 12);

        var cycleStart = 2;
        var grooveStart = 0;
        var freeDayDone = false;
        var maxRandomWeekPatternTries = 500;
        //var maxRandomWeekPatternTries = 1;
        var maxCycleCombTries = 100;
        //var maxCycleCombTries = 10000;

        AtomicInteger i = new AtomicInteger();

        Stream.generate(patternService::getRandomWeeklyCycleValuePatternMap)

                .limit(maxRandomWeekPatternTries)
                .forEach(cycleValuePatternMap -> {
                            var valueAgendaCountMaps = agendaService.resortTopAgendaSetForCyclePattern(grooveValueAgendaMapMap, cycleValuePatternMap, productCountMap, cycleStart - 1);
                            Stream.generate(() -> cycleService.createRandomCycleComb(cycleValuePatternMap, new Hashtable<>(productCountMap), valueAgendaCountMaps, cycleStart, grooveStart, freeDayDone))
                                    .limit(maxCycleCombTries)
                                    .forEach(cycleComb -> {
                                        Integer perc1 = null;
                                        Integer perc2 = null;
                                        AgendaComb agendaComb = null;
                                        String startAgendaCombKey = "Free";

                                        if(cycleComb.getAgendaCombStatsCycles()[cycleStart-1] != null) {
                                            agendaComb = cycleComb.getAgendaCombStatsCycles()[cycleStart - 1].getAgendaComb();
                                            perc1 = agendaComb.getPercentage();
                                            startAgendaCombKey = agendaComb.getKey();
                                        }
                                        if(cycleComb.getAgendaCombStatsCycles()[cycleStart] != null) {
                                            perc2 = cycleComb.getAgendaCombStatsCycles()[cycleStart].getAgendaComb().getPercentage();
                                        }
                                        if(perc1 == null) {
                                            perc1 = perc2;
                                        }
                                        if(perc2 == null) {
                                            perc2 = cycleComb.getAgendaCombStatsCycles()[cycleStart+1].getAgendaComb().getPercentage();
                                        }
                                        if(perc1>= 10 && perc2>=5) {
                                            cycleStartStatsMap.computeIfAbsent(startAgendaCombKey, CycleStartStats::new).addCycleComb(agendaComb, cycleComb);
                                        }
                                    });
                            if(truncStats) {
                                if(cycleStartStatsMap.size() > 100) {
                                    var cnt = cycleStartStatsMap.size() - 100;
                                    new ArrayList<>(cycleStartStatsMap.values()).stream().sorted(Comparator.comparing(CycleStartStats::getMaxValue))
                                            .limit(cnt).forEach(cycleStartStats -> cycleStartStatsMap.remove(cycleStartStats.getAgendaCombKey()));
                                }
                                cycleStartStatsMap.values().forEach(cycleStartStats -> cycleStartStats.truncMap(100));
                                LOGGER.info(((i.getAndIncrement()+1)*100/maxRandomWeekPatternTries)+"%, "+cycleStartStatsMap.size());
                            }
                        }
                );

        latch.countDown();
    }
}
