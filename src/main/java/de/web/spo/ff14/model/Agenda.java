package de.web.spo.ff14.model;

import lombok.Getter;
import lombok.ToString;

import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@ToString
@Getter
public class Agenda {
    private int hour = 0;
    private final List<AgendaProduct> productList = new ArrayList<>();
    private final Set<Product> productSet = new HashSet<>();
    private Product lastProduct;
    private final Groover groover;
    private String productStr;
    private int maxDuration = 0;

    public Agenda(Groover groover) {
        this.groover = groover;
    }

    public void addProduct(Product product) {
        if(hour + product.getDuration() > 24) {
            return;
        }
        var agendaProduct = new AgendaProduct(lastProduct, product, groover.getGroove(hour));
        productList.add(agendaProduct);
        productSet.add(product);

        groover.addProduct(hour + product.getDuration() - 1);
        hour +=product.getDuration();
        lastProduct=product;
        maxDuration =  Math.max(maxDuration, product.getDuration());

        productStr = productList.stream().map(AgendaProduct::getProduct).map(prod -> prod.getName() + " (" + prod.getDuration() +"h)").collect(Collectors.joining(", "));
    }

    public AgendaResult calculateProduct(WeeklyProducts weeklyProducts, ProductList products, Map<Product, Integer> productProducedBeforeMap, int cycle, int startGroove) {

        var probFormat = new DecimalFormat("0.#");

        AtomicInteger minValue = new AtomicInteger();
        AtomicInteger medianValue = new AtomicInteger();
        AtomicInteger maxValue = new AtomicInteger();
        AtomicReference<Double> medianProbability = new AtomicReference<>(1.0);
        AtomicReference<Double> maxProbability = new AtomicReference<>(1.0);

        var productProducedAfterMap = productProducedBeforeMap == null ? new HashMap<Product, Integer>() : new HashMap<>(productProducedBeforeMap);

        productSet.forEach(product -> {
            var supplyCountListOptional = Optional.ofNullable(products)
                    .map(products1 -> products1.get(product))
                    .map(productSupplyList -> productSupplyList.get(cycle))
                    .map(CycleSupply::supplyCountList);

            int supplyProbabilityMedian = supplyCountListOptional
                    .map(SupplyCountList::getMedianSupply)
                    .map(SupplyCount::getProbability)
                    .orElse(0);
            medianProbability.set(medianProbability.get() * supplyProbabilityMedian / 100.0);

            int supplyProbabilityMax = supplyCountListOptional
                    .map(SupplyCountList::getMinSupply)
                    .map(SupplyCount::getProbability)
                    .orElse(0);
            maxProbability.set(maxProbability.get() * supplyProbabilityMax / 100.0);
        });

        productList.forEach(agendaProduct -> {
            int producedBefore = Objects.requireNonNullElse(productProducedAfterMap.get(agendaProduct.getProduct()), 0);
            var supplyCountListOptional = Optional.ofNullable(products)
                    .map(products1 -> products1.get(agendaProduct.getProduct()))
                    .map(productSupplyList -> productSupplyList.get(cycle))
                    .map(CycleSupply::supplyCountList);

            int supplyValueMin =
                    supplyCountListOptional
                    .map(SupplyCountList::getMaxSupply)
                    .map(SupplyCount::getSupply)
                    .orElse(0);

            var supplyValueMedian = supplyCountListOptional
                    .map(SupplyCountList::getMedianSupply)
                    .map(SupplyCount::getSupply)
                    .orElse(0);

            var supplyValueMax = supplyCountListOptional
                    .map(SupplyCountList::getMinSupply)
                    .map(SupplyCount::getSupply)
                    .orElse(0);

            minValue.addAndGet(
                    weeklyProducts.get(agendaProduct.getProduct())
                            .calculateValue(
                                    supplyValueMin,
                                    producedBefore,
                                    Math.min(startGroove + agendaProduct.getGroove(),35),
                                    agendaProduct.getEfficiencyBonus()
                            )
            );
            medianValue.addAndGet(
                    weeklyProducts.get(agendaProduct.getProduct())
                            .calculateValue(
                                    supplyValueMedian,
                                    producedBefore,
                                    Math.min(startGroove + agendaProduct.getGroove(),35),
                                    agendaProduct.getEfficiencyBonus()
                            )
            );
            maxValue.addAndGet(
                    weeklyProducts.get(agendaProduct.getProduct())
                            .calculateValue(
                                    supplyValueMax,
                                    producedBefore,
                                    Math.min(startGroove + agendaProduct.getGroove(),35),
                                    agendaProduct.getEfficiencyBonus()
                            )
            );
            productProducedAfterMap.put(agendaProduct.getProduct(), producedBefore+3*agendaProduct.getEfficiencyBonus());
        });

        return new AgendaResult(cycle, this, minValue.get(), medianValue.get(), probFormat.format(medianProbability.get() * 100.0), maxValue.get(), probFormat.format(maxProbability.get() * 100.0), productProducedAfterMap, Math.min(startGroove + groover.getLastGroove(),35));
    }

}
