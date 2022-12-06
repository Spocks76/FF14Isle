package de.web.spo.ff14.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public enum Supply {

    NONEXISTENT("Nonexistent", 1.6, -100, -9),
    INSUFFICIENT("Insufficient", 1.3, -8, -1),
    SUFFICIENT("Sufficient", 1.0, 0, 7),
    SURPLUS("Surplus", 0.8, 8, 15),
    OVERFLOWING("Overflowing", 0.6, 16, 100);

    public static final Map<String, Supply> supplyMap = Arrays.stream(values()).collect(Collectors.toMap(Supply::getName, supply -> supply));
    public static Supply valueOf(int supplyValue) {
        return Arrays.stream(values()).filter(value -> supplyValue>=value.minSupplyValue && supplyValue <= value.maxSupplyValue).findFirst().orElse(SUFFICIENT);
    }

    private final String name;
    private final double modifier;
    private final int minSupplyValue;
    private final int maxSupplyValue;
}
