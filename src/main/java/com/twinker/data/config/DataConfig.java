package com.twinker.data.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Configuration management class for the Twinker application.
 * This class handles loading and accessing configuration properties from
 * the application's properties file.
 *
 * <p>
 * The class provides:
 * <ul>
 * <li>Static configuration loading at startup</li>
 * <li>Property access methods</li>
 * <li>Error handling for missing configuration</li>
 * <li>Logging of configuration issues</li>
 * </ul>
 * </p>
 *
 * <p>
 * Configuration is loaded from {@code config.properties} in the resources
 * directory. The file must exist and be accessible at application startup.
 * </p>
 *
 * @author Twinker Development Team
 */
public class DataConfig {
    private static final Logger logger = Logger.getLogger(DataConfig.class.getName());
    private static final Properties properties = new Properties();

    /**
     * Static initializer block that loads the configuration properties.
     * Attempts to load {@code config.properties} from the classpath.
     * If the file is not found or cannot be loaded, throws a RuntimeException.
     *
     * @throws RuntimeException if the configuration file cannot be loaded
     */
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

    /**
     * Retrieves a configuration property value by its key.
     *
     * @param key the key of the configuration property to retrieve
     * @return the value associated with the key, or null if the key doesn't exist
     */
    public static String get(String key) {
        return properties.getProperty(key);
    }
}
