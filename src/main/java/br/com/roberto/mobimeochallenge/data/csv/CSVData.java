package br.com.roberto.mobimeochallenge.data.csv;

import br.com.roberto.mobimeochallenge.data.csv.parsers.LinesParser;
import br.com.roberto.mobimeochallenge.data.csv.parsers.StopsParser;
import br.com.roberto.mobimeochallenge.data.csv.parsers.TimesParser;
import br.com.roberto.mobimeochallenge.model.Line;
import br.com.roberto.mobimeochallenge.model.LineSchedule;
import br.com.roberto.mobimeochallenge.model.Stop;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVData {

    private static List<Line> lines = new ArrayList<>();
    private static List<Stop> stops = new ArrayList<>();
    private static List<LineSchedule> schedule = new ArrayList<>();

    /**
     * Loads all the CSV data into memory.
     * @throws IOException
     */
    public static void load() throws IOException {
        loadLinesAndDelays();
        loadStops();
        loadTimes();
        addScheduleToLines();
        addScheduleToStops();
    }

    private static void addScheduleToStops() {
        stops.stream().forEach(CSVData::findAndAddSchedules);
    }

    private static void findAndAddSchedules(Stop stop) {
        schedule.stream()
                .filter(schedule -> schedule.getStop().equals(stop))
                .forEach(schedule -> stop.getSchedules().add(schedule));
    }

    private static void addScheduleToLines() {
        lines.stream().forEach(CSVData::findAndAddSchedules);
    }

    private static void findAndAddSchedules(Line line) {
        schedule.stream()
                .filter(schedule -> schedule.getLine().equals(line))
                .forEach(schedule -> line.getSchedules().add(schedule));
    }

    private static void loadTimes() throws IOException {
        schedule = new TimesParser(stops, lines).parseStops("csv/times.csv");
    }

    private static void loadStops() throws IOException {
        stops = new StopsParser().parseStops("csv/stops.csv");
    }

    private static void loadLinesAndDelays() throws IOException {
        lines = new LinesParser().parseLinesAndDelays("csv/lines.csv", "csv/delays.csv");
    }

    public static List<Line> getLines() {
        return lines;
    }

    public static List<Stop> getStops() {
        return stops;
    }

    public static List<LineSchedule> getSchedule() {
        return schedule;
    }
}
