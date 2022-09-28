package de.web.spo.ff14;

import de.web.spo.ff14.model.AgendaCombStats;
import de.web.spo.ff14.model.ValueAgendaCountList;
import de.web.spo.ff14.service.AgendaService;
import de.web.spo.ff14.service.ExcelService;
import de.web.spo.ff14.service.PatternService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.*;
import java.util.concurrent.CountDownLatch;

@SpringBootApplication
public class SpringBootFF14Isle implements CommandLineRunner {
    public SpringBootFF14Isle(AgendaService agendaService, ExcelService excelService, PatternService patternService) {
        this.agendaService = agendaService;
        this.excelService = excelService;
        this.patternService = patternService;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringBootFF14Isle.class, args);
    }

    private final AgendaService agendaService;

    private final ExcelService excelService;

    private final PatternService patternService;



    @Override
    public void run(String... args) throws Exception {
        /*var cycles = new Cycles();

        Arrays.stream(Product.values()).forEach(product -> {
            var cycle2List = prognosisCycleValueService.calculateCycle2Value(cycles.getCycleValuesList().get(1).getCycleValueMap().get(product));
            System.out.println(cycle2List);
        });*/

        /**/

        /*
      */

        List<ValueAgendaCountList> valueAgendaCountLists = new ArrayList<>(agendaService.createRandomTopAgendaSet().values());

        //valueAgendaCountLists.forEach(valueAgendaCountList -> System.out.println(valueAgendaCountList.getValue() + ": " + valueAgendaCountList.getAgendaCountMap().values().stream().map(AgendaCount::getAgenda).map(Agenda::getProductKey).toList()));

        Map<String, AgendaCombStats> agendaCombStatsMap = new Hashtable<>();

        CountDownLatch latch = new CountDownLatch(5);

        new Thread(new SimThread(patternService, agendaService, agendaCombStatsMap, valueAgendaCountLists, latch, true)).start();
        new Thread(new SimThread(patternService, agendaService, agendaCombStatsMap, valueAgendaCountLists, latch, false)).start();
        new Thread(new SimThread(patternService, agendaService, agendaCombStatsMap, valueAgendaCountLists, latch, false)).start();
        new Thread(new SimThread(patternService, agendaService, agendaCombStatsMap, valueAgendaCountLists, latch, false)).start();
        new Thread(new SimThread(patternService, agendaService, agendaCombStatsMap, valueAgendaCountLists, latch, false)).start();

        latch.await();

        List<AgendaCombStats> combStatsList = new ArrayList<>(agendaCombStatsMap.values());
        combStatsList.sort(Comparator.comparing(AgendaCombStats::getAvgValue).reversed());
        combStatsList.subList(0,50).forEach(agendaCombStats -> System.out.println(
                "Avg: " + agendaCombStats.getAvgValue() + ", Min: " + "(" + agendaCombStats.getMinPercent() + "%) " + agendaCombStats.getMinAgendaComb().getValue() + ", Max: " + "(" + agendaCombStats.getMaxPercent() + "%) " + agendaCombStats.getMaxAgendaComb().getValue() + ", MinCombo: " +  agendaCombStats.getMinAgendaComb().getKey()
                )
        );



    }
}