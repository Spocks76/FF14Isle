package de.web.spo.ff14.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public enum Supply {

    NONEXISTENT("Nonexistent", 1.6),
    INSUFFICIENT("Insufficient", 1.3),
    SUFFICIENT("Sufficient", 1.0),
    SURPLUS("Surplus", 0.8),
    OVERFLOWING("Overflowing", 0.6);

    public static final Map<String, Supply> supplyMap = Arrays.stream(values()).collect(Collectors.toMap(Supply::getName, supply -> supply));

    private final String name;
    private final double modifier;
}
