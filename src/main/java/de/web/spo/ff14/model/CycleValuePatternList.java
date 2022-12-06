package de.web.spo.ff14.model;

import java.util.*;
import java.util.stream.Stream;

public record CycleValuePatternList(String patternKey, List<CycleValuePattern> cycleValuePatternList) {
    public static List<Map<String, CycleValuePatternList>> cycleValuePatternMap = List.of(new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>());
    public static Map<String, Map<Integer, CycleValuePatternStats>> patternSupplyPercentageMap = new HashMap<>();

    static {
        List<CycleValuePattern> cycleValuePatterns = List.of(
                new CycleValuePattern(List.of(
                        new CycleValue(Supply.INSUFFICIENT, DemandShift.SKYROCKETING, -8),
                        new CycleValue(Supply.NONEXISTENT, DemandShift.SKYROCKETING, -15),
                        new CycleValue(Supply.SUFFICIENT, DemandShift.PLUMMETING, 0),
                        new CycleValue(Supply.SUFFICIENT, DemandShift.NONE, 0),
                        new CycleValue(Supply.SUFFICIENT, DemandShift.NONE, 0),
                        new CycleValue(Supply.SUFFICIENT, DemandShift.NONE, 0),
                        new CycleValue(Supply.SUFFICIENT, DemandShift.NONE, 0)
                ), 4, "2S"),
                new CycleValuePattern(List.of(
                        new CycleValue(Supply.INSUFFICIENT, DemandShift.INCREASING, -4),
                        new CycleValue(Supply.INSUFFICIENT, DemandShift.INCREASING, -8),
                        new CycleValue(Supply.SUFFICIENT, DemandShift.PLUMMETING, 2),
                        new CycleValue(Supply.SUFFICIENT, DemandShift.NONE, 2),
                        new CycleValue(Supply.SUFFICIENT, DemandShift.NONE, 2),
                        new CycleValue(Supply.SUFFICIENT, DemandShift.NONE, 2),
                        new CycleValue(Supply.SUFFICIENT, DemandShift.NONE, 2)
                ), 4, "2W"),
                new CycleValuePattern(List.of(
                        new CycleValue(Supply.SUFFICIENT, DemandShift.NONE, 0),
                        new CycleValue(Supply.INSUFFICIENT, DemandShift.SKYROCKETING, -8),
                        new CycleValue(Supply.NONEXISTENT, DemandShift.SKYROCKETING, -15),
                        new CycleValue(Supply.SUFFICIENT, DemandShift.PLUMMETING, 0),
                        new CycleValue(Supply.SUFFICIENT, DemandShift.NONE, 0),
                        new CycleValue(Supply.SUFFICIENT, DemandShift.NONE, 0),
                        new CycleValue(Supply.SUFFICIENT, DemandShift.NONE, 0)
                ), 4, "3S"),
                new CycleValuePattern(List.of(
                        new CycleValue(Supply.SUFFICIENT, DemandShift.NONE, 0),
                        new CycleValue(Supply.INSUFFICIENT, DemandShift.INCREASING, -4),
                        new CycleValue(Supply.INSUFFICIENT, DemandShift.INCREASING, -8),
                        new CycleValue(Supply.SUFFICIENT, DemandShift.PLUMMETING, 2),
                        new CycleValue(Supply.SUFFICIENT, DemandShift.NONE, 2),
                        new CycleValue(Supply.SUFFICIENT, DemandShift.NONE, 2),
                        new CycleValue(Supply.SUFFICIENT, DemandShift.NONE, 2)
                ), 4, "3W"),
                new CycleValuePattern(List.of(
                        new CycleValue(Supply.SUFFICIENT, DemandShift.NONE,0),
                        new CycleValue(Supply.SUFFICIENT, DemandShift.NONE, 0),
                        new CycleValue(Supply.INSUFFICIENT, DemandShift.SKYROCKETING, -8),
                        new CycleValue(Supply.NONEXISTENT, DemandShift.SKYROCKETING, -15),
                        new CycleValue(Supply.SUFFICIENT, DemandShift.PLUMMETING, 0),
                        new CycleValue(Supply.SUFFICIENT, DemandShift.NONE, 0),
                        new CycleValue(Supply.SUFFICIENT, DemandShift.NONE, 0)
                ), 4, "4S"),
                new CycleValuePattern(List.of(
                        new CycleValue(Supply.SUFFICIENT, DemandShift.NONE, 0),
                        new CycleValue(Supply.SUFFICIENT, DemandShift.NONE, 0),
                        new CycleValue(Supply.INSUFFICIENT, DemandShift.INCREASING, -4),
                        new CycleValue(Supply.INSUFFICIENT, DemandShift.INCREASING, -8),
                        new CycleValue(Supply.SUFFICIENT, DemandShift.PLUMMETING, 2),
                        new CycleValue(Supply.SUFFICIENT, DemandShift.NONE, 2),
                        new CycleValue(Supply.SUFFICIENT, DemandShift.NONE, 2)
                ), 4, "4W"),
                new CycleValuePattern(List.of(
                        new CycleValue(Supply.SUFFICIENT, DemandShift.NONE, 0),
                        new CycleValue(Supply.SUFFICIENT, DemandShift.NONE, 0),
                        new CycleValue(Supply.SUFFICIENT, DemandShift.NONE, 0),
                        new CycleValue(Supply.INSUFFICIENT, DemandShift.SKYROCKETING, -8),
                        new CycleValue(Supply.NONEXISTENT, DemandShift.SKYROCKETING, -15),
                        new CycleValue(Supply.SUFFICIENT, DemandShift.PLUMMETING, 0),
                        new CycleValue(Supply.SUFFICIENT, DemandShift.NONE, 0)
                ), 4, "5S"),
                new CycleValuePattern(List.of(
                        new CycleValue(Supply.SUFFICIENT, DemandShift.NONE, 0),
                        new CycleValue(Supply.SUFFICIENT, DemandShift.NONE, 0),
                        new CycleValue(Supply.SUFFICIENT, DemandShift.NONE, 0),
                        new CycleValue(Supply.INSUFFICIENT, DemandShift.INCREASING, -4),
                        new CycleValue(Supply.INSUFFICIENT, DemandShift.INCREASING, -8),
                        new CycleValue(Supply.SUFFICIENT, DemandShift.PLUMMETING, 2),
                        new CycleValue(Supply.SUFFICIENT, DemandShift.NONE, 2)
                ), 4, "5W"),
                new CycleValuePattern(List.of(
                        new CycleValue(Supply.SUFFICIENT, DemandShift.NONE, 0),
                        new CycleValue(Supply.INSUFFICIENT, DemandShift.NONE, -1),
                        new CycleValue(Supply.SUFFICIENT, DemandShift.PLUMMETING, 7),
                        new CycleValue(Supply.SUFFICIENT, DemandShift.SKYROCKETING, 0),
                        new CycleValue(Supply.INSUFFICIENT, DemandShift.SKYROCKETING, -8),
                        new CycleValue(Supply.NONEXISTENT, DemandShift.SKYROCKETING, -15),
                        new CycleValue(Supply.SUFFICIENT, DemandShift.PLUMMETING, 0)
                ), 4, "6S"),
                new CycleValuePattern(List.of(
                        new CycleValue(Supply.SUFFICIENT, DemandShift.NONE, 0),
                        new CycleValue(Supply.INSUFFICIENT, DemandShift.NONE, -1),
                        new CycleValue(Supply.SUFFICIENT, DemandShift.DECREASING, 4),
                        new CycleValue(Supply.SUFFICIENT, DemandShift.INCREASING, 0),
                        new CycleValue(Supply.INSUFFICIENT, DemandShift.INCREASING, -4),
                        new CycleValue(Supply.INSUFFICIENT, DemandShift.INCREASING, -8),
                        new CycleValue(Supply.SUFFICIENT, DemandShift.PLUMMETING, 2)
                ), 4, "6W"),
                new CycleValuePattern(List.of(
                        new CycleValue(Supply.SUFFICIENT, DemandShift.NONE, 0),
                        new CycleValue(Supply.INSUFFICIENT, DemandShift.NONE, -1),
                        new CycleValue(Supply.SUFFICIENT, DemandShift.PLUMMETING, 7),
                        new CycleValue(Supply.SUFFICIENT, DemandShift.NONE, 7),
                        new CycleValue(Supply.SUFFICIENT, DemandShift.SKYROCKETING, 0),
                        new CycleValue(Supply.INSUFFICIENT, DemandShift.SKYROCKETING, -8),
                        new CycleValue(Supply.NONEXISTENT, DemandShift.SKYROCKETING, -15)
                ), 5, "7S"),
                new CycleValuePattern(List.of(
                        new CycleValue(Supply.SUFFICIENT, DemandShift.NONE, 0),
                        new CycleValue(Supply.INSUFFICIENT, DemandShift.NONE, -1),
                        new CycleValue(Supply.SUFFICIENT, DemandShift.PLUMMETING, 7),
                        new CycleValue(Supply.SUFFICIENT, DemandShift.INCREASING, 4),
                        new CycleValue(Supply.SUFFICIENT, DemandShift.INCREASING, 0),
                        new CycleValue(Supply.INSUFFICIENT, DemandShift.INCREASING, -4),
                        new CycleValue(Supply.INSUFFICIENT, DemandShift.INCREASING, -8)
                ), 5, "7W")
        );


        cycleValuePatterns.forEach(cycleValuePattern -> {
            for (int i = 0; i < cycleValuePattern.count(); i++) {
                for (int cycleIndex = 0; cycleIndex < 4; cycleIndex++) {
                    cycleValuePatternMap.get(cycleIndex).computeIfAbsent(CycleValuePattern.getPatternKeyUntil(cycleIndex + 1, cycleValuePattern.cycleValueList()), patternKey -> new CycleValuePatternList(patternKey, new ArrayList<>())).cycleValuePatternList().add(cycleValuePattern);
                }
            }
        });

        cycleValuePatternMap
                .forEach(cycleValuePatternListMap -> cycleValuePatternListMap
                        .forEach((patternKey, cycleValuePatternList1) -> cycleValuePatternList1.cycleValuePatternList()
                                .forEach(cycleValuePattern -> Stream.iterate(1, cycle -> cycle + 1).limit(7)
                                        .forEach(cycle -> patternSupplyPercentageMap
                                                .computeIfAbsent(patternKey, k -> new HashMap<>())
                                                .computeIfAbsent(cycle, k -> new CycleValuePatternStats(patternKey))
                                                .addPattern(cycleValuePattern.cycleValueList().get(cycle-1).supply())
                                        ))
                        )
                );

    }

}
