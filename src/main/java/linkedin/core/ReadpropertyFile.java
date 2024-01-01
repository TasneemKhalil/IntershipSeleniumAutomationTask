package linkedin.core;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ReadpropertyFile {
    private Properties properties;

    public ReadpropertyFile(String filePath) throws IOException {
        properties = new Properties();
        try (FileReader reader = new FileReader(filePath)) {
            properties.load(reader);
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}
