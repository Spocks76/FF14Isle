package de.web.spo.ff14.service;

import de.web.spo.ff14.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Stream;
@Service
public class AgendaService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final WeeklyProducts weeklyProducts;

    public AgendaService(WeeklyProducts weeklyProducts) {
        this.weeklyProducts = weeklyProducts;
    }

    private void addProductToAgenda(Map<Product, Integer> productCountMap, Agenda agenda, int hour, Product product) {
        if(agenda.getHour() > hour
                || hour + product.getDuration() > 24
                || product.equals(agenda.getLastProduct())
                || agenda.getLastProduct() != null && agenda.getLastProduct().getCategories().stream().noneMatch(category -> product.getCategories().contains(category))) {
            return;
        }
        var cycleValueStats = new CycleValueStats(new CycleValue(Supply.SUFFICIENT, DemandShift.NONE, 0), new CycleValuePatternStats(""));
        var peak =  "";
        var produced = productCountMap.getOrDefault(product, 0);
        var supply = cycleValueStats.cycleValue().supply();
        var supplyValue =  cycleValueStats.cycleValue().supplyValue();
        var weeklyProduct = weeklyProducts.getProducts().get(product);
        agenda.addProduct(product, weeklyProduct.popularity(), supplyValue, produced, -1, peak);
    }

    private void addProductFromTemplateToAgenda(Cycle cycle, Map<Product, String> productPeakMap, Map<Product, Integer> productCountMap, Agenda agendaTemplate, Agenda agenda, int hour) {
        if(agenda.getHour() > hour) {
            return;
        }

        var product = agendaTemplate.getProductMap().get(hour);
        if(product != null) {
            var producCycleValue = cycle.getProductCycleValueMap().get(product);
            var cycleValue = producCycleValue.cycleValue();
            var cycleValuePatternStats = producCycleValue.cycleValuePatternStats();
            var peak =  productPeakMap.get(product);
            var produced = productCountMap.getOrDefault(product, 0);
            var supply = cycleValue.supply();
            var supplyValue = cycleValue.supplyValue();
            var weeklyProduct = weeklyProducts.getProducts().get(product);
            agenda.addProduct(product, weeklyProduct.popularity(), supplyValue, produced, cycleValuePatternStats.getPercentage(supply), peak);
        }
    }

    public AgendaComb createAgendaComb(Cycle cycle, Map<Product, String> productPeakMap, Map<Product, Integer> productCountMap, Agenda agendaTemplate, int startGroove) {
        ProductLog productLog = new ProductLog();
        Groover groover = new Groover(startGroove);
        var agenda1 = new Agenda(groover, productLog);
        var agenda2 = new Agenda(groover, productLog);
        var agenda3 = new Agenda(groover, productLog);
        Stream.iterate(0, hour -> hour + 2)
                .limit(13)
                .forEach(hour ->  {
                    productLog.getProducts().getOrDefault(hour, new HashMap<>()).forEach((product, count) -> productCountMap.merge(product, count, Integer::sum));
                    addProductFromTemplateToAgenda(cycle, productPeakMap, productCountMap, agendaTemplate, agenda1, hour);
                    addProductFromTemplateToAgenda(cycle, productPeakMap, productCountMap, agendaTemplate, agenda2, hour);
                    addProductFromTemplateToAgenda(cycle, productPeakMap, productCountMap, agendaTemplate, agenda3, hour);
                });
        return new AgendaComb(cycle, groover, agenda1, agenda2, agenda3, productCountMap);
    }

    private ArrayList<Product> getAvailableProducts(Product product, int duration) {
        var availableProducts = new ArrayList<Product>();

        if(duration == 20 || duration <= 16) {
            product.getCategories().forEach(category -> availableProducts.addAll(Product.categoryTimeMap.getMap().get(category.name()).getMap().get(4)));
        }
        if(duration == 18 || duration <= 14) {
            product.getCategories().forEach(category -> availableProducts.addAll(Product.categoryTimeMap.getMap().get(category.name()).getMap().get(6)));
        }
        if(duration == 16 || duration <= 12) {
            product.getCategories().forEach(category -> availableProducts.addAll(Product.categoryTimeMap.getMap().get(category.name()).getMap().get(8)));
        }
        availableProducts.remove(product);
        return availableProducts;
    }

    private void createAgendaWithProducts(Map<Integer, GrooveValueAgendaMap> grooveValueAgendaMapMap, List<Product> products) {
        var productCountMap = new HashMap<Product, Integer>();
        var agenda = new Agenda(new Groover(0), new ProductLog());
        products.forEach(product -> addProductToAgenda(productCountMap, agenda, agenda.getHour(), product));
        if(agenda.getHour() == 24) {
            grooveValueAgendaMapMap.computeIfAbsent(agenda.getGroover().getLastGroove(), groove -> new GrooveValueAgendaMap(groove, 250)).addAgenda(agenda);
        }
    }

    public Map<Integer, GrooveValueAgendaMap> createRandomTopAgendaSet() {
        Map<Integer, GrooveValueAgendaMap> grooveValueAgendaMapMap = new HashMap<>();
        weeklyProducts.getProducts().keySet().forEach(product1 -> {
            var dur1 = product1.getDuration();
            getAvailableProducts(product1, dur1).forEach(product2 -> {
                var dur2 = dur1 + product2.getDuration();
                getAvailableProducts(product2, dur2).forEach(product3 -> {
                    var dur3 = dur2 + product3.getDuration();
                    if (dur3 == 24) {
                        createAgendaWithProducts(grooveValueAgendaMapMap, List.of(product1, product2, product3));
                    }
                    getAvailableProducts(product3, dur3).forEach(product4 -> {
                        var dur4 = dur3 + product4.getDuration();
                        if (dur4 == 24) {
                            createAgendaWithProducts(grooveValueAgendaMapMap, List.of(product1, product2, product3, product4));
                        }
                        getAvailableProducts(product4, dur4).forEach(product5 -> {
                            var dur5 = dur4 + product5.getDuration();
                            if (dur5 == 24) {
                                createAgendaWithProducts(grooveValueAgendaMapMap, List.of(product1, product2, product3, product4, product5));
                            }
                            getAvailableProducts(product5, dur5).forEach(product6 -> {
                                var dur6 = dur5 + product6.getDuration();
                                if (dur6 == 24) {
                                    createAgendaWithProducts(grooveValueAgendaMapMap, List.of(product1, product2, product3, product4, product5, product6));
                                }
                            });
                        });
                    });
                });
            });
        });

        return grooveValueAgendaMapMap;
    }

    public Agenda recreateTopAgendaFromTemplate(Cycle cycle, Map<Product, String> productPeakMap, Map<Product, Integer> productCountMap, int startGroove, Agenda agendaTemplate) {
        var agenda = new Agenda(new Groover(startGroove), new ProductLog());
        agendaTemplate.getProductList().forEach(agendaProduct -> {
            var product = agendaProduct.getProduct();
            var producCycleValue = cycle.getProductCycleValueMap().get(product);
            var cycleValue = producCycleValue.cycleValue();
            var cycleValuePatternStats = producCycleValue.cycleValuePatternStats();
            var peak =  productPeakMap.get(product);
            var produced = productCountMap.getOrDefault(product, 0);
            var supply = cycleValue.supply();
            var supplyValue = cycleValue.supplyValue();
            var weeklyProduct = weeklyProducts.getProducts().get(product);
            agenda.addProduct(product, weeklyProduct.popularity(), supplyValue, produced, cycleValuePatternStats.getPercentage(supply), peak);
        });
        return agenda;
    }

    public void resortTopAgendaSetForCyclePattern(PeakCombCycles peakCombCycles, Map<Integer, GrooveValueAgendaMap> grooveValueAgendaMapMapOld) {
        Stream.iterate(peakCombCycles.getCycles().getStartCycle(), cycleNr -> cycleNr + 1).limit(8-peakCombCycles.getCycles().getStartCycle()).forEach(cycleNr -> {
            var cycle = peakCombCycles.getCycles().getCycle(cycleNr);
            Map<Integer, GrooveValueAgendaMap> grooveValueAgendaMapMap = new Hashtable<>();
            grooveValueAgendaMapMapOld.values().forEach(grooveValueAgendaMap -> grooveValueAgendaMap.getValueAgendaCountMap().values().forEach(valueAgendaCountMap -> valueAgendaCountMap.getAgendaCountMap().values()
                    .forEach(agendaCount -> {
                        var agenda = recreateTopAgendaFromTemplate(cycle, peakCombCycles.getProductPeakMap(), peakCombCycles.getProductCountMap(), 0, agendaCount.getAgenda());
                        grooveValueAgendaMapMap.computeIfAbsent(agenda.getGroover().getLastGroove(), groove -> new GrooveValueAgendaMap(groove, 10)).addAgenda(agenda);
                    })
            ));
            var agendaList = new ArrayList<Agenda>();
            grooveValueAgendaMapMap.values().forEach(grooveValueAgendaMap -> grooveValueAgendaMap.getValueAgendaCountMap().values().forEach(valueAgendaCountMap -> valueAgendaCountMap.getAgendaCountMap().values().forEach(agendaCount -> agendaList.add(agendaCount.getAgenda()))));
            cycle.setAgendaList(agendaList);
        });
    }
}

