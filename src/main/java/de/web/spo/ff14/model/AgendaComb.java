package de.web.spo.ff14.model;

import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public record AgendaComb(Groover groover, Agenda agenda1, Agenda agenda2, Agenda agenda3, Map<Product, Integer> productCountMap) {
    public int getValue() {
        return agenda1().getValue() + agenda2.getValue() + agenda3().getValue();
    }
    public String getKey() {
        return agenda1().getProductKey().toString()+agenda2().getProductKey().toString()+agenda3.getProductKey().toString();
    }

    public String getKey2() {
        return agenda1().getProductKey2().toString();
    }

    public int getPercentage() {
        var percentageMap = new Hashtable<>(agenda1.getPercentageMap());
        percentageMap.putAll(agenda2.getPercentageMap());
        percentageMap.putAll(agenda3.getPercentageMap());
        AtomicReference<Double> percentage = new AtomicReference<>((double) 1);
        percentageMap.values().forEach(p -> percentage.set(percentage.get() * p / 100.0));
        return (int) Math.round(percentage.get() * 100);
    }
}
