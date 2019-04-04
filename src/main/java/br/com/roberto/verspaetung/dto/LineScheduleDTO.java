package br.com.roberto.verspaetung.dto;

import br.com.roberto.verspaetung.model.LineSchedule;
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
