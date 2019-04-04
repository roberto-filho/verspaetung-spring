package br.com.roberto.verspaetung.resources;

import br.com.roberto.verspaetung.dto.GenericMessageDTO;
import br.com.roberto.verspaetung.model.Line;
import br.com.roberto.verspaetung.services.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/vehicles")
public class VehiclesResource {

    private final VehicleService vehicleService;

    @Autowired
    public VehiclesResource(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @GetMapping("/find")
    public ResponseEntity find(@RequestParam LocalTime time, @RequestParam int x, @RequestParam int y) {
        final List<Line> linesThatCrossCoords = vehicleService.findVehicle(time, x, y);

        // If we find more than one lines that cross (x,y), return the one with the least delay.
        final Line line = linesThatCrossCoords.stream()
                .sorted(Comparator.comparing(Line::getAverageDelay))
                .findFirst()
                .orElse(null);

        if (line == null) {
            return createNoLineMessage(x,y,time);
        }

        return ResponseEntity.ok(line);
    }

    private ResponseEntity createNoLineMessage(int x, int y, LocalTime time) {
        final String message = String.format("There is no Line for coordinates (%s, %s) at [%s]", x, y, time);
        return ResponseEntity.ok().body(new GenericMessageDTO(message));
    }

}
