package br.com.roberto.mobimeochallenge.services;

import br.com.roberto.mobimeochallenge.data.csv.CSVData;
import br.com.roberto.mobimeochallenge.model.Line;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalTime;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VehicleServiceTest {

    @Autowired
    private VehicleService vehicleService;

    @BeforeClass
    public static void setUp() throws Exception {
        CSVData.load();
    }

    @Test
    public void shouldFindVehicleM4ForX2andY9AndTime100800() {
        final LocalTime time = LocalTime.of(10, 8);
        final List<Line> vehicles = vehicleService.findVehicle(time, 2, 9);

        assertThat(vehicles, hasSize(1));
        final Line line = vehicles.stream().findFirst().orElse(null);
        assertThat(line, hasProperty("name", equalTo("M4")));
    }

    @Test
    public void shouldFindVehicleForX2andY12AndTime1010() {
        final LocalTime time = LocalTime.of(10, 10);
        final List<Line> vehicles = vehicleService.findVehicle(time, 2, 12);

        assertThat(vehicles, hasSize(1));
        final Line line = vehicles.stream().findFirst().orElse(null);
        assertThat(line, hasProperty("name", equalTo("S75")));
    }
}