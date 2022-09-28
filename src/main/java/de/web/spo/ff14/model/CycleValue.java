package de.web.spo.ff14.model;

public record CycleValue(Supply supply, DemandShift demandShift) {
    public String getPatternKey(int cycleNumber, int cycleNumberMax) {
        return switch(cycleNumber) {
            case 1 -> this.supply().getName();
            case 2 -> switch (this.supply()) {
                case NONEXISTENT, SUFFICIENT -> this.supply().getName();
                case INSUFFICIENT -> this.demandShift().equals(DemandShift.SKYROCKETING) || this.demandShift().equals(DemandShift.INCREASING)
                        ? this.supply().getName() + "-" + this.demandShift().getName()
                        : this.supply().getName();
                default -> "";
            };
            case 3, 4 -> switch (this.supply()) {
                case NONEXISTENT -> this.supply().getName();
                case INSUFFICIENT -> this.demandShift().equals(DemandShift.SKYROCKETING) || this.demandShift().equals(DemandShift.INCREASING)
                        ? this.supply().getName() + "-" + this.demandShift().getName()
                        : "";
                case SUFFICIENT -> this.supply().getName() + "-" + this.demandShift().getName();
                default -> "";
            };
            default -> "";
        };
    }
}
