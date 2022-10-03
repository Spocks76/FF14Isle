package de.web.spo.ff14.model;

import java.util.Map;

public record AgendaComb(Groover groover, Agenda agenda1, Agenda agenda2, Agenda agenda3, Map<Product, Integer> productCountMap) {
    public int getValue() {
        return agenda1().getValue() + agenda2.getValue() + agenda3().getValue();
    }
    public String getKey() {
        return agenda1().getProductKey().toString()+agenda2().getProductKey().toString()+agenda3.getProductKey().toString();
    }
}
