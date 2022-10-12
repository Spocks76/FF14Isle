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

    private final Random rand = new Random();

    private final WeeklyProducts weeklyProducts;

    private final PatternService patternService;

    public AgendaService(WeeklyProducts weeklyProducts, PatternService patternService) {
        this.weeklyProducts = weeklyProducts;
        this.patternService = patternService;
    }

    private void addProductToAgenda(Map<Product, CycleValueStatsList> cycleValuePatternMap, Map<Product, Integer> productCountMap, int cycleIndex, Agenda agenda, int hour) {
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
        if(availableProducts.size() > 0 && cycleIndex >= 0) {
            var product = availableProducts.get(rand.nextInt(availableProducts.size()));
            var cycleValueStats = cycleValuePatternMap != null ? cycleValuePatternMap.get(product).cycleValueStatsList().get(cycleIndex) : new CycleValueStats(new CycleValue(Supply.SUFFICIENT, DemandShift.NONE), new CycleValuePatternStats(""));
            var produced = productCountMap.getOrDefault(product, 0);
            var supply =  cycleValueStats.cycleValue().supply();
            var cycleValuePatternStats = this.patternService.getCycleValuePatternStats(cycleIndex+1, product);
            agenda.addProduct(product, weeklyProducts.getProducts().get(product), supply, produced, cycleValuePatternStats.getPercentage(supply));
        }
    }

    private void addProductFromTemplateToAgenda(Map<Product, CycleValueStatsList> cycleValuePatternMap, Map<Product, Integer> productCountMap, int cycleIndex, Agenda templateAgenda, Agenda agenda, int hour) {
        if(agenda.getHour() > hour) {
            return;
        }

        var product = templateAgenda.getProductMap().get(hour);
        if(product != null) {
            var cycleValueStats = cycleValuePatternMap != null ? cycleValuePatternMap.get(product).cycleValueStatsList().get(cycleIndex) : new CycleValueStats(new CycleValue(Supply.SUFFICIENT, DemandShift.NONE), new CycleValuePatternStats(""));
            var produced = productCountMap.getOrDefault(product, 0);
            var supply =  cycleValueStats.cycleValue().supply();
            var cycleValuePatternStats = this.patternService.getCycleValuePatternStats(cycleIndex+1, product);
            agenda.addProduct(product, weeklyProducts.getProducts().get(product), cycleValueStats.cycleValue().supply(), produced, cycleValuePatternStats.getPercentage(supply));
        }
    }
    private Agenda getRandomAgendaFromList(List<ValueAgendaCountMap> valueAgendaCountMaps) {
        var agendaCountList = new ArrayList<>(valueAgendaCountMaps.get(rand.nextInt(valueAgendaCountMaps.size())).getAgendaCountMap().values());
        var agendaCount = agendaCountList.get(rand.nextInt(agendaCountList.size()));
        return agendaCount.getAgenda();
    }

    public AgendaComb createRandomAgendaComb(List<ValueAgendaCountMap> valueAgendaCountMap, Map<Product, CycleValueStatsList> cycleValuePatternMap, Map<Product, Integer> productCountMap, int cycle, int startGroove) {

        Agenda tmplAgenda1 = getRandomAgendaFromList(valueAgendaCountMap);
        Agenda tmplAgenda2 = tmplAgenda1;
        Agenda tmplAgenda3 = tmplAgenda1;

        ProductLog productLog = new ProductLog();
        Groover groover = new Groover(startGroove);
        var agenda1 = new Agenda(groover, productLog);
        var agenda2 = new Agenda(groover, productLog);
        var agenda3 = new Agenda(groover, productLog);
        Stream.iterate(0, hour -> hour + 2)
                .limit(13)
                .forEach(hour ->  {
                    productLog.getProducts().getOrDefault(hour, new HashMap<>()).forEach((product, count) -> productCountMap.merge(product, count, Integer::sum));
                    addProductFromTemplateToAgenda(cycleValuePatternMap, productCountMap, cycle - 1, tmplAgenda1, agenda1, hour);
                    addProductFromTemplateToAgenda(cycleValuePatternMap, productCountMap, cycle - 1, tmplAgenda2, agenda2, hour);
                    addProductFromTemplateToAgenda(cycleValuePatternMap, productCountMap, cycle - 1, tmplAgenda3, agenda3, hour);
        });
        return new AgendaComb(groover, agenda1, agenda2, agenda3, productCountMap);
    }

    public Agenda recreateTopAgendaFromTemplate(Map<Product, CycleValueStatsList> cycleValuePatternMap, Map<Product, Integer> productCountMap, int cycleIndex, int startGroove, Agenda agendaTemplate) {
        var agenda = new Agenda(new Groover(startGroove), new ProductLog());
        agendaTemplate.getProductList().forEach(agendaProduct -> {
            var product = agendaProduct.getProduct();
            var cycleValueStats = cycleValuePatternMap != null ? cycleValuePatternMap.get(product).cycleValueStatsList().get(cycleIndex) : new CycleValueStats(new CycleValue(Supply.SUFFICIENT, DemandShift.NONE), new CycleValuePatternStats(""));
            var produced = productCountMap.getOrDefault(product, 0);
            var supply = cycleValueStats.cycleValue().supply();
            agenda.addProduct(product, weeklyProducts.getProducts().get(product), supply, produced, cycleValueStats.cycleValuePatternStats().getPercentage(supply));
        });
        return agenda;
    }

    public Agenda createRandomAgenda(Map<Product, CycleValueStatsList> cycleValuePatternMap, Map<Product, Integer> productCountMap, int cycleIndex) {
        var agenda = new Agenda(new Groover(0), new ProductLog());
        Stream.iterate(0, hour -> hour + 2)
                .limit(10)
                .forEach(hour -> addProductToAgenda(cycleValuePatternMap, productCountMap, cycleIndex, agenda, hour));
        return agenda;
    }

    private void addRandomTopAgendaSet(Map<Integer, GrooveValueAgendaMap> grooveValueAgendaMapMap, Map<Product, Integer> productCountMap) {
        Stream.generate(() -> createRandomAgenda(null, productCountMap, 0))
                .limit(1000000)
                .forEach(agenda -> grooveValueAgendaMapMap.computeIfAbsent(agenda.getGroover().getLastGroove(), groove -> new GrooveValueAgendaMap(groove, 200)).addAgenda(agenda));
    }

    public Map<Integer, GrooveValueAgendaMap> createRandomTopAgendaSet() {
        Map<Integer, GrooveValueAgendaMap> grooveValueAgendaMapMap = new Hashtable<>();
        addRandomTopAgendaSet(grooveValueAgendaMapMap, new HashMap<>());
        return grooveValueAgendaMapMap;
    }

    public Map<Integer, List<ValueAgendaCountMap>> resortTopAgendaSetForCyclePattern(Map<Integer, GrooveValueAgendaMap> grooveValueAgendaMapMapOld, Map<Product, CycleValueStatsList> cycleValuePatternMap, Map<Product, Integer> productCountMap, int cycleIndexStart) {
        Map<Integer, List<ValueAgendaCountMap>> valueAgendCountMapListMap = new Hashtable<>();

        Stream.iterate(cycleIndexStart, cycleIndex -> cycleIndex + 1).limit(7 - cycleIndexStart)
                .forEach(cycleIndex -> {
                    Map<Integer, GrooveValueAgendaMap> grooveValueAgendaMapMap = new Hashtable<>();
                    grooveValueAgendaMapMapOld.values().forEach(grooveValueAgendaMap -> grooveValueAgendaMap.getValueAgendaCountMap().values().forEach(valueAgendaCountMap -> valueAgendaCountMap.getAgendaCountMap().values()
                            .forEach(agendaCount -> {
                                var agenda = recreateTopAgendaFromTemplate(cycleValuePatternMap, productCountMap, cycleIndex, 0, agendaCount.getAgenda());
                                grooveValueAgendaMapMap.computeIfAbsent(agenda.getGroover().getLastGroove(), groove -> new GrooveValueAgendaMap(groove, 2)).addAgenda(agenda);
                            })));
                    List<ValueAgendaCountMap> valueAgendaCountMaps = new ArrayList<>();
                    grooveValueAgendaMapMap.values().forEach(grooveValueAgendaMap -> valueAgendaCountMaps.addAll(grooveValueAgendaMap.getValueAgendaCountMap().values()));
                    valueAgendCountMapListMap.put(cycleIndex, valueAgendaCountMaps);
                });

        return valueAgendCountMapListMap;
    }
}

