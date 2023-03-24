package de.web.spo.ff14.model;


import java.util.HashMap;
import java.util.Map;


public class ProductList {
    private final Map<Product, ProductSupplyList> productSupplyListMap = new HashMap<>();

    public ProductSupplyList get(Product key) {
        return productSupplyListMap.get(key);
    }

    public void put(Product key, ProductSupplyList value) {
        productSupplyListMap.put(key, value);
    }
}
