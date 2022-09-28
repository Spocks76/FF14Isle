package de.web.spo.ff14.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
@AllArgsConstructor
@Getter
public enum DemandShift {
    SKYROCKETING("Skyrocketing"),
    INCREASING("Increasing"),
    NONE("None"),
    DECREASING("Decreasing"),
    PLUMMETING("Plummeting");

    public static final Map<String, DemandShift> demandShiftMap = Arrays.stream(values()).collect(Collectors.toMap(DemandShift::getName, demandShift -> demandShift));

    private final String name;

}
