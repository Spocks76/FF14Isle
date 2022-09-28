package de.web.spo.ff14.model;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class AgendaProduct {

    private int efficiencyBonus = 1;
    private final Product product;

    private final Popularity popularity;

    private final Supply supply;

    private final int groove;

    public AgendaProduct(Product productBefore, Product product, Popularity popularity, Supply supply, int groove) {
        this.product = product;
        this.popularity = popularity;
        this.supply = supply;
        this.groove = groove;
        if(productBefore != null && !product.equals(productBefore)) {
            product.getCategories().forEach(category -> {
                if(productBefore.getCategories().contains(category)) {
                    efficiencyBonus = 2;
                }
            });
        }
    }

    public double getValue() {
        return Math.floor(popularity.getModifier() * supply.getModifier() * Math.floor(product.getValue() * 1.2 * (1 + groove / 100.0))) * efficiencyBonus;
    }
}