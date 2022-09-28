package de.web.spo.ff14.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public record CycleValuePatternList(List<CycleValuePattern> cycleValuePatternList) {
    public static List<Map<String, CycleValuePatternList>> cycleValuePatternMap = List.of(new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>());

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
                    cycleValuePatternMap.get(cycleIndex).computeIfAbsent(CycleValuePattern.getPatternKeyUntil(cycleIndex + 1, cycleValuePattern.cycleValueList()), patternKey -> new CycleValuePatternList(new ArrayList<>())).cycleValuePatternList().add(cycleValuePattern);
                }
            }
        });

    }
}
