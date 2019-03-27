package br.com.roberto.mobimeochallenge.services;

import br.com.roberto.mobimeochallenge.data.LinesDAO;
import br.com.roberto.mobimeochallenge.exeptions.LineScheduleNotFound;
import br.com.roberto.mobimeochallenge.model.DelayInformation;
import br.com.roberto.mobimeochallenge.model.Line;
import br.com.roberto.mobimeochallenge.model.LineSchedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Optional;
import java.util.function.Supplier;

@Service
public class LineService {

    private LinesDAO dao;

    @Autowired
    public LineService(LinesDAO dao) {
        this.dao = dao;
    }

    /**
     * Checks if a particular line is delayed, give a stop and time.
     * @param lineId
     * @return
     */
    public DelayInformation isDelayed(int lineId, int stopId, LocalTime time) throws LineScheduleNotFound {
        // Create the function to throw exceptions
        final Supplier<LineScheduleNotFound> exceptionSupplier = () -> new LineScheduleNotFound(lineId, stopId);

        final Line line = findLineOrThrowError(lineId, exceptionSupplier);
        final LineSchedule lineSchedule = findScheduleOrThrowError(line, stopId, exceptionSupplier);

        return getDelay(lineSchedule, time);
    }

    private LineSchedule findScheduleOrThrowError(Line line, int stopId, Supplier<LineScheduleNotFound> exceptionSupplier)
            throws LineScheduleNotFound {
        return line.getSchedules()
                .stream()
                .filter(schedule -> filterByStopId(schedule, stopId))
                .findFirst()
                .orElseThrow(exceptionSupplier);
    }

    private Line findLineOrThrowError(int lineId, Supplier<LineScheduleNotFound> createException) throws LineScheduleNotFound {
        final Line line = dao.findById(lineId);

        return Optional.ofNullable(line)
                .orElseThrow(createException);
    }

    /**
     * Checks if a given line is late for a given stop.
     * @param lineSchedule the schedule for this line/stop.
     * @param time         the time to be checked at
     * @return true if <code>given time > scheduledTime >= scheduledTime+delay</code>.
     */
    private DelayInformation getDelay(LineSchedule lineSchedule, LocalTime time) {
        final LocalTime scheduledTime = lineSchedule.getTime();

        if (time.isAfter(scheduledTime)) {
            // Check how late it is
            return new DelayInformation(
                    lineSchedule.getLine(),
                    scheduledTime.until(time, ChronoUnit.MINUTES));
        } else {
            return null;
        }
    }

    private boolean filterByStopId(LineSchedule lineSchedule, int stopId) {
        return lineSchedule.getStop().getId() == stopId;
    }
}
