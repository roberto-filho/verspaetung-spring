package br.com.roberto.mobimeochallenge.data.csv.parsers;

import br.com.roberto.mobimeochallenge.model.Stop;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;

public class StopsParser extends DataParser {

    public List<Stop> parseStops(String filename) throws IOException {
        List<Stop> stops = new ArrayList<>();

        try (CSVParser linesParser = createParser(filename)) {
            StreamSupport.stream(linesParser.spliterator(), true)
                    .map(this::crateStopFromCSVRecord)
                    .forEach(stops::add);
        }

        return stops;
    }

    private Stop crateStopFromCSVRecord(CSVRecord record) {
        Stop stop = new Stop();
        stop.setId(Integer.parseInt(record.get("stop_id")));
        stop.setX(Integer.parseInt(record.get("x")));
        stop.setY(Integer.parseInt(record.get("y")));
        return stop;
    }

}
