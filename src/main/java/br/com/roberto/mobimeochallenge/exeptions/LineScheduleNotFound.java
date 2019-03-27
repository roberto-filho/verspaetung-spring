package br.com.roberto.mobimeochallenge.exeptions;

public class LineScheduleNotFound extends Exception {
    private int lineId;
    private int stopId;

    public LineScheduleNotFound(int lineId, int stopId) {
        super(String.format("Schedule not found for line with id [%s] and stop with id [%s].", lineId, stopId));
        this.lineId = lineId;
        this.stopId = stopId;
    }

    public int getLineId() {
        return lineId;
    }

    public int getStopId() {
        return stopId;
    }
}
