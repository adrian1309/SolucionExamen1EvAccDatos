package config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConfigProperties {
    private static ConfigProperties configurationProperties;
    private Properties properties;

    public String getProperty(String key) {
        return (String)properties.get(key);
    }

    private ConfigProperties() {

    }

    public static ConfigProperties getInstance() {
        if (configurationProperties == null) {
            try {
                configurationProperties = new ConfigProperties();
                configurationProperties.properties = new Properties();
                configurationProperties.properties.loadFromXML(new FileInputStream("propertiesFiles/settings.xml"));
            } catch (IOException ex) {
                Logger.getLogger(ConfigProperties.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return configurationProperties;
    }
}
