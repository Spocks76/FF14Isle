package de.web.spo.ff14.service;

import de.web.spo.ff14.model.*;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

@Service
public class CycleService {

    private final WeeklyProducts weeklyProducts;

    public CycleService(WeeklyProducts weeklyProducts) {
        this.weeklyProducts = weeklyProducts;
    }

    public CycleResult<AgendaResult> getCycleResult(int cycle, Map<Integer, GrooveAgendaResults<AgendaResult>> agendaSet, ProductList productList, Map<Product, Integer> productCountMap, int startGroove) {
        var cycleResult = new CycleResult<AgendaResult>(cycle);

        agendaSet.values().forEach(grooveAgendaResults -> {
            var treeMapMin = new TreeMap<Integer, Map<String, AgendaResult>>(Comparator.reverseOrder());
            var treeMapMax = new TreeMap<Integer, Map<String, AgendaResult>>(Comparator.reverseOrder());
            grooveAgendaResults.maxResults().values().forEach(agendaResultMap -> agendaResultMap.values().stream().map(AgendaResult::agenda)
                    .map(agenda -> agenda.calculateProduct(weeklyProducts, productList, productCountMap, cycleResult.getCycle(), startGroove))
                    .forEach(agendaResult -> {
                        treeMapMin.computeIfAbsent(agendaResult.minValue(), minValue -> new HashMap<>()).put(agendaResult.getProductProducedKey(), agendaResult);
                        treeMapMax.computeIfAbsent(agendaResult.maxValue(), maxValue -> new HashMap<>()).put(agendaResult.getProductProducedKey(), agendaResult);
                    }));
                    cycleResult.getGrooveAgendaResultsMap().put(grooveAgendaResults.groove(), new GrooveAgendaResults<>(grooveAgendaResults.groove(), treeMapMin, treeMapMax));
        });

        return cycleResult;
    }

    public AgendaComb checkAgendaList(AgendaComb agendaComb, CycleResult<AgendaResult> cycleResult, ProductList productList, int startCycle) {

        var maxAgendaComb = new AgendaComb(agendaComb);
        var maxAgendaComAtomic = new AtomicReference<>(maxAgendaComb);
        var maxValue = new AtomicInteger(maxAgendaComb.calculateProduct(weeklyProducts, productList, startCycle).maxValue());

        cycleResult.trim(1000);

        cycleResult.getAllResults().values().forEach(agendaResult -> {
            agendaComb.addAgenda(cycleResult.getCycle(), agendaResult.agenda());
            var actValue = agendaComb.calculateProduct(weeklyProducts, productList, startCycle).maxValue();
            if(actValue>  maxValue.get()) {
                maxAgendaComAtomic.set(new AgendaComb(agendaComb));
                maxValue.set(actValue);
            }
        });

        return maxAgendaComAtomic.get();
    }

    public CycleResult<AgendaCombResult> combineCycleResults(CycleResult<AgendaResult> cycleResult1, CycleResult<AgendaResult> cycleResult2, ProductList productList, int startCycle) {
        var cycleResult = new CycleResult<AgendaCombResult>(cycleResult1.getCycle());
        cycleResult1.trim(40);
        var allResults1 = cycleResult1.getAllResults().values();
        cycleResult2.trim(40);
        var allResults2 = cycleResult2.getAllResults().values();

        allResults1.forEach(agendaResult1 ->
            allResults2.forEach(agendaResult2 -> {
                var agendaComb = new AgendaComb();
                agendaComb.addAgenda(cycleResult1.getCycle(), agendaResult1.agenda());
                agendaComb.addAgenda(cycleResult2.getCycle(), agendaResult2.agenda());
                var agendaCombResult = agendaComb.calculateProduct(weeklyProducts, productList, startCycle);
                var groove = agendaCombResult.groove();
                var grooveAgendaResults = cycleResult.getGrooveAgendaResultsMap()
                        .computeIfAbsent(groove, k -> new GrooveAgendaResults<>(groove, new TreeMap<>(Comparator.reverseOrder()), new TreeMap<>(Comparator.reverseOrder())));
                grooveAgendaResults.minResults().computeIfAbsent(agendaCombResult.minValue(), minValue -> new HashMap<>()).put(agendaCombResult.getProductProducedKey(), agendaCombResult);
                grooveAgendaResults.maxResults().computeIfAbsent(agendaCombResult.maxValue(), maxValue -> new HashMap<>()).put(agendaCombResult.getProductProducedKey(), agendaCombResult);
                grooveAgendaResults.trim(1000);
            })
        );
        return cycleResult;
    }

