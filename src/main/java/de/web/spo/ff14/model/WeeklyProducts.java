package de.web.spo.ff14.model;

import lombok.Getter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@Getter
@ToString
public class WeeklyProducts {
    private final Map<Product, Popularity> products = new HashMap<>();

    public void addProduct(Product product, Popularity popularity) {
        products.put(product, popularity);
    }
}
