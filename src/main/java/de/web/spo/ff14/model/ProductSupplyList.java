package de.web.spo.ff14.model;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public class ProductSupplyList {

    @Getter
    private final Product product;
    @Getter
    private final Map<Integer, CycleSupply> cycleSupplyMap = new HashMap<>();

    public ProductSupplyList(Product product) {
        this.product = product;
    }

    public CycleSupply get(Integer key) {
        return cycleSupplyMap.get(key);
    }

    public CycleSupply put(Integer key, CycleSupply value) {
        return cycleSupplyMap.put(key, value);
    }
}
