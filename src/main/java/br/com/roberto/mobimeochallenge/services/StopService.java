package br.com.roberto.mobimeochallenge.services;

import br.com.roberto.mobimeochallenge.data.StopsDAO;
import br.com.roberto.mobimeochallenge.model.LineSchedule;
import br.com.roberto.mobimeochallenge.model.Stop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StopService {

    final private StopsDAO dao;

    @Autowired
    public StopService(StopsDAO dao) {
        this.dao = dao;
    }

    public LineSchedule findNextVehicle(int stopId, LocalTime time) {
        final Stop stop = dao.findById(stopId);
        // Guard clause
        if (stop == null) {
            return null;
        }

        // Get the schedules for this line
        final List<LineSchedule> schedules = stop.getSchedules()
                .stream()
                .sorted(Comparator.comparing(LineSchedule::getTime))
                .collect(Collectors.toList());

        if (schedules.size() == 1) {
            // Nothing to do here, this will always be the next.
            return schedules.get(0);
        }

        for (int i = 0; i <= schedules.size(); i++) {
            final LineSchedule current = schedules.get(i);

            // Check if this is the last record, if it is, then we looped.
            // In this case, just return the first record.
            if (i == schedules.size()-1) {
                return schedules.get(0);
            }

            final LineSchedule next = schedules.get(i + 1);

            if (time.isBefore(current.getTime())) {
                // It's before the first line for this stop
                return current;
            }
            if (isAfterCurrentAndBeforeNext(time, current.getTime(), next.getTime())) {
                // Then the next vehicle is the "next" vehicle, with the least delay
                return findWithLowestDelay(schedules, next.getTime());
            }
        }

        // None found. Should never reach this point.
        return null;
    }

    /**
     * Checks if there are two lines arriving at the same time
     * and returns the one with the lowest delay to be the next.
     * @param schedules the schedules for a given stop;
     * @param time      the time of the next vehicle.
     * @return the line with the lowest delay for the given time.
     */
    private LineSchedule findWithLowestDelay(List<LineSchedule> schedules, LocalTime time) {
        return schedules.stream()
                .filter(lineSchedule -> lineSchedule.getTime().equals(time))
                .sorted(Comparator.comparing(LineSchedule::getLineDelay))
                .findFirst()
                .orElse(null);
    }

    private boolean isAfterCurrentAndBeforeNext(LocalTime time, LocalTime start, LocalTime end) {
        return (time.equals(start) || time.isAfter(start)) && time.isBefore(end);
    }
}
