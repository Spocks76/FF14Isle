package de.web.spo.ff14.service;

import de.web.spo.ff14.model.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PatternService {
    private final List<CycleValues> cycleValuesList;

    public PatternService(List<CycleValues> cycleValuesList) {
        this.cycleValuesList = cycleValuesList;
    }

    private final Random rand = new Random();

    public Map<Product, CycleValuePattern> getRandomWeeklyCycleValuePatternMap() {
        int maxCycle = Math.min(cycleValuesList.size(), 4);
        var cycleValuePatternListMap = CycleValuePatternList.cycleValuePatternMap.get(maxCycle-1);

        var cycleValuePatternListMap2 = new HashMap<String, CycleValuePatternList>();
        cycleValuePatternListMap.forEach((patternKey, cycleValuePatternList) -> cycleValuePatternListMap2.put(patternKey, new CycleValuePatternList(new ArrayList<>(cycleValuePatternList.cycleValuePatternList()))));

        var cycleValuePatternMap = new HashMap<Product, CycleValuePattern>();
        var productList = new ArrayList<>(Product.productMap.values());
        while(productList.size() > 0) {
            var product = productList.get(rand.nextInt(productList.size()));
            productList.remove(product);
            var cycleValueList = new ArrayList<CycleValue>();
            for(int i=0; i<maxCycle; i++) {
                cycleValueList.add(cycleValuesList.get(i).getCycleValueMap().get(product));
            }

            var patternKey = CycleValuePattern.getPatternKeyUntil(maxCycle, cycleValueList);
            var cycleValuePatternList = cycleValuePatternListMap2.get(patternKey);
            var cycleValuePattern = cycleValuePatternList.cycleValuePatternList().get(rand.nextInt(cycleValuePatternList.cycleValuePatternList().size()));
            cycleValuePatternList.cycleValuePatternList().remove(cycleValuePattern);
            cycleValuePatternMap.put(product, cycleValuePattern);
        }
        return cycleValuePatternMap;
    }
}