    public AgendaComb checkAgendaList2(AgendaComb agendaComb, CycleResult<AgendaCombResult> cycleResult, ProductList productList, int startCycle) {

        var maxAgendaComb = new AgendaComb(agendaComb);
        var maxAgendaComAtomic = new AtomicReference<>(maxAgendaComb);
        var maxValue = new AtomicInteger(maxAgendaComb.calculateProduct(weeklyProducts, productList, startCycle).minValue());

        cycleResult.trim(1000);

        cycleResult.getAllResults().values().forEach(agendaCombResult -> {
            Stream.iterate(2, cycle -> cycle + 1).limit(6).forEach(cycle -> {
               var agenda = agendaCombResult.agendaComb().getAgenda(cycle);
               if(agenda != null) {
                   agendaComb.addAgenda(cycle, agenda);
               }
            });

            var actValue = agendaComb.calculateProduct(weeklyProducts, productList, startCycle).minValue();
            if(actValue>  maxValue.get()) {
                maxAgendaComAtomic.set(new AgendaComb(agendaComb));
                maxValue.set(actValue);
            }
        });

        return maxAgendaComAtomic.get();
    }

    public void addAgenda(AgendaComb agendaComb, int cycle, String productKey, Map<Integer, GrooveAgendaResults<AgendaResult>> agendaSet, ProductList productList) {
        var cycleResults = this.getCycleResult(cycle, agendaSet, productList, new HashMap<>(), 0);
        var agendaResult = cycleResults.getAllResults().get(productKey);
        agendaComb.addAgenda(cycle, agendaResult.agenda());
    }

    public AgendaComb calcCycles(AgendaComb agendaCombStart, int startCycle, int restDay, Map<Integer, GrooveAgendaResults<AgendaResult>> agendaSet, ProductList productList) {
        var agendaCombResult = agendaCombStart.calculateProduct(weeklyProducts, productList, startCycle);
        var productCountMap = agendaCombResult.productProducedMap();
        var startGroove = agendaCombResult.groove();
        var agendaComb = new AtomicReference<>(agendaCombStart);

        var cycleCompMap = new HashMap<Integer, List<List<CycleResult<AgendaResult>>>>();
        var cycleCompMap2 = new HashMap<Integer, List<List<CycleResult<AgendaResult>>>>();

        Stream.iterate(7, cycle -> cycle - 1).limit(8 - startCycle).filter(cycle -> cycle != restDay).forEach(cycle -> {
            Stream.iterate(cycle - 1, cycle2 -> cycle2 - 1).limit(cycle - startCycle).filter(cycle2 -> cycle2 != restDay).forEach(cycle2 -> {
                var list = new ArrayList<CycleResult<AgendaResult>>();
                cycleCompMap.computeIfAbsent(cycle, k -> new ArrayList<>()).add(list);
                cycleCompMap2.computeIfAbsent(cycle2, k -> new ArrayList<>()).add(list);
            });

            var cycleResults = this.getCycleResult(cycle, agendaSet, productList, productCountMap, startGroove);
            agendaComb.set(this.checkAgendaList(agendaComb.get(), cycleResults, productList, startCycle));
            Optional.ofNullable(cycleCompMap.get(cycle)).ifPresent(cycleResults2 -> cycleResults2.forEach(cycleResultsList -> cycleResultsList.add(cycleResults)));
            Optional.ofNullable(cycleCompMap2.get(cycle)).ifPresent(cycleResults2 -> cycleResults2.forEach(cycleResultsList -> cycleResultsList.add(cycleResults)));
        });
        cycleCompMap.values().forEach(ag -> ag.stream().filter(cycleResults -> cycleResults.size() == 2).forEach(cycleResultsList -> {
            var cycleResultsComb = this.combineCycleResults(cycleResultsList.get(0), cycleResultsList.get(1), productList, startCycle);
            agendaComb.set(this.checkAgendaList2(agendaComb.get(), cycleResultsComb, productList, startCycle));
            System.out.print(".");
        }));
        System.out.println();

        return agendaComb.get();
    }

}
