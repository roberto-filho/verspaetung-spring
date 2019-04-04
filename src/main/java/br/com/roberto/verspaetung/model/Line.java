package br.com.roberto.verspaetung.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@ToString(exclude = {"schedules"})
public class Line {
    private int id;
    private String name;
    private int averageDelay;
    @JsonIgnore private List<LineSchedule> schedules = new ArrayList<>();
}
