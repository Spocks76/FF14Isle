package de.web.spo.ff14.model;

import lombok.Getter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Getter
public class ProductCategoryTime {

    private final Map<String, ProductTime> map = new HashMap<>();

    public ProductCategoryTime(Product[] products) {
        Arrays.stream(products).forEach(this::addProduct);
    }

    public void addProduct(Product product) {
        product.getCategories().forEach(category -> map.computeIfAbsent(category.name(), categoryName -> new ProductTime()).addProduct(product));
    }
}
