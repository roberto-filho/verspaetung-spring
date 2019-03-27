package br.com.roberto.mobimeochallenge.model;

import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@ToString(exclude = {"schedules"})
public class Stop {
    private int id;
    private int x, y;
    private List<LineSchedule> schedules = new ArrayList<>();
}
