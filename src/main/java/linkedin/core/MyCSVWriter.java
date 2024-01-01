package linkedin.core;

import com.opencsv.CSVWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class MyCSVWriter {
    private String fileName;
    private CSVWriter writer;

    public MyCSVWriter(String fileName) throws IOException {
        this.fileName = fileName;
        this.writer = new CSVWriter(new FileWriter(fileName, true)); // Append mode
    }

    public void writeAll(List<String[]> data) throws IOException {
        for (String[] line : data) {
            writer.writeNext(line);
        }
    }

    // Method to close the CSVWriter
    public void close() throws IOException {
        if (writer != null) {
            writer.close();
        }
    }
}
