package de.web.spo.ff14.model;

public record WeeklyProduct(Product product, Popularity popularity, String peakKey) {

    public int calculateValue(int supplyValue, int produced, int groove, int efficiencyBonus) {
        var supply = Supply.valueOf(supplyValue + produced);
        return (int) Math.floor(popularity.getModifier() * 100 * supply.getModifier() * 100 * Math.floor(product.getValue() * 1.2 * (1.0 + groove / 100.0)) / 10000) * 3 * efficiencyBonus;
    }

}
