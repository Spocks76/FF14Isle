package de.web.spo.ff14;

import de.web.spo.ff14.model.*;
import de.web.spo.ff14.service.AgendaService;
import de.web.spo.ff14.service.CycleService;
import de.web.spo.ff14.service.PatternService;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Stream;

@SpringBootApplication
public class SpringBootFF14Isle implements CommandLineRunner {

    private final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(this.getClass());

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

        Map<String, CycleStartStats> cycleStartStatsMap = new Hashtable<>();

        var threadCount = 15;
        CountDownLatch latch = new CountDownLatch(threadCount);
        Stream.iterate(1, threadNumber -> threadNumber + 1)
                .limit(threadCount)
                .forEach(threadNumber -> new Thread(new SimThread(agendaService, patternService, cycleService, latch, grooveValueAgendaMapMap, cycleStartStatsMap, threadNumber == 1)).start());

        latch.await();

        cycleStartStatsMap.values().stream()
                .sorted(Comparator.comparing(CycleStartStats::getMaxValue).reversed())
                .limit(50)
                .forEach(cycleStartStats -> {
                    System.out.println("===> " + cycleStartStats.getMaxValue() + ", " +cycleStartStats.getAvgValue() + ", " + cycleStartStats.getAgendaCombKey());
                    cycleStartStats.getCycleCombStatsMap().values().stream().sorted(Comparator.comparing(CycleCombStats::getMaxValue).reversed())
                            .limit(10)
                            .forEach(cycleCombStats -> System.out.println(cycleCombStats.getCountCycleComb() + " : " + cycleCombStats.getAvgValue() + " : " + cycleCombStats.getCycleComb().getLastGroove() + " " +
                                    cycleCombStats.getCycleComb().getAgendaCombMap().values().stream()
                                    .map(agendaComb -> agendaComb.getPercentage() + "%:" + agendaComb.getValue()).toList()
                                    + " --> " + cycleCombStats.getCycleComb().getKey()
                            ));
                });

        cycleStartStatsMap.values().stream()
                .map(CycleStartStats::getStartAgendaCombStats)
                .filter(agendaCombStats -> agendaCombStats.getMinAgendaComb() != null && agendaCombStats.getMaxAgendaComb() != null)
                .sorted(Comparator.comparing(AgendaCombStats::getMaxValue).reversed())
                .limit(100)
                .forEach(agendaCombStats -> System.out.println("Min " + agendaCombStats.getMinAgendaComb().getKey2() + ":" + agendaCombStats.getMinAgendaComb().getValue() + ", Avg " + agendaCombStats.getAvgValue() + ", Max " + agendaCombStats.getMaxAgendaComb().getKey2() + ":" + agendaCombStats.getMaxAgendaComb().getValue()));
    }
}