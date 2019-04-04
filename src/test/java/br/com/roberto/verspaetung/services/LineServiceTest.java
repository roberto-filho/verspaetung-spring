package br.com.roberto.verspaetung.services;

import br.com.roberto.verspaetung.data.csv.CSVData;
import br.com.roberto.verspaetung.exeptions.LineScheduleNotFound;
import br.com.roberto.verspaetung.model.DelayInformation;
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
public class LineServiceTest {

    @Autowired
    private LineService lineService;

    @BeforeClass
    public static void setUp() throws Exception {
        CSVData.load();
    }

    @Test
    public void shouldBeDelayedByOneWhenLineM4AndStop2AndTime1006() throws LineScheduleNotFound {
        final DelayInformation delayed = lineService.isDelayed(0, 2, LocalTime.of(10, 6));

        assertThat(delayed, notNullValue());
        assertThat(delayed.getDelayInMinutes(), equalTo(1L));
    }

    @Test
    public void shouldNotBeDelayedWhenLineS75AndStop9AndTime1008() throws LineScheduleNotFound {
        final DelayInformation delayed = lineService.isDelayed(2, 9, LocalTime.of(10, 8));

        assertThat(delayed, nullValue());
    }

    @Test
    public void shouldNotBeDelayedWhenLine200andStop7AndTime1006() throws LineScheduleNotFound {
        final DelayInformation delayed = lineService.isDelayed(1, 7, LocalTime.of(10, 6));

        assertThat(delayed, nullValue());
    }

    @Test(expected = LineScheduleNotFound.class)
    public void shouldThrowExceptionIfLineOrStopNotFound() throws LineScheduleNotFound {
        lineService.isDelayed(500, 7, LocalTime.of(10, 6));
    }
}