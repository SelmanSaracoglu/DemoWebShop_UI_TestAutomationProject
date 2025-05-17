package utilities;

import java.io.FileInputStream;
import java.util.Properties;

public class ConfigReader {

    private static Properties properties;

    static {
        try {
            FileInputStream fis = new FileInputStream("src/test/resources/configuration.properties");
            properties = new Properties();
            properties.load(fis);
            fis.close();
        } catch (Exception e) {
            System.out.println("Config dosyası okunamadı: " + e.getMessage());
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}
