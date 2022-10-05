package de.web.spo.ff14.model;

import java.util.List;

public record CycleValuePattern(List<CycleValue> cycleValueList, int count) {

    public static String getPatternKeyUntil(int cycleNumber, List<CycleValue> cycleValueList) {
        var stringBuilder = new StringBuilder(cycleValueList.get(0).getPatternKey(1));
        for(int i=1; i<cycleNumber; i++) {
            stringBuilder.append(",");
            stringBuilder.append(cycleValueList.get(i).getPatternKey(i+1));
        }
        return stringBuilder.toString();
    }

    public static String getAltPatternKeyUntil(int cycleNumber, List<CycleValue> cycleValueList) {
        var stringBuilder = new StringBuilder(cycleValueList.get(0).getAltPatternKey(1));
        for(int i=1; i<cycleNumber; i++) {
            stringBuilder.append(",");
            stringBuilder.append(cycleValueList.get(i).getAltPatternKey(i+1));
        }
        return stringBuilder.toString();
    }

}
