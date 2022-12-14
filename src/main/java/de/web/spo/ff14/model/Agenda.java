package de.web.spo.ff14.model;

import lombok.Getter;
import lombok.ToString;

import java.util.*;

@ToString
@Getter
public class Agenda {
    private int value=0;
    private int hour = 0;
    private final List<AgendaProduct> productList = new ArrayList<>();
    private Product lastProduct;
    private final Groover groover;
    private final ProductLog productLog;
    private final StringBuffer productKey = new StringBuffer();
    private final StringBuffer productKey2 = new StringBuffer();
    private final Map<Integer, Product> productMap = new HashMap<>();

    private final Map<Product, Integer> percentageMap = new HashMap<>();
    public Agenda(Groover groover, ProductLog productLog) {
        this.groover = groover;
        this.productLog = productLog;
    }
    public void addProduct(Product product, Popularity popularity, int supplyValue, int produced, int percentage, String peak) {
        if(hour + product.getDuration() > 24) {
            return;
        }
        var agendaProduct = new AgendaProduct(lastProduct, product, popularity, supplyValue, groover.getGroove(hour), produced);
        productList.add(agendaProduct);
        productKey.append(product.getName()).append(", ");
        productKey2.append(product.getName()).append("(").append(peak).append("), ");
        groover.addProduct(hour + product.getDuration() - 1);
        productLog.addProduct(hour + product.getDuration() - 1, product, agendaProduct.getEfficiencyBonus());
        productMap.put(hour, product);
        percentageMap.put(product, percentage);
        hour +=product.getDuration();
        lastProduct=product;
        value+=agendaProduct.getValue();
    }
}
