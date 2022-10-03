package de.web.spo.ff14;

import de.web.spo.ff14.model.CycleCombStats;
import de.web.spo.ff14.service.AgendaService;
import de.web.spo.ff14.service.CycleService;
import de.web.spo.ff14.service.ExcelService;
import de.web.spo.ff14.service.PatternService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.*;
import java.util.concurrent.CountDownLatch;

@SpringBootApplication
public class SpringBootFF14Isle implements CommandLineRunner {
    public SpringBootFF14Isle(AgendaService agendaService, ExcelService excelService, PatternService patternService, CycleService cycleService) {
        this.agendaService = agendaService;
        this.excelService = excelService;
        this.patternService = patternService;
        this.cycleService = cycleService;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringBootFF14Isle.class, args);
    }

    private final AgendaService agendaService;

    private final ExcelService excelService;

    private final PatternService patternService;

    private final CycleService cycleService;



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


        var grooveValueAgendaMapMap = agendaService.createRandomTopAgendaSet();

        //valueAgendaCountLists.forEach(valueAgendaCountList -> System.out.println(valueAgendaCountList.getValue() + ": " + valueAgendaCountList.getAgendaCountMap().values().stream().map(AgendaCount::getAgenda).map(Agenda::getProductKey).toList()));

        Map<String, CycleCombStats> cycleCombStatsMap = new Hashtable<>();

        CountDownLatch latch = new CountDownLatch(5);

        new Thread(new SimThread(patternService, cycleService, agendaService, grooveValueAgendaMapMap, latch, cycleCombStatsMap, true)).start();
        new Thread(new SimThread(patternService, cycleService, agendaService, grooveValueAgendaMapMap, latch, cycleCombStatsMap, false)).start();
        new Thread(new SimThread(patternService, cycleService, agendaService, grooveValueAgendaMapMap, latch, cycleCombStatsMap, false)).start();
        new Thread(new SimThread(patternService, cycleService, agendaService, grooveValueAgendaMapMap, latch, cycleCombStatsMap, false)).start();
        new Thread(new SimThread(patternService, cycleService, agendaService, grooveValueAgendaMapMap, latch, cycleCombStatsMap, false)).start();

        latch.await();

        cycleCombStatsMap.values().stream().sorted(Comparator.comparing(CycleCombStats::getAvgValue).reversed()).limit(50).forEach(cycleCombStats -> System.out.println(cycleCombStats.getCycleComb().getMaxAgendaCombStatsSum()
                + Arrays.stream(cycleCombStats.getCycleComb().getAgendaCombStatsCycles())
                .filter(Objects::nonNull)
                .map(agendaCombStats -> agendaCombStats.getMaxAgendaComb().getValue()).toList()
                .toString()
                + " --> " + cycleCombStats.getCycleComb().getKey()
        ));
    }
}