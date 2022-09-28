package de.web.spo.ff14.model;

import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ToString
@Getter
public class Agenda {
    private int value=0;
    private int hour = 0;
    private final List<AgendaProduct> productList = new ArrayList<>();
    private Product lastProduct;
    private final Groover groover;
    private final StringBuffer productKey = new StringBuffer();
    private final Map<Integer, Product> productMap = new HashMap<>();
    public Agenda(Groover groover) {
        this.groover = groover;
    }
    public void addProduct(Product product, Popularity popularity, Supply supply) {
        if(hour + product.getDuration() > 24) {
            return;
        }
        var agendaProduct = new AgendaProduct(lastProduct, product, popularity, supply, groover.getGroove(hour));
        productList.add(agendaProduct);
        productKey.append(product.getName()).append(", ");
        groover.addProduct(hour + product.getDuration() - 1);
        productMap.put(hour, product);
        hour +=product.getDuration();
        lastProduct=product;
        value+=agendaProduct.getValue();
    }
}
