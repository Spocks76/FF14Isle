package de.web.spo.ff14.model;


import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class Cycle {
    private final int cycle;

    private final Map<Product, ProductCycleValue> productCycleValueMap = new HashMap<>();

    @Setter
    private List<Agenda> agendaList;

    @Setter
    private List<CycleComb> cycleCombList;

    public Cycle(int cycle) {
        this.cycle = cycle;
    }
}
