package de.web.spo.ff14.model;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class CycleValues {

    private final Map<Product, CycleValue> cycleValueMap = new HashMap<>();

    public void addCycleValue(Product product, CycleValue cycleValue) {
        cycleValueMap.put(product, cycleValue);
    }

}
