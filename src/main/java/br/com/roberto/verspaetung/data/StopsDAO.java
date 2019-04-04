package br.com.roberto.verspaetung.data;

import br.com.roberto.verspaetung.data.csv.CSVData;
import br.com.roberto.verspaetung.model.Stop;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StopsDAO {

    public List<Stop> list() {
        return CSVData.getStops();
    }

    public Stop findById(int id) {
        return list().stream()
                .filter(stop -> stop.getId() == id)
                .findFirst()
                .orElse(null);
    }
}
