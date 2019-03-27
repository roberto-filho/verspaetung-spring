package br.com.roberto.mobimeochallenge.resources;

import br.com.roberto.mobimeochallenge.dto.GenericMessageDTO;
import br.com.roberto.mobimeochallenge.dto.LineScheduleDTO;
import br.com.roberto.mobimeochallenge.model.Line;
import br.com.roberto.mobimeochallenge.model.LineSchedule;
import br.com.roberto.mobimeochallenge.model.Stop;
import br.com.roberto.mobimeochallenge.services.StopService;
import br.com.roberto.mobimeochallenge.services.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/stops")
public class StopsResource {

    private final StopService stopService;

    @Autowired
    public StopsResource(StopService stopService) {
        this.stopService = stopService;
    }

    @GetMapping("/{id}/next-vehicle")
    public ResponseEntity find(@PathVariable("id") int stopId) {
        final LineSchedule schedule = stopService.findNextVehicle(stopId, LocalTime.now());

        if (schedule == null) {
            return ResponseEntity.ok()
                    .body(new GenericMessageDTO(String.format("Stop with id [%s] not found.", stopId)));
        }

        return ResponseEntity.ok()
                .body(new LineScheduleDTO(schedule));
    }

}
