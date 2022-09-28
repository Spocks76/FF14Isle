package de.web.spo.ff14.service;

import de.web.spo.ff14.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AgendaService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final Random rand = new Random();

    private final WeeklyProducts weeklyProducts;


    public AgendaService(WeeklyProducts weeklyProducts) {
        this.weeklyProducts = weeklyProducts;
    }

    private void addProductToAgenda(Map<Product, CycleValuePattern> cycleValuePatternMap, int cycleIndex, Agenda agenda, int hour) {
        if(agenda.getHour() > hour) {
            return;
        }
        List<Product> availableProducts;
        if(agenda.getLastProduct() == null) {
            availableProducts = List.of(Product.values());
        } else {
            availableProducts = new ArrayList<>();
            if(agenda.getHour() == 20 || agenda.getHour() <= 16) {
                agenda.getLastProduct().getCategories().forEach(category -> availableProducts.addAll(Product.categoryTimeMap.getMap().get(category.name()).getMap().get(4)));
            }
            if(agenda.getHour() == 18 || agenda.getHour() <= 14) {
                agenda.getLastProduct().getCategories().forEach(category -> availableProducts.addAll(Product.categoryTimeMap.getMap().get(category.name()).getMap().get(6)));
            }
            if(agenda.getHour() == 16 || agenda.getHour() <= 12) {
                agenda.getLastProduct().getCategories().forEach(category -> availableProducts.addAll(Product.categoryTimeMap.getMap().get(category.name()).getMap().get(8)));
            }
            availableProducts.remove(agenda.getLastProduct());
        }
        if(availableProducts.size() > 0) {
            var product = availableProducts.get(rand.nextInt(availableProducts.size()));
            var supply = cycleIndex >= 0 ? cycleValuePatternMap.get(product).cycleValueList().get(cycleIndex).supply() : Supply.SUFFICIENT;
            agenda.addProduct(product, weeklyProducts.getProducts().get(product), supply);
        }
    }

    private void addProductFromTemplateToAgenda(Map<Product, CycleValuePattern> cycleValuePatternMap, int cycleIndex, Agenda templateAgenda, Agenda agenda, int hour) {
        if(agenda.getHour() > hour) {
            return;
        }

        var product = templateAgenda.getProductMap().get(hour);
        if(product != null) {
            var supply = cycleIndex >= 0 ? cycleValuePatternMap.get(product).cycleValueList().get(cycleIndex).supply() : Supply.SUFFICIENT;
            agenda.addProduct(product, weeklyProducts.getProducts().get(product), supply);
        }
    }
    private Agenda getRandomAgendaFromList(List<ValueAgendaCountList> valueAgendaCountLists) {
        var agendaCountList = new ArrayList<>(valueAgendaCountLists.get(rand.nextInt(valueAgendaCountLists.size())).getAgendaCountMap().values());
        var agendaCount = agendaCountList.get(rand.nextInt(agendaCountList.size()));
        return agendaCount.getAgenda();
    }

    public AgendaComb createRandomAgendaComb(List<ValueAgendaCountList> valueAgendaCountList, Map<Product, CycleValuePattern> cycleValuePatternMap, int cycleIndex, int startGroove) {

        Agenda tmplAgenda1 = getRandomAgendaFromList(valueAgendaCountList);
        Agenda tmplAgenda2 = getRandomAgendaFromList(valueAgendaCountList);
        Agenda tmplAgenda3 = tmplAgenda1;

        Groover groover = new Groover(startGroove);
        var agenda1 = new Agenda(groover);
        var agenda2 = new Agenda(groover);
        var agenda3 = new Agenda(groover);
        for(int h = 0; h<20; h+=2) {
            addProductFromTemplateToAgenda(cycleValuePatternMap, cycleIndex, tmplAgenda1, agenda1, h);
            addProductFromTemplateToAgenda(cycleValuePatternMap, cycleIndex, tmplAgenda2, agenda2, h);
            addProductFromTemplateToAgenda(cycleValuePatternMap, cycleIndex, tmplAgenda3, agenda3, h);
        }
        return new AgendaComb(groover, agenda1, agenda2, agenda3);
    }

    public Agenda createRandomAgenda(Map<Product, CycleValuePattern> cycleValuePatternMap, int cycleIndex) {
        var agenda = new Agenda(new Groover(0));
        for(int h = 0; h<20; h+=2) {
            addProductToAgenda(cycleValuePatternMap, cycleIndex, agenda, h);
        }
        return agenda;
    }

    private void addRandomTopAgendaSet(TreeMap<Integer, ValueAgendaCountList> treeMap, Map<Product, CycleValuePattern> cycleValuePatternMap) {
        for(var i=0;i<1000000;i++) {
            var agenda = this.createRandomAgenda(cycleValuePatternMap, 0);
            treeMap.computeIfAbsent(agenda.getValue(), ValueAgendaCountList::new).addAgenda(agenda);
            if(treeMap.size()>100) {
                treeMap.remove(treeMap.lastKey());
            }
        }
    }

    public TreeMap<Integer, ValueAgendaCountList> createRandomTopAgendaSet() {
        TreeMap<Integer, ValueAgendaCountList> treeMap = new TreeMap<>(Comparator.reverseOrder());
        addRandomTopAgendaSet(treeMap, Product.allSufficientCycleValuePatternMap);
        return treeMap;
    }
}

