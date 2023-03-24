package de.web.spo.ff14.model;

import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;

public record AgendaResult(int cycle, Agenda agenda, int minValue, int medianValue, String medianProbabilityStr,  int maxValue, String maxProbabilityStr, Map<Product, Integer> productProducedMap, int grooveAfter) implements Result {
    public String getProductProducedKey() {
       return productProducedMap.keySet().stream().sorted(Comparator.comparing(Product::getName)).map(product -> product.getName()+":"+productProducedMap.get(product)).collect(Collectors.joining(","));
    }

    public String toString() {
        var medStr = "";
        if(medianValue > minValue && medianValue < maxValue) {
            medStr = ", " + medianProbabilityStr + "%: " + medianValue;
        }
        return "Cycle " + cycle + ", Min: " + minValue + medStr + ", Max: " + maxValue + " (" + maxProbabilityStr + "%)" + ", Agenda: " + agenda.getProductStr();
    }

    @Override
    public String getProductKey() {
        return agenda.getProductStr();
    }
}
