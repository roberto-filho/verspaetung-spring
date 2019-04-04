package br.com.roberto.verspaetung.services;

import br.com.roberto.verspaetung.data.csv.CSVData;
import br.com.roberto.verspaetung.model.LineSchedule;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalTime;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StopServiceTest {

    @Autowired
    private StopService stopService;

    @BeforeClass
    public static void setUp() throws Exception {
        CSVData.load();
    }

    @Test
    public void shouldReturnS75withStop4AndTime1010() {
        final LineSchedule nextVehicle = stopService.findNextVehicle(4, LocalTime.of(10, 10));
        assertThat(nextVehicle, notNullValue());
        assertThat(nextVehicle.getLine(), hasProperty("name", equalTo("S75")));
    }

    @Test
    public void shouldReturnM4withStop4AndTime1011() {
        final LineSchedule nextVehicle = stopService.findNextVehicle(4, LocalTime.of(10, 11));
        assertThat(nextVehicle, notNullValue());
        assertThat(nextVehicle.getLine(), hasProperty("name", equalTo("M4")));
    }

    @Test
    public void shouldReturn200withStop3AndTime1007() {
        final LineSchedule nextVehicle = stopService.findNextVehicle(3, LocalTime.of(10, 7));
        assertThat(nextVehicle.getLine(), hasProperty("name", equalTo("200")));
    }
}