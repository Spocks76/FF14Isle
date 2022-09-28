package de.web.spo.ff14.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public enum Popularity {

    VERY_HIGH("Very High", 1.4),
    HIGH("High",1.2),
    AVERAGE("Average", 1.0),
    LOW("Low", 0.8);

    public static final Map<String, Popularity> popularityMap = Arrays.stream(values()).collect(Collectors.toMap(Popularity::getName, popularity -> popularity));

    private final String name;
    private final double modifier;
}
