package de.web.spo.ff14.model;

import lombok.Getter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
public class PeakCombCycles {

    private final Cycles cycles;

    private final Map<Product, String> productPeakMap = new HashMap<>();

    private final Map<Product, Integer> productCountMap;

    public PeakCombCycles(int startCycle, Map<Product, Integer> productCountMap) {
        cycles = new Cycles(startCycle);
        this.productCountMap = productCountMap;
    }

    public String getProductPeaks() {
        return Arrays.stream(Product.values()).map(productPeakMap::get).collect(Collectors.joining(","));
    }

}
