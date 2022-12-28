package de.web.spo.ff14.model;

import lombok.Getter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@Getter
@ToString
public class WeeklyProducts {

    private final Map<Product, WeeklyProduct> products = new HashMap<>();

    public void addProduct(Product product, Popularity popularity, String peakKey) {
        products.put(product, new WeeklyProduct(product, popularity, peakKey));
    }
}
