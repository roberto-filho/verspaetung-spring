package br.com.roberto.verspaetung.resources;

import br.com.roberto.verspaetung.dto.GenericMessageDTO;
import br.com.roberto.verspaetung.dto.LineScheduleDTO;
import br.com.roberto.verspaetung.model.LineSchedule;
import br.com.roberto.verspaetung.services.StopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;

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
