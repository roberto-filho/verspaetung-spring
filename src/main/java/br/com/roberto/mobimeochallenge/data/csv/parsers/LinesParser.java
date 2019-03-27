package br.com.roberto.mobimeochallenge.data.csv.parsers;

import br.com.roberto.mobimeochallenge.model.Line;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.StreamSupport;

public class LinesParser extends DataParser {

    public List<Line> parseLinesAndDelays(String linesFilename, String delaysFilename) throws IOException {
        List<Line> lines = new ArrayList<>();

        final Map<String, Integer> lineDelays = parseDelays(delaysFilename);

        try (CSVParser linesParser = createParser(linesFilename)) {
            for (CSVRecord record : linesParser) {
                lines.add(createLineObject(record, lineDelays));
            }
        }

        return lines;
    }

    private Line createLineObject(CSVRecord record, Map<String, Integer> lineDelays) {
        final Line line = new Line();

        line.setId(Integer.parseInt(record.get("line_id")));
        line.setName(record.get("line_name"));
        line.setAverageDelay(lineDelays.get(line.getName()));

        return line;
    }

    private Map<String, Integer> parseDelays(String filename) throws IOException {
        Map<String, Integer> delays = new HashMap<>();
        try (CSVParser delaysParser = createParser(filename)) {
            StreamSupport
                .stream(delaysParser.spliterator(), true)
                .forEach(record -> delays.put(record.get("line_name"), Integer.parseInt(record.get("delay"))));
        }
        return delays;
    }

}
