package de.web.spo.ff14;

import de.web.spo.ff14.model.CycleCombStats;
import de.web.spo.ff14.service.AgendaService;
import de.web.spo.ff14.service.CycleService;
import de.web.spo.ff14.service.PatternService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
public class SpringBootFF14Isle implements CommandLineRunner {
    public SpringBootFF14Isle(AgendaService agendaService, PatternService patternService, CycleService cycleService) {
        this.agendaService = agendaService;
        this.patternService = patternService;
        this.cycleService = cycleService;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringBootFF14Isle.class, args);
    }

    private final AgendaService agendaService;

    private final PatternService patternService;

    private final CycleService cycleService;



    @Override
    public void run(String... args) throws Exception {

        var grooveValueAgendaMapMap = agendaService.createRandomTopAgendaSet();

        //valueAgendaCountLists.forEach(valueAgendaCountList -> System.out.println(valueAgendaCountList.getValue() + ": " + valueAgendaCountList.getAgendaCountMap().values().stream().map(AgendaCount::getAgenda).map(Agenda::getProductKey).toList()));

        Map<String, CycleCombStats> cycleCombStatsMap = new Hashtable<>();

        CountDownLatch latch = new CountDownLatch(10);
        Stream.iterate(1, threadNumber -> threadNumber + 1)
                .limit(10)
                .forEach(threadNumber ->  new Thread(new SimThread(patternService, cycleService, agendaService, grooveValueAgendaMapMap, latch, cycleCombStatsMap, threadNumber == 1)).start());

        latch.await();

        cycleCombStatsMap.values().stream().sorted(Comparator.comparing(CycleCombStats::getMaxValue).reversed())
                .limit(50)
                .forEach(cycleCombStats -> System.out.println(cycleCombStats.getCountCycleComb() + " : " + cycleCombStats.getCycleComb().getMaxAgendaCombStatsSum()
                        + Arrays.stream(cycleCombStats.getCycleComb().getAgendaCombStatsCycles())
                        .filter(Objects::nonNull)
                        .map(agendaCombStats -> agendaCombStats.getMinAgendaComb().getPercentage() + "%:" + agendaCombStats.getMinAgendaComb().getValue() + "|" + agendaCombStats.getMaxAgendaComb().getPercentage() + "%:" + agendaCombStats.getMaxAgendaComb().getValue()).toList()
                        + " --> " + cycleCombStats.getCycleComb().getKey()
                        + " {" + cycleCombStats.getCycleComb().getProductCountMap().entrySet().stream().map(productIntegerEntry -> productIntegerEntry.getKey().getName() + "=" + productIntegerEntry.getValue()).collect(Collectors.joining(", ")) + "} "
        ));
    }
}