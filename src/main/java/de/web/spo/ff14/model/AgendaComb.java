package de.web.spo.ff14.model;

import lombok.Getter;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AgendaComb {

    private List<Agenda> agendaList = Arrays.asList(new Agenda[7]);
    @Getter
    private String productStr;

    public AgendaComb() {}

    public AgendaComb(AgendaComb agendaComb) {
        agendaList = new ArrayList<>(agendaComb.agendaList);
        productStr = agendaComb.productStr;
    }

    public void addAgenda(int cycle, Agenda agenda) {
        agendaList.set(cycle - 1 , agenda);
        productStr = agendaList.stream().filter(Objects::nonNull).map(Agenda::getProductStr).collect(Collectors.joining(","));
    }

    public Agenda getAgenda(int cycle) {
        return agendaList.get(cycle - 1);
    }

    public AgendaCombResult calculateProduct(WeeklyProducts weeklyProducts, ProductList products, int startCycle) {
        AtomicInteger minValue = new AtomicInteger();
        AtomicInteger medianValue = new AtomicInteger();
        AtomicInteger maxValue = new AtomicInteger();

        AtomicInteger groove = new AtomicInteger(0);
        AtomicReference<Map<Product, Integer>> productProducedMap = new AtomicReference<>(new HashMap<>());
        List<AgendaResult> agendaResults = new ArrayList<>();
        Stream.iterate(1, cycle -> cycle + 1).limit(7).filter(cycle -> getAgenda(cycle) != null).forEach(cycle ->  {
            var agendaResult = getAgenda(cycle).calculateProduct(weeklyProducts, products, productProducedMap.get(), cycle, groove.get());
            var agendaResult2 = getAgenda(cycle).calculateProduct(weeklyProducts, products, productProducedMap.get(), cycle, 0);
            var treshold = 3250;
            if(cycle == startCycle && agendaResult.agenda().getMaxDuration() > 4) {
                treshold = 3600;
            }
            if(cycle == startCycle && agendaResult.agenda().getMaxDuration() > 6) {
                treshold = 3750;
            }
            if(agendaResult2.maxValue()>treshold) {
                agendaResults.add(agendaResult);
                minValue.set(minValue.get() + agendaResult.minValue());
                medianValue.set(medianValue.get() + agendaResult.medianValue());
                maxValue.set(maxValue.get() + agendaResult.maxValue());
                groove.set(agendaResult.grooveAfter());
                productProducedMap.set(agendaResult.productProducedMap());
            }
        });

        return new AgendaCombResult(this, minValue.get(), medianValue.get(), maxValue.get(), productProducedMap.get(), groove.get(), agendaResults.stream().map(AgendaResult::toString).collect(Collectors.joining(", ")));
    }

}
