package br.com.roberto.mobimeochallenge.resources;

import br.com.roberto.mobimeochallenge.dto.GenericMessageDTO;
import br.com.roberto.mobimeochallenge.exeptions.LineScheduleNotFound;
import br.com.roberto.mobimeochallenge.model.DelayInformation;
import br.com.roberto.mobimeochallenge.services.LineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;

@RestController
@RequestMapping("/lines")
public class LinesResource {

    private final LineService lineService;

    @Autowired
    public LinesResource(LineService lineService) {
        this.lineService = lineService;
    }

    @GetMapping("/{id}/get-delay")
    public ResponseEntity find(@PathVariable("id") int lineId, @RequestParam int stopId) {
        DelayInformation delayed;

        try {
            delayed = lineService.isDelayed(lineId, stopId, LocalTime.now());
        } catch (LineScheduleNotFound lineScheduleNotFound) {
            return ResponseEntity.unprocessableEntity()
                    .body(new GenericMessageDTO(lineScheduleNotFound.getMessage()));
        }

        if (delayed == null) {
            // Delay was not found.
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(delayed);
    }

}
