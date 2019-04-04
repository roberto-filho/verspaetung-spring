package br.com.roberto.verspaetung.dto;

import br.com.roberto.verspaetung.model.Line;
import lombok.Data;

@Data
public class LineDTO {
    private int id;
    private String name;

    public LineDTO(final Line line) {
        this.id = line.getId();
        this.name = line.getName();
    }
}
