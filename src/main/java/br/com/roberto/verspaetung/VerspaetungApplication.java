package br.com.roberto.verspaetung;

import br.com.roberto.verspaetung.data.csv.CSVData;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.net.URISyntaxException;

@SpringBootApplication
public class VerspaetungApplication {

    public static void main(String[] args) throws IOException, URISyntaxException {
        // Load the data from our CSVs
        CSVData.load();
//        CSVData.getLines().stream().forEach(System.out::println);
//        CSVData.getStops().stream().forEach(System.out::println);
//        CSVData.getSchedule().stream().forEach(System.out::println);
        SpringApplication.run(VerspaetungApplication.class, args);
    }

}
