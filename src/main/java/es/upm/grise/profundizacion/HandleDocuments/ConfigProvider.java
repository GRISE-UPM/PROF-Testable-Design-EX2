package es.upm.grise.profundizacion.HandleDocuments;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static es.upm.grise.profundizacion.HandleDocuments.Error.*;

public class ConfigProvider {
    private final Properties propertiesInFile = new Properties();

    public ConfigProvider(final String path) throws NonRecoverableError {
        if (path == null) {

            System.out.println(UNDEFINED_ENVIRON.getMessage());
            throw new NonRecoverableError();

        } else {
            InputStream inputFile = null;

            // Load the property file
            try {
                inputFile = new FileInputStream(path + "config.properties");
                propertiesInFile.load(inputFile);

            } catch (FileNotFoundException e) {

                System.out.println(NON_EXISTING_FILE.getMessage());
                throw new NonRecoverableError();

            } catch (IOException e) {

                System.out.println(CANNOT_READ_FILE.getMessage());
                throw new NonRecoverableError();

            }

            // Get the DB username and password
            String url = propertiesInFile.getProperty("url");
            String username = propertiesInFile.getProperty("username");
            String password = propertiesInFile.getProperty("password");
        }
    }

    public String getConfigValue(String configKey) {
        return propertiesInFile.getProperty(configKey);
    }
}
