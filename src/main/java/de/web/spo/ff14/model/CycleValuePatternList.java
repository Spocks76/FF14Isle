package de.web.spo.ff14.model;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public record CycleValuePatternList(String patternKey, List<CycleValuePattern> cycleValuePatternList) {

    public static Map<String, CycleValuePattern> peakCycleValuePatternMap;

    public static Map<String, Integer> season1SlotsMap;
    public static Map<String, Integer> season2SlotsMap;

    static {
        List<CycleValuePattern> cycleValuePatterns = List.of(
                new CycleValuePattern(List.of(
                        new CycleValue(1, Supply.INSUFFICIENT, DemandShift.SKYROCKETING, -8),
                        new CycleValue(2, Supply.NONEXISTENT, DemandShift.SKYROCKETING, -15),
                        new CycleValue(3, Supply.SUFFICIENT, DemandShift.PLUMMETING, 0),
                        new CycleValue(4, Supply.SUFFICIENT, DemandShift.NONE, 0),
                        new CycleValue(5, Supply.SUFFICIENT, DemandShift.NONE, 0),
                        new CycleValue(6, Supply.SUFFICIENT, DemandShift.NONE, 0),
                        new CycleValue(7, Supply.SUFFICIENT, DemandShift.NONE, 0)
                ), 4, "2S"),
                new CycleValuePattern(List.of(
                        new CycleValue(1, Supply.INSUFFICIENT, DemandShift.INCREASING, -4),
                        new CycleValue(2, Supply.INSUFFICIENT, DemandShift.INCREASING, -8),
                        new CycleValue(3, Supply.SUFFICIENT, DemandShift.PLUMMETING, 2),
                        new CycleValue(4, Supply.SUFFICIENT, DemandShift.NONE, 2),
                        new CycleValue(5, Supply.SUFFICIENT, DemandShift.NONE, 2),
                        new CycleValue(6, Supply.SUFFICIENT, DemandShift.NONE, 2),
                        new CycleValue(7, Supply.SUFFICIENT, DemandShift.NONE, 2)
                ), 4, "2W"),
                new CycleValuePattern(List.of(
                        new CycleValue(1, Supply.SUFFICIENT, DemandShift.NONE, 0),
                        new CycleValue(2, Supply.INSUFFICIENT, DemandShift.SKYROCKETING, -8),
                        new CycleValue(3, Supply.NONEXISTENT, DemandShift.SKYROCKETING, -15),
                        new CycleValue(4, Supply.SUFFICIENT, DemandShift.PLUMMETING, 0),
                        new CycleValue(5, Supply.SUFFICIENT, DemandShift.NONE, 0),
                        new CycleValue(6, Supply.SUFFICIENT, DemandShift.NONE, 0),
                        new CycleValue(7, Supply.SUFFICIENT, DemandShift.NONE, 0)
                ), 4, "3S"),
                new CycleValuePattern(List.of(
                        new CycleValue(1, Supply.SUFFICIENT, DemandShift.NONE, 0),
                        new CycleValue(2, Supply.INSUFFICIENT, DemandShift.INCREASING, -4),
                        new CycleValue(3, Supply.INSUFFICIENT, DemandShift.INCREASING, -8),
                        new CycleValue(4, Supply.SUFFICIENT, DemandShift.PLUMMETING, 2),
                        new CycleValue(5, Supply.SUFFICIENT, DemandShift.NONE, 2),
                        new CycleValue(6, Supply.SUFFICIENT, DemandShift.NONE, 2),
                        new CycleValue(7, Supply.SUFFICIENT, DemandShift.NONE, 2)
                ), 4, "3W"),
                new CycleValuePattern(List.of(
                        new CycleValue(1, Supply.SUFFICIENT, DemandShift.NONE,0),
                        new CycleValue(2, Supply.SUFFICIENT, DemandShift.NONE, 0),
                        new CycleValue(3, Supply.INSUFFICIENT, DemandShift.SKYROCKETING, -8),
                        new CycleValue(4, Supply.NONEXISTENT, DemandShift.SKYROCKETING, -15),
                        new CycleValue(5, Supply.SUFFICIENT, DemandShift.PLUMMETING, 0),
                        new CycleValue(6, Supply.SUFFICIENT, DemandShift.NONE, 0),
                        new CycleValue(7, Supply.SUFFICIENT, DemandShift.NONE, 0)
                ), 4, "4S"),
                new CycleValuePattern(List.of(
                        new CycleValue(1, Supply.SUFFICIENT, DemandShift.NONE, 0),
                        new CycleValue(2, Supply.SUFFICIENT, DemandShift.NONE, 0),
                        new CycleValue(3, Supply.INSUFFICIENT, DemandShift.INCREASING, -4),
                        new CycleValue(4, Supply.INSUFFICIENT, DemandShift.INCREASING, -8),
                        new CycleValue(5, Supply.SUFFICIENT, DemandShift.PLUMMETING, 2),
                        new CycleValue(6, Supply.SUFFICIENT, DemandShift.NONE, 2),
                        new CycleValue(7, Supply.SUFFICIENT, DemandShift.NONE, 2)
                ), 4, "4W"),
                new CycleValuePattern(List.of(
                        new CycleValue(1, Supply.SUFFICIENT, DemandShift.NONE, 0),
                        new CycleValue(2, Supply.SUFFICIENT, DemandShift.NONE, 0),
                        new CycleValue(3, Supply.SUFFICIENT, DemandShift.NONE, 0),
                        new CycleValue(4, Supply.INSUFFICIENT, DemandShift.SKYROCKETING, -8),
                        new CycleValue(5, Supply.NONEXISTENT, DemandShift.SKYROCKETING, -15),
                        new CycleValue(6, Supply.SUFFICIENT, DemandShift.PLUMMETING, 0),
                        new CycleValue(7, Supply.SUFFICIENT, DemandShift.NONE, 0)
                ), 4, "5S"),
                new CycleValuePattern(List.of(
                        new CycleValue(1, Supply.SUFFICIENT, DemandShift.NONE, 0),
                        new CycleValue(2, Supply.SUFFICIENT, DemandShift.NONE, 0),
                        new CycleValue(3, Supply.SUFFICIENT, DemandShift.NONE, 0),
                        new CycleValue(4, Supply.INSUFFICIENT, DemandShift.INCREASING, -4),
                        new CycleValue(5, Supply.INSUFFICIENT, DemandShift.INCREASING, -8),
                        new CycleValue(6, Supply.SUFFICIENT, DemandShift.PLUMMETING, 2),
                        new CycleValue(7, Supply.SUFFICIENT, DemandShift.NONE, 2)
                ), 4, "5W"),
                new CycleValuePattern(List.of(
                        new CycleValue(1, Supply.SUFFICIENT, DemandShift.NONE, 0),
                        new CycleValue(2, Supply.INSUFFICIENT, DemandShift.NONE, -1),
                        new CycleValue(3, Supply.SUFFICIENT, DemandShift.PLUMMETING, 7),
                        new CycleValue(4, Supply.SUFFICIENT, DemandShift.SKYROCKETING, 0),
                        new CycleValue(5, Supply.INSUFFICIENT, DemandShift.SKYROCKETING, -8),
                        new CycleValue(6, Supply.NONEXISTENT, DemandShift.SKYROCKETING, -15),
                        new CycleValue(7, Supply.SUFFICIENT, DemandShift.PLUMMETING, 0)
                ), 4, "6S"),
                new CycleValuePattern(List.of(
                        new CycleValue(1, Supply.SUFFICIENT, DemandShift.NONE, 0),
                        new CycleValue(2, Supply.INSUFFICIENT, DemandShift.NONE, -1),
                        new CycleValue(3, Supply.SUFFICIENT, DemandShift.DECREASING, 4),
                        new CycleValue(4, Supply.SUFFICIENT, DemandShift.INCREASING, 0),
                        new CycleValue(5, Supply.INSUFFICIENT, DemandShift.INCREASING, -4),
                        new CycleValue(6, Supply.INSUFFICIENT, DemandShift.INCREASING, -8),
                        new CycleValue(7, Supply.SUFFICIENT, DemandShift.PLUMMETING, 2)
                ), 4, "6W"),
                new CycleValuePattern(List.of(
                        new CycleValue(1, Supply.SUFFICIENT, DemandShift.NONE, 0),
                        new CycleValue(2, Supply.INSUFFICIENT, DemandShift.NONE, -1),
                        new CycleValue(3, Supply.SUFFICIENT, DemandShift.PLUMMETING, 7),
                        new CycleValue(4, Supply.SUFFICIENT, DemandShift.NONE, 7),
                        new CycleValue(5, Supply.SUFFICIENT, DemandShift.SKYROCKETING, 0),
                        new CycleValue(6, Supply.INSUFFICIENT, DemandShift.SKYROCKETING, -8),
                        new CycleValue(7, Supply.NONEXISTENT, DemandShift.SKYROCKETING, -15)
                ), 5, "7S"),
                new CycleValuePattern(List.of(
                        new CycleValue(1, Supply.SUFFICIENT, DemandShift.NONE, 0),
                        new CycleValue(2, Supply.INSUFFICIENT, DemandShift.NONE, -1),
                        new CycleValue(3, Supply.SUFFICIENT, DemandShift.PLUMMETING, 7),
                        new CycleValue(4, Supply.SUFFICIENT, DemandShift.INCREASING, 4),
                        new CycleValue(5, Supply.SUFFICIENT, DemandShift.INCREASING, 0),
                        new CycleValue(6, Supply.INSUFFICIENT, DemandShift.INCREASING, -4),
                        new CycleValue(7, Supply.INSUFFICIENT, DemandShift.INCREASING, -8)
                ), 5, "7W")
        );

        peakCycleValuePatternMap = cycleValuePatterns.stream().collect(Collectors.toMap(CycleValuePattern::peak, cycleValuePattern -> cycleValuePattern));
        season1SlotsMap = cycleValuePatterns.stream().collect(Collectors.toMap(CycleValuePattern::peak, CycleValuePattern::count));
        season2SlotsMap = cycleValuePatterns.stream().collect(Collectors.toMap(CycleValuePattern::peak, cycleValuePattern -> 1));
    }

}
