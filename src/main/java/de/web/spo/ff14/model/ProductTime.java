package de.web.spo.ff14.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
public class ProductTime {
    private final Map<Integer, List<Product>> map = Map.of(
            4, new ArrayList<>(),
            6, new ArrayList<>(),
            8, new ArrayList<>()
    );

    public void addProduct(Product product) {
        map.get(product.getDuration()).add(product);
    }

}
