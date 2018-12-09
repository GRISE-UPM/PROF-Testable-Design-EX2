package es.upm.grise.profundizacion2018.tema5;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigurationHandler {

    private Properties properties;

    public ConfigurationHandler(){
        this.properties = new Properties();
    }

    public Properties readProperties(FileInputStream file) throws IOException {
        properties.load(file);
        return properties;
    }
}
