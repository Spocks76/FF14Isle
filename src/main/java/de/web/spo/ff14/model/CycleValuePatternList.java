package de.web.spo.ff14.model;

import java.util.*;
import java.util.stream.Stream;

public record CycleValuePatternList(String patternKey, List<CycleValuePattern> cycleValuePatternList) {
    public static List<Map<String, CycleValuePatternList>> cycleValuePatternMap = List.of(new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>());
    public static Map<String, Map<Integer, CycleValuePatternStats>> patternSupplyPercentageMap = new HashMap<>();

    static {
        List<CycleValuePattern> cycleValuePatterns = List.of(
                new CycleValuePattern(List.of(
                        new CycleValue(Supply.INSUFFICIENT, DemandShift.SKYROCKETING),
                        new CycleValue(Supply.NONEXISTENT, DemandShift.SKYROCKETING),
                        new CycleValue(Supply.SUFFICIENT, DemandShift.PLUMMETING),
                        new CycleValue(Supply.SUFFICIENT, DemandShift.NONE),
                        new CycleValue(Supply.SUFFICIENT, DemandShift.NONE),
                        new CycleValue(Supply.SUFFICIENT, DemandShift.NONE),
                        new CycleValue(Supply.SUFFICIENT, DemandShift.NONE)
                ), 4),
                new CycleValuePattern(List.of(
                        new CycleValue(Supply.INSUFFICIENT, DemandShift.INCREASING),
                        new CycleValue(Supply.INSUFFICIENT, DemandShift.INCREASING),
                        new CycleValue(Supply.SUFFICIENT, DemandShift.PLUMMETING),
                        new CycleValue(Supply.SUFFICIENT, DemandShift.NONE),
                        new CycleValue(Supply.SUFFICIENT, DemandShift.NONE),
                        new CycleValue(Supply.SUFFICIENT, DemandShift.NONE),
                        new CycleValue(Supply.SUFFICIENT, DemandShift.NONE)
                ), 4),
                new CycleValuePattern(List.of(
                        new CycleValue(Supply.SUFFICIENT, DemandShift.NONE),
                        new CycleValue(Supply.INSUFFICIENT, DemandShift.SKYROCKETING),
                        new CycleValue(Supply.NONEXISTENT, DemandShift.SKYROCKETING),
                        new CycleValue(Supply.SUFFICIENT, DemandShift.PLUMMETING),
                        new CycleValue(Supply.SUFFICIENT, DemandShift.NONE),
                        new CycleValue(Supply.SUFFICIENT, DemandShift.NONE),
                        new CycleValue(Supply.SUFFICIENT, DemandShift.NONE)
                ), 4),
                new CycleValuePattern(List.of(
                        new CycleValue(Supply.SUFFICIENT, DemandShift.NONE),
                        new CycleValue(Supply.INSUFFICIENT, DemandShift.INCREASING),
                        new CycleValue(Supply.INSUFFICIENT, DemandShift.INCREASING),
                        new CycleValue(Supply.SUFFICIENT, DemandShift.PLUMMETING),
                        new CycleValue(Supply.SUFFICIENT, DemandShift.NONE),
                        new CycleValue(Supply.SUFFICIENT, DemandShift.NONE),
                        new CycleValue(Supply.SUFFICIENT, DemandShift.NONE)
                ), 4),
                new CycleValuePattern(List.of(
                        new CycleValue(Supply.SUFFICIENT, DemandShift.NONE),
                        new CycleValue(Supply.SUFFICIENT, DemandShift.NONE),
                        new CycleValue(Supply.INSUFFICIENT, DemandShift.SKYROCKETING),
                        new CycleValue(Supply.NONEXISTENT, DemandShift.SKYROCKETING),
                        new CycleValue(Supply.SUFFICIENT, DemandShift.PLUMMETING),
                        new CycleValue(Supply.SUFFICIENT, DemandShift.NONE),
                        new CycleValue(Supply.SUFFICIENT, DemandShift.NONE)
                ), 4),
                new CycleValuePattern(List.of(
                        new CycleValue(Supply.SUFFICIENT, DemandShift.NONE),
                        new CycleValue(Supply.SUFFICIENT, DemandShift.NONE),
                        new CycleValue(Supply.INSUFFICIENT, DemandShift.INCREASING),
                        new CycleValue(Supply.INSUFFICIENT, DemandShift.INCREASING),
                        new CycleValue(Supply.SUFFICIENT, DemandShift.PLUMMETING),
                        new CycleValue(Supply.SUFFICIENT, DemandShift.NONE),
                        new CycleValue(Supply.SUFFICIENT, DemandShift.NONE)
                ), 4),
                new CycleValuePattern(List.of(
                        new CycleValue(Supply.SUFFICIENT, DemandShift.NONE),
                        new CycleValue(Supply.SUFFICIENT, DemandShift.NONE),
                        new CycleValue(Supply.SUFFICIENT, DemandShift.NONE),
                        new CycleValue(Supply.INSUFFICIENT, DemandShift.SKYROCKETING),
                        new CycleValue(Supply.NONEXISTENT, DemandShift.SKYROCKETING),
                        new CycleValue(Supply.SUFFICIENT, DemandShift.PLUMMETING),
                        new CycleValue(Supply.SUFFICIENT, DemandShift.NONE)
                ), 4),
                new CycleValuePattern(List.of(
                        new CycleValue(Supply.SUFFICIENT, DemandShift.NONE),
                        new CycleValue(Supply.SUFFICIENT, DemandShift.NONE),
                        new CycleValue(Supply.SUFFICIENT, DemandShift.NONE),
                        new CycleValue(Supply.INSUFFICIENT, DemandShift.INCREASING),
                        new CycleValue(Supply.INSUFFICIENT, DemandShift.INCREASING),
                        new CycleValue(Supply.SUFFICIENT, DemandShift.PLUMMETING),
                        new CycleValue(Supply.SUFFICIENT, DemandShift.NONE)
                ), 4),
                new CycleValuePattern(List.of(
                        new CycleValue(Supply.SUFFICIENT, DemandShift.NONE),
                        new CycleValue(Supply.INSUFFICIENT, DemandShift.NONE),
                        new CycleValue(Supply.SUFFICIENT, DemandShift.PLUMMETING),
                        new CycleValue(Supply.SUFFICIENT, DemandShift.SKYROCKETING),
                        new CycleValue(Supply.INSUFFICIENT, DemandShift.SKYROCKETING),
                        new CycleValue(Supply.NONEXISTENT, DemandShift.SKYROCKETING),
                        new CycleValue(Supply.SUFFICIENT, DemandShift.PLUMMETING)
                ), 4),
                new CycleValuePattern(List.of(
                        new CycleValue(Supply.SUFFICIENT, DemandShift.NONE),
                        new CycleValue(Supply.INSUFFICIENT, DemandShift.NONE),
                        new CycleValue(Supply.SUFFICIENT, DemandShift.DECREASING),
                        new CycleValue(Supply.SUFFICIENT, DemandShift.INCREASING),
                        new CycleValue(Supply.INSUFFICIENT, DemandShift.INCREASING),
                        new CycleValue(Supply.INSUFFICIENT, DemandShift.INCREASING),
                        new CycleValue(Supply.SUFFICIENT, DemandShift.PLUMMETING)
                ), 4),
                new CycleValuePattern(List.of(
                        new CycleValue(Supply.SUFFICIENT, DemandShift.NONE),
                        new CycleValue(Supply.INSUFFICIENT, DemandShift.NONE),
                        new CycleValue(Supply.SUFFICIENT, DemandShift.PLUMMETING),
                        new CycleValue(Supply.SUFFICIENT, DemandShift.NONE),
                        new CycleValue(Supply.SUFFICIENT, DemandShift.SKYROCKETING),
                        new CycleValue(Supply.INSUFFICIENT, DemandShift.SKYROCKETING),
                        new CycleValue(Supply.NONEXISTENT, DemandShift.SKYROCKETING)
                ), 5),
                new CycleValuePattern(List.of(
                        new CycleValue(Supply.SUFFICIENT, DemandShift.NONE),
                        new CycleValue(Supply.INSUFFICIENT, DemandShift.NONE),
                        new CycleValue(Supply.SUFFICIENT, DemandShift.PLUMMETING),
                        new CycleValue(Supply.SUFFICIENT, DemandShift.INCREASING),
                        new CycleValue(Supply.SUFFICIENT, DemandShift.INCREASING),
                        new CycleValue(Supply.INSUFFICIENT, DemandShift.INCREASING),
                        new CycleValue(Supply.INSUFFICIENT, DemandShift.INCREASING)
                ), 5)
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
