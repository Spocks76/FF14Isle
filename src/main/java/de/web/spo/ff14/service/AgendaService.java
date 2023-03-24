package de.web.spo.ff14.service;

import de.web.spo.ff14.model.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;
@Service
public class AgendaService {

    private final WeeklyProducts weeklyProducts;

    public AgendaService(WeeklyProducts weeklyProducts) {
        this.weeklyProducts = weeklyProducts;
    }

    private void addProductToAgenda(Agenda agenda, int hour, Product product) {
        if(agenda.getHour() > hour
                || hour + product.getDuration() > 24
                || product.equals(agenda.getLastProduct())
                || agenda.getLastProduct() != null && agenda.getLastProduct().getCategories().stream().noneMatch(category -> product.getCategories().contains(category))) {
            return;
        }
        agenda.addProduct(product);
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

    private void createAgendaWithProducts(CycleResult<AgendaResult> cycleResult, List<Product> products) {
        var groover = new Groover(0);
        var agenda1 = new Agenda(groover);
        var agenda2 = new Agenda(groover);
        var agenda3 = new Agenda(groover);
        products.forEach(product -> {
            addProductToAgenda(agenda1, agenda1.getHour(), product);
            addProductToAgenda(agenda2, agenda2.getHour(), product);
            addProductToAgenda(agenda3, agenda3.getHour(), product);
        });
        if(agenda1.getHour() == 24) {
            var agendaResult = agenda1.calculateProduct(weeklyProducts, null, null, 1, 0);
            cycleResult.getGrooveAgendaResultsMap()
                    .computeIfAbsent(agenda1.getGroover().getLastGroove(), groove -> new GrooveAgendaResults<>(groove, new TreeMap<>(), new TreeMap<>()))
                    .maxResults().computeIfAbsent(agendaResult.maxValue(), k -> new HashMap<>()).put(agendaResult.getProductKey(), agendaResult);
        }
    }

    public CycleResult<AgendaResult> createTopAgendaSet() {
        var cycleResult = new CycleResult<AgendaResult>(1);
        weeklyProducts.getProducts().keySet().forEach(product1 -> {
            var dur1 = product1.getDuration();
            getAvailableProducts(product1, dur1).forEach(product2 -> {
                var dur2 = dur1 + product2.getDuration();
                getAvailableProducts(product2, dur2).forEach(product3 -> {
                    var dur3 = dur2 + product3.getDuration();
                    if (dur3 == 24) {
                        createAgendaWithProducts(cycleResult, List.of(product1, product2, product3));
                    }
                    getAvailableProducts(product3, dur3).forEach(product4 -> {
                        var dur4 = dur3 + product4.getDuration();
                        if (dur4 == 24) {
                            createAgendaWithProducts(cycleResult, List.of(product1, product2, product3, product4));
                        }
                        getAvailableProducts(product4, dur4).forEach(product5 -> {
                            var dur5 = dur4 + product5.getDuration();
                            if (dur5 == 24) {
                                createAgendaWithProducts(cycleResult, List.of(product1, product2, product3, product4, product5));
                            }
                            getAvailableProducts(product5, dur5).forEach(product6 -> {
                                var dur6 = dur5 + product6.getDuration();
                                if (dur6 == 24) {
                                    createAgendaWithProducts(cycleResult, List.of(product1, product2, product3, product4, product5, product6));
                                }
                            });
                        });
                    });
                });
            });
        });

        return cycleResult;
    }


}

