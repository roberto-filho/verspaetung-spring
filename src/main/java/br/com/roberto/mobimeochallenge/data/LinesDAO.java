package br.com.roberto.mobimeochallenge.data;

import br.com.roberto.mobimeochallenge.data.csv.CSVData;
import br.com.roberto.mobimeochallenge.model.Line;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LinesDAO {

    public List<Line> list() {
        return CSVData.getLines();
    }

    public Line findById(int lineId) {
        return list().
                stream()
                .filter(line -> line.getId() == lineId)
                .findFirst()
                .orElse(null);
    }
}
