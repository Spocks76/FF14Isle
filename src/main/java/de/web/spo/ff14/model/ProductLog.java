package de.web.spo.ff14.model;

import lombok.Getter;

import java.util.Hashtable;
import java.util.Map;

@Getter
public class ProductLog {

    private final Map<Integer, Map<Product, Integer>> products = new Hashtable<>();

    public synchronized void addProduct(int hour, Product product, int count) {
        products.computeIfAbsent(hour + 1 , key -> new Hashtable<>()).merge(product, count, Integer::sum);
    }
}
