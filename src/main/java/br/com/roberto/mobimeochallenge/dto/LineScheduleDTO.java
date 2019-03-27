package br.com.roberto.mobimeochallenge.dto;

import br.com.roberto.mobimeochallenge.model.LineSchedule;
import lombok.Data;

import java.time.LocalTime;

@Data
public class LineScheduleDTO {
    private StopDTO stop;
    private LineDTO line;
    private LocalTime time;

    public LineScheduleDTO(LineSchedule lineSchedule) {
        this.stop = new StopDTO(lineSchedule.getStop());
        this.line = new LineDTO(lineSchedule.getLine());
        this.time = lineSchedule.getTime();
    }
}
