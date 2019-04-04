package br.com.roberto.verspaetung.dto;

import br.com.roberto.verspaetung.model.Stop;
import lombok.Data;

@Data
public class StopDTO {
    private int id, x, y;

    public StopDTO(final Stop stop) {
        this.id = stop.getId();
        this.x = stop.getX();
        this.y = stop.getY();
    }
}
