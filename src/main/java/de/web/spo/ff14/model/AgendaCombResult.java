package de.web.spo.ff14.model;

import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;

public record AgendaCombResult(AgendaComb agendaComb, int minValue, int medianValue, int maxValue, Map<Product, Integer> productProducedMap, int groove, String agendaCombStr) implements Result {
    public String getProductProducedKey() {
        return productProducedMap.keySet().stream().sorted(Comparator.comparing(Product::getName)).map(product -> product.getName()+":"+productProducedMap.get(product)).collect(Collectors.joining(","));
    }

    public String toString() {
        return "Min: " + minValue + ", Median: " + medianValue + ", Max: " + maxValue + ", AgendaComb: " + agendaCombStr;
    }

    @Override
    public String getProductKey() {
        return agendaComb.getProductStr();
    }
}
