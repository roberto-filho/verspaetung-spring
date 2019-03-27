package br.com.roberto.mobimeochallenge;

import br.com.roberto.mobimeochallenge.data.csv.CSVData;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.net.URISyntaxException;

@SpringBootApplication
public class MobimeoChallengeApplication {

    public static void main(String[] args) throws IOException, URISyntaxException {
        // Load the data from our CSVs
        CSVData.load();
//        CSVData.getLines().stream().forEach(System.out::println);
//        CSVData.getStops().stream().forEach(System.out::println);
//        CSVData.getSchedule().stream().forEach(System.out::println);
        SpringApplication.run(MobimeoChallengeApplication.class, args);
    }

}
