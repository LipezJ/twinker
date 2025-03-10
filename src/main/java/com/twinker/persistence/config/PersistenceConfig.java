package com.twinker.persistence.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PersistenceConfig {
    private static final Logger logger = Logger.getLogger(PersistenceConfig.class.getName());
    private static final Properties properties = new Properties();

    static {
        try (InputStream input = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new IOException("File `config.properties` not found in `resources/`");
            }
            properties.load(input);
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.toString());
            throw new RuntimeException("Error loading `config.properties`", e);
        }
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }
}
