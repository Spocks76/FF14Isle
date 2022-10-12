package de.web.spo.ff14.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public record CycleValuePattern(List<CycleValue> cycleValueList, int count) {

    public static String getPatternKeyUntil(int cycleNumber, List<CycleValue> cycleValueList) {
        var stringBuilder = new StringBuilder(cycleValueList.get(0).getPatternKey(1));
        for(int i=1; i<cycleNumber; i++) {
            stringBuilder.append(",");
            stringBuilder.append(cycleValueList.get(i).getPatternKey(i+1));
        }
        return stringBuilder.toString();
    }

    public static String getAltPatternKeyUntil(int cycleNumber, List<CycleValue> cycleValueList) {
        var stringBuilder = new StringBuilder(cycleValueList.get(0).getAltPatternKey(1));
        for(int i=1; i<cycleNumber; i++) {
            stringBuilder.append(",");
            stringBuilder.append(cycleValueList.get(i).getAltPatternKey(i+1));
        }
        return stringBuilder.toString();
    }

    public CycleValueStatsList getCycleValueStatsList(String patternKey) {
        var cycleValueStatsList = new ArrayList<CycleValueStats>();
        Stream.iterate(1, cycleNumber -> cycleNumber + 1).limit(7).forEach(cycleNumber -> {
            var cycleValuePattern = cycleValueList.get(cycleNumber -1);
            var cycleValueStats = CycleValuePatternList.patternSupplyPercentageMap.get(patternKey).get(cycleNumber);
            var cycleValue = new CycleValue(cycleValuePattern.supply(), cycleValuePattern.demandShift());
            cycleValueStatsList.add(new CycleValueStats(cycleValue, cycleValueStats));
        });
        return new CycleValueStatsList(cycleValueStatsList);
    }

}
