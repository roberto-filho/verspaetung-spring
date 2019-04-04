package br.com.roberto.verspaetung.model;

import lombok.Data;

import java.time.LocalTime;

@Data
public class LineSchedule {
    private Stop stop;
    private Line line;
    private LocalTime time;

    public int getLineDelay() {
        return line.getAverageDelay();
    }
}
