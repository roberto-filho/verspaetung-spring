package br.com.roberto.verspaetung.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DelayInformation {
    private Line line;
    private long delayInMinutes;
}
