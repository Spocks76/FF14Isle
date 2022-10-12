package de.web.spo.ff14.service;

import de.web.spo.ff14.model.*;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Stream;

@Service
public class PatternService {
    private final List<CycleValues> cycleValuesList;
    private final Random rand = new Random();

    private final Map<Product, Map<Integer, CycleValuePatternStats>> patternSupplyPercentageMap = new HashMap<>();
    private final Map<String, Map<Integer, Set<Product>>> productDistributionMap = new HashMap<>();

    public PatternService(List<CycleValues> cycleValuesList) {
        this.cycleValuesList = cycleValuesList;
        this.calculatePatternStats();
    }

    private void calculatePatternStats() {
        int maxCycle = Math.min(cycleValuesList.size(), 4);
        var cycleValuePatternListMap = CycleValuePatternList.cycleValuePatternMap.get(maxCycle-1);
        Product.productMap.values().forEach(product -> {
            var cycleValuePatternList = getCycleValuePatternList(maxCycle, product, cycleValuePatternListMap);
            cycleValuePatternList.cycleValuePatternList().forEach(cycleValuePattern ->
                    Stream.iterate(1, cycle -> cycle + 1).limit(7)
                            .forEach(cycle -> {
                                patternSupplyPercentageMap
                                        .computeIfAbsent(product, k -> new HashMap<>())
                                        .computeIfAbsent(cycle, k -> new CycleValuePatternStats(cycleValuePatternList.patternKey()))
                                        .addPattern(cycleValuePattern.cycleValueList().get(cycle-1).supply());
                                productDistributionMap
                                        .computeIfAbsent(cycleValuePatternList.patternKey(), k -> new HashMap<>())
                                        .computeIfAbsent(cycle, k -> new HashSet<>())
                                        .add(product);
                            })
            );
        });
    }

    private Map<String, CycleValuePatternList> copyPatternMap(Map<String, CycleValuePatternList> cycleValuePatternListMap) {
        var cycleValuePatternListMap2 = new HashMap<String, CycleValuePatternList>();
        cycleValuePatternListMap.forEach((patternKey, cycleValuePatternList) -> cycleValuePatternListMap2.put(patternKey, new CycleValuePatternList(patternKey, new ArrayList<>(cycleValuePatternList.cycleValuePatternList()))));
        return cycleValuePatternListMap2;
    }

    private CycleValuePatternList getCycleValuePatternList(int maxCycle, Product product, Map<String, CycleValuePatternList> cycleValuePatternListMap) {
        var cycleValueList = new ArrayList<CycleValue>();
        Stream.iterate(1, cycle -> cycle +1).limit(maxCycle).forEach(cycle -> cycleValueList.add(cycleValuesList.get(cycle-1).getCycleValueMap().get(product)));

        var patternKey = CycleValuePattern.getPatternKeyUntil(maxCycle, cycleValueList);
        var cycleValuePatternList = cycleValuePatternListMap.get(patternKey);
        if(cycleValuePatternList == null || cycleValuePatternList.cycleValuePatternList().size() == 0) {
            patternKey = CycleValuePattern.getAltPatternKeyUntil(maxCycle, cycleValueList);
            cycleValuePatternList = cycleValuePatternListMap.get(patternKey);
        }
        return cycleValuePatternList;
    }

    public CycleValuePatternStats getCycleValuePatternStats(int cycle, Product product) {
        var cycleValuePatternStats = patternSupplyPercentageMap.get(product).get(cycle);
        var productCount = productDistributionMap.get(cycleValuePatternStats.getPatternKey()).get(cycle);
        cycleValuePatternStats.setCount(productCount.size());
        return cycleValuePatternStats;
    }

    public Map<Product, CycleValueStatsList> getRandomWeeklyCycleValuePatternMap() {
        int maxCycle = Math.min(cycleValuesList.size(), 4);

        var cycleValuePatternListMap = copyPatternMap(CycleValuePatternList.cycleValuePatternMap.get(maxCycle-1));

        var cycleValuePatternMap = new HashMap<Product, CycleValueStatsList>();
        var productList = new ArrayList<>(Product.productMap.values());
        while(productList.size() > 0) {
            var product = productList.get(rand.nextInt(productList.size()));
            productList.remove(product);

            var cycleValuePatternList = getCycleValuePatternList(maxCycle, product, cycleValuePatternListMap);

            var cycleValuePattern = cycleValuePatternList.cycleValuePatternList().get(rand.nextInt(cycleValuePatternList.cycleValuePatternList().size()));
            cycleValuePatternList.cycleValuePatternList().remove(cycleValuePattern);
            cycleValuePatternMap.put(product, cycleValuePattern.getCycleValueStatsList(cycleValuePatternList.patternKey()));
        }
        return cycleValuePatternMap;
    }
}

