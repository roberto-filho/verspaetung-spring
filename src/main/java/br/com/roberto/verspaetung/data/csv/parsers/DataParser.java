package br.com.roberto.verspaetung.data.csv.parsers;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public abstract class DataParser {
    /**
     * Creates a CSV parsers for a given filename.
     * @param filename the path to the CSV file
     * @return the parsers
     * @throws IOException
     */
    protected CSVParser createParser(String filename) throws IOException {
        // Get the URL of the resource, this is portable between development
        final URL fileURL = new ClassPathResource(filename).getURL();
        return new CSVParser(new InputStreamReader(fileURL.openStream()), CSVFormat.DEFAULT
                .withFirstRecordAsHeader()
                .withIgnoreHeaderCase()
                .withTrim());
    }
}
