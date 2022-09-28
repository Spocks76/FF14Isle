package de.web.spo.ff14.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class AgendaCount {

    private Agenda agenda;
    private Integer count;
    public void incCount() {
        count++;
    }
}
