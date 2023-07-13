package me.kacper.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    public final Properties properties;
    private final String config;

    public ConfigReader(){
        properties = new Properties();
        config = "src/main/resources/config.yml";

        try (FileInputStream fileInputStream = new FileInputStream(config)){
            properties.load(fileInputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String get(String key) {
        return properties.getProperty(key);
    }

    public Properties getProperties() {
        return properties;
    }

    public String getConfig() {
        return config;
    }
}
