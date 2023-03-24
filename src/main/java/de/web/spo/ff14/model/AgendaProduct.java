package de.web.spo.ff14.model;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class AgendaProduct {

    private int efficiencyBonus = 1;
    private final Product product;
    private final int groove;

    public AgendaProduct(Product productBefore, Product product, int groove) {
        this.product = product;
        this.groove = groove;
        if(productBefore != null && !product.equals(productBefore)) {
            product.getCategories().forEach(category -> {
                if(productBefore.getCategories().contains(category)) {
                    efficiencyBonus = 2;
                }
            });
        }
    }
}
