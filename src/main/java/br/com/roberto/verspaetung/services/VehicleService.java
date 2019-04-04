package br.com.roberto.verspaetung.services;

import br.com.roberto.verspaetung.data.LinesDAO;
import br.com.roberto.verspaetung.model.Line;
import br.com.roberto.verspaetung.model.LineSchedule;
import br.com.roberto.verspaetung.model.Stop;
import org.apache.commons.lang3.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VehicleService {

    private final LinesDAO linesDAO;

    @Autowired
    public VehicleService(LinesDAO linesDAO) {
        this.linesDAO = linesDAO;
    }

    /**
     * Finds the vehicle crossing specific coordinates in a specific
     * time.
     * @param time the time at which the vehicle is there.
     * @param x    the x coordinate.
     * @param y    the y coordinate.
     * @return the current line(s) that may be crossing the given coordinates at the given time.
     */
    public List<Line> findVehicle(LocalTime time, int x, int y) {
        final List<Line> lines = linesDAO.list();

        final List<Line> matchingLines = new ArrayList<>();

        for (Line line : lines) {
            // Get the stops along this line
            final List<LineSchedule> stops = line.getSchedules().stream()
                    .sorted(Comparator.comparing(LineSchedule::getTime))
                    .collect(Collectors.toList());

            for (int i = 0; i+1 < stops.size(); i++) {
                final LineSchedule departure = stops.get(i);
                final LineSchedule arrival = stops.get(i + 1);

                final Stop currentStop = departure.getStop();
                final Stop nextStop = arrival.getStop();

                if (isBetween(currentStop, nextStop, x, y)
                        && isTimeBetween(departure.getTime(), arrival.getTime(), time)) {
                    matchingLines.add(line);
                }
            }
        }

        return matchingLines;
    }

    private boolean isTimeBetween(LocalTime start, LocalTime end, LocalTime time) {
        // TODO Check if this condition should also consider time.equals(start)
        return time.isAfter(start) && time.isBefore(end);
    }

    /**
     * Checks if a given x,y coordinate is within the route
     * between two stops.
     * @param stop     the current stop.
     * @param nextStop the next stop.
     * @param x        the x coordinate.
     * @param y        the y coordinate.
     * @return <code>true</code> if this coordinate is between the two stops, <code>false</code> otherwise.
     */
    private boolean isBetween(Stop stop, Stop nextStop, int x, int y) {
        Range<Integer> rangeX = Range.between(stop.getX(), nextStop.getX());
        Range<Integer> rangeY = Range.between(stop.getY(), nextStop.getY());

        return rangeX.contains(x) && rangeY.contains(y);
    }

}
