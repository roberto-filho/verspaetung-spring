package br.com.roberto.mobimeochallenge.data.csv.parsers;

import br.com.roberto.mobimeochallenge.model.Line;
import br.com.roberto.mobimeochallenge.model.LineSchedule;
import br.com.roberto.mobimeochallenge.model.Stop;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;

public class TimesParser extends DataParser {

    private final List<Stop> stops;
    private final List<Line> lines;

    public TimesParser(List<Stop> stops, List<Line> lines) {
        this.stops = stops;
        this.lines = lines;
    }

    public List<LineSchedule> parseStops(String filename) throws IOException {
        List<LineSchedule> times = new ArrayList<>();

        try (CSVParser linesParser = createParser(filename)) {
            StreamSupport.stream(linesParser.spliterator(), true)
                    .map(this::crateLineScheduleFromCSVRecord)
                    .forEach(times::add);
        }

        return times;
    }

    private LineSchedule crateLineScheduleFromCSVRecord(CSVRecord record) {
        final LineSchedule lineSchedule = new LineSchedule();

        int lineId = Integer.parseInt(record.get("line_id"));
        int stopId = Integer.parseInt(record.get("stop_id"));

        lineSchedule.setLine(findLine(lineId));
        lineSchedule.setStop(findStop(stopId));
        lineSchedule.setTime(LocalTime.parse(record.get("time")));

        return lineSchedule;
    }

    private Stop findStop(int stopId) {
        return stops.stream()
                .filter(stop -> stop.getId() == stopId)
                .findAny()
                .get();
    }

    private Line findLine(int lineId) {
        return lines.stream()
                .filter(line -> line.getId() == lineId)
                .findAny()
                .get();
    }

}
