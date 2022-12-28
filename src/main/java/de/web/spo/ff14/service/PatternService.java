package de.web.spo.ff14.service;

import de.web.spo.ff14.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Stream;

@Service
public class PatternService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final Random rand = new Random(System.currentTimeMillis());

    private final WeeklyProducts weeklyProducts;
    private final Map<String, List<String>> peakKeyMapping;

    private final Map<String, Map<String, Integer>> peakKeyPatternCount = new HashMap<>();

    private final Map<Product, Map<Integer, CycleValuePatternStats>> patternSupplyPercentageMap = new HashMap<>();
    private final Map<String, Map<Integer, Set<Product>>> productDistributionMap = new HashMap<>();

    public PatternService(WeeklyProducts weeklyProducts, Map<String, List<String>> peakKeyMapping) {
        this.weeklyProducts = weeklyProducts;
        this.peakKeyMapping = peakKeyMapping;
        this.calculatePatternStats();
    }

    private void calculatePatternStats() {
        peakKeyMapping.forEach((peakKey, mappingKeyList) -> {
            var map = new HashMap<String, Integer>();
            peakKeyPatternCount.put(peakKey, map);
            mappingKeyList.forEach(peak -> map.put(peak, CycleValuePatternList.peakCycleValuePatternMap.get(peak).count()));
        });

        weeklyProducts.getProducts().values().forEach(weeklyProduct -> {
            if(CycleValuePatternList.peakCycleValuePatternMap.containsKey(weeklyProduct.peakKey())) {
                peakKeyPatternCount.values().forEach(map -> {
                    if(map.containsKey(weeklyProduct.peakKey())) {
                        map.put(weeklyProduct.peakKey(), map.get(weeklyProduct.peakKey()) - 1);
                    }
                });
                Stream.iterate(1, cycle -> cycle + 1).limit(7)
                        .forEach(cycle -> {
                            var cycleValuePattern = CycleValuePatternList.peakCycleValuePatternMap.get(weeklyProduct.peakKey());
                            patternSupplyPercentageMap
                                    .computeIfAbsent(weeklyProduct.product(), k -> new HashMap<>())
                                    .computeIfAbsent(cycle, k -> new CycleValuePatternStats(weeklyProduct.peakKey()))
                                    .addPattern(cycleValuePattern.cycleValueList().get(cycle - 1).supply(), 1);
                            productDistributionMap
                                    .computeIfAbsent(weeklyProduct.peakKey(), k -> new HashMap<>())
                                    .computeIfAbsent(cycle, k -> new HashSet<>())
                                    .add(weeklyProduct.product());
                        });
            }
        });

        weeklyProducts.getProducts().values().forEach(weeklyProduct -> {
            if(!CycleValuePatternList.peakCycleValuePatternMap.containsKey(weeklyProduct.peakKey())) {
                peakKeyMapping.get(weeklyProduct.peakKey()).forEach(peak -> {
                    var cycleValuePattern = CycleValuePatternList.peakCycleValuePatternMap.get(peak);
                    Stream.iterate(1, cycle -> cycle + 1).limit(7)
                            .forEach(cycle -> {
                                patternSupplyPercentageMap
                                        .computeIfAbsent(weeklyProduct.product(), k -> new HashMap<>())
                                        .computeIfAbsent(cycle, k -> new CycleValuePatternStats(weeklyProduct.peakKey()))
                                        .addPattern(cycleValuePattern.cycleValueList().get(cycle - 1).supply(), peakKeyPatternCount.get(weeklyProduct.peakKey()).get(peak));
                                productDistributionMap
                                        .computeIfAbsent(weeklyProduct.peakKey(), k -> new HashMap<>())
                                        .computeIfAbsent(cycle, k -> new HashSet<>())
                                        .add(weeklyProduct.product());
                            });
                });
            }
        });
    }

    public CycleValuePatternStats getCycleValuePatternStats(int cycle, Product product) {
        return patternSupplyPercentageMap.get(product).get(cycle);
    }

    private HashMap<String, Map<String, Integer>> clonePeakPatternCount() {
        var peakKeyPatternCount2 = new HashMap<String, Map<String, Integer>>();
        peakKeyPatternCount.forEach((peakKey,peakMap) -> peakKeyPatternCount2.put(peakKey, new HashMap<>(peakMap)));
        return peakKeyPatternCount2;
    }

    public PeakCombCycles createRandomPeakCombCycles(int startCycle, Map<Product, Integer> productCountMap) {
        var peakCombCycles = new PeakCombCycles(startCycle, productCountMap);
        var peakKeyPatternCount = clonePeakPatternCount();
        var productList = new ArrayList<>(Product.productMap.values());
        while(productList.size() > 0) {
            var product = productList.get(rand.nextInt(productList.size()));
            productList.remove(product);

            String peak;
            var weeklyProduct = weeklyProducts.getProducts().get(product);
            if(CycleValuePatternList.peakCycleValuePatternMap.containsKey(weeklyProduct.peakKey())) {
                peak = weeklyProduct.peakKey();
            } else {
                var peakCountMap = peakKeyPatternCount.get(weeklyProduct.peakKey());
                var peakList = new ArrayList<>(peakCountMap.keySet());
                peak = peakList.get(rand.nextInt(peakList.size()));
                peakCountMap.put(peak, peakCountMap.get(peak) - 1);
                if(peakCountMap.get(peak) == 0) {
                    peakCountMap.remove(peak);
                }
            }

            peakCombCycles.getProductPeakMap().put(product, peak);
            var cycleValuePattern = CycleValuePatternList.peakCycleValuePatternMap.get(peak);
            var cycleValuePatternStatsMap = patternSupplyPercentageMap.get(product);

            Stream.iterate(startCycle, cycleNr -> cycleNr + 1).limit(8-startCycle).forEach(cycleNr -> {
                var cycle = peakCombCycles.getCycles().getCycle(cycleNr);
                cycle.getProductCycleValueMap().put(product, new ProductCycleValue(product, cycleValuePattern.cycleValueList().get(cycleNr-1), cycleValuePatternStatsMap.get(cycleNr)));
            });
        }
        return peakCombCycles;
    }
}

