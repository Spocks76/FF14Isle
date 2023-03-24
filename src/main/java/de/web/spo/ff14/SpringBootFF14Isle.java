package de.web.spo.ff14;

import de.web.spo.ff14.model.*;
import de.web.spo.ff14.service.AgendaService;
import de.web.spo.ff14.service.CycleService;
import de.web.spo.ff14.service.ExcelService;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.*;
import java.util.stream.Stream;

@SpringBootApplication
public class SpringBootFF14Isle implements CommandLineRunner {

    private final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    public SpringBootFF14Isle(AgendaService agendaService, ExcelService excelService, CycleService cycleService) {
        this.agendaService = agendaService;
        this.excelService = excelService;
        this.cycleService = cycleService;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringBootFF14Isle.class, args);
    }

    private final AgendaService agendaService;

    private final ExcelService excelService;

    private final CycleService cycleService;

    private SupplyCountList createSupplyCountList(int supply) {
        var supplyCountList = new SupplyCountList();
        supplyCountList.addSupply(supply);
        return supplyCountList;
    }

    @Override
    public void run(String... args) throws Exception {

        var weeklyProducts = excelService.weeklyProducts();

        var agendaSet = agendaService.createTopAgendaSet().getGrooveAgendaResultsMap();

        var season1Slots = CycleValuePatternList.season1SlotsMap;
        var season2Slots = CycleValuePatternList.season2SlotsMap;

        var peakKeyMapping = excelService.peakKeyMapping();

        weeklyProducts.getProducts().values().forEach(weeklyProduct -> {
            if(weeklyProduct.product().getSeason() == 1) {
                var slots = season1Slots.get(weeklyProduct.peakKey());
                if(slots != null) {
                    season1Slots.put(weeklyProduct.peakKey(), slots -1);
                }
            }
            if(weeklyProduct.product().getSeason() == 2) {
                var slots = season2Slots.get(weeklyProduct.peakKey());
                if(slots != null) {
                    season2Slots.put(weeklyProduct.peakKey(), slots -1);
                }
            }
        });

        var productList = new ProductList();
        weeklyProducts.getProducts().values().forEach(weeklyProduct -> {
            var productSupplyList = new ProductSupplyList(weeklyProduct.product());
            productList.put(productSupplyList.getProduct(), productSupplyList);
            if(peakKeyMapping.containsKey(weeklyProduct.peakKey())) {
                peakKeyMapping.get(weeklyProduct.peakKey()).forEach(peak -> {
                    int slots = 0;
                    if(weeklyProduct.product().getSeason() == 1) {
                        slots = Objects.requireNonNullElse(season1Slots.get(peak),0);
                    }
                    if(weeklyProduct.product().getSeason() == 2) {
                        slots = Objects.requireNonNullElse(season2Slots.get(peak),0);
                    }
                    int slotsFinal = slots;
                    CycleValuePatternList.peakCycleValuePatternMap
                            .get(peak).cycleValueList()
                            .forEach(cycleValue ->
                                    productSupplyList.getCycleSupplyMap()
                                            .computeIfAbsent(cycleValue.cycle(), k -> new CycleSupply(cycleValue.cycle(), new SupplyCountList()))
                                            .supplyCountList()
                                            .addSupply(cycleValue.supplyValue(), slotsFinal)
                            );
                });
            } else {
                CycleValuePatternList.peakCycleValuePatternMap
                        .get(weeklyProduct.peakKey()).cycleValueList()
                        .forEach(cycleValue ->
                                productSupplyList.put(cycleValue.cycle(), new CycleSupply(cycleValue.cycle(), createSupplyCountList(cycleValue.supplyValue()))));
            }
            productSupplyList.getCycleSupplyMap().values().forEach(cycleSupply -> cycleSupply.supplyCountList().calculateProbabilities());
        });

        var startCycle = 5;
        var restDayStart = 4;
        var restDayEnd = 4;

        var agendaComb = new AgendaComb();

        cycleService.addAgenda(agendaComb, 2, "Boiled Egg (4h), Popoto Salad (4h), Onion Soup (6h), Popoto Salad (4h), Onion Soup (6h)", agendaSet, productList);
        cycleService.addAgenda(agendaComb, 3, "Rope (4h), Shark Oil (8h), Grilled Clam (4h), Shark Oil (8h)", agendaSet, productList);
        //cycleService.addAgenda(agendaComb, 4, "Cawl Cennin (6h), Horn (6h), Cawl Cennin (6h), Horn (6h)", agendaSet, productList);



        var agendaCombResult = agendaComb.calculateProduct(excelService.weeklyProducts(), productList, startCycle);
        System.out.println(agendaCombResult);


        Stream.iterate(restDayStart, restDay -> restDay  + 1).limit(restDayEnd - restDayStart +1).forEach(restDay -> {
            System.out.println("Calculate for Rest Cycle " + restDay);
            var agendaComb2 = cycleService.calcCycles(new AgendaComb(agendaComb), startCycle, restDay, agendaSet, productList);
            var agendaCombResult2 = agendaComb2.calculateProduct(excelService.weeklyProducts(), productList, startCycle);
            System.out.println(agendaCombResult2);
        });

    }
}