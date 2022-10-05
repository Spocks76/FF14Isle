package de.web.spo.ff14.model;

public record CycleValue(Supply supply, DemandShift demandShift) {
    public String getPatternKey(int cycleNumber) {
        return switch(cycleNumber) {
            case 1 -> this.supply().getName() + (this.supply().equals(Supply.INSUFFICIENT) && this.demandShift.equals(DemandShift.INCREASING) ? "-" + this.demandShift().getName() : "");
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

    public String getAltPatternKey(int cycleNumber) {
        if(cycleNumber == 1 && this.supply().equals(Supply.INSUFFICIENT) && !this.demandShift.equals(DemandShift.INCREASING)) {
            return this.supply().getName() + "-" + DemandShift.INCREASING.getName();
        }
        return this.getPatternKey(cycleNumber);
    }
}
