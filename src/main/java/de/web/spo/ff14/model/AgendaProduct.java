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

    private final int produced;

    public AgendaProduct(Product productBefore, Product product, Popularity popularity, Supply supply, int groove, int produced) {
        this.product = product;
        this.popularity = popularity;
        this.supply = supply;
        this.groove = groove;
        this.produced = produced;
        if(productBefore != null && !product.equals(productBefore)) {
            product.getCategories().forEach(category -> {
                if(productBefore.getCategories().contains(category)) {
                    efficiencyBonus = 2;
                }
            });
        }
    }

    public double getValue() {
        var supply = this.supply;
        if(produced > 7) {
            supply = Supply.values()[supply.ordinal()+1];
        }
        if(produced > 15) {
            supply = Supply.values()[supply.ordinal()+1];
        }
        return Math.floor(popularity.getModifier() * 100 * supply.getModifier() * 100 * Math.floor(product.getValue() * 1.2 * (1.0 + groove / 100.0)) / 10000) * efficiencyBonus;
    }
}
