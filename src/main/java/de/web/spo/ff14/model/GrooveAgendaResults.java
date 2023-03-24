package de.web.spo.ff14.model;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public record GrooveAgendaResults<R>(int groove, TreeMap<Integer, Map<String, R>> minResults, TreeMap<Integer, Map<String, R>> maxResults) {

    public void trim(int trimSize) {
        if(minResults.size() > trimSize) {
            var toRemoveMinKeys = new ArrayList<>(minResults.keySet()).subList(trimSize, minResults.size());
            toRemoveMinKeys.forEach(minResults::remove);
        }

        if(maxResults.size() > trimSize) {
            var toRemoveMaxKeys = new ArrayList<>(maxResults.keySet()).subList(trimSize, maxResults.size());
            toRemoveMaxKeys.forEach(maxResults::remove);
        }
    }

}
