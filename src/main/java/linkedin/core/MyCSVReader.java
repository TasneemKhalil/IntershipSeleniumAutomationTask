package linkedin.core;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class MyCSVReader {
    private String fileName;

    public MyCSVReader(String fileName) {
        this.fileName = fileName;
    }

    public Object[][] readAll() throws IOException, CsvException {
        try (CSVReader reader = new CSVReader(new FileReader(fileName))) {
            List<String[]> allRows = reader.readAll();
            return allRows.stream().map(row -> (Object[]) row).toArray(Object[][]::new);
        }
    }
}
