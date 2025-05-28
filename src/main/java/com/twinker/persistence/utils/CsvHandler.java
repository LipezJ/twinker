package com.twinker.persistence.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utility class for handling CSV file operations in the Twinker application.
 * Provides basic functionality for reading from and writing to CSV files.
 *
 * <p>
 * This class handles:
 * <ul>
 * <li>Reading CSV files line by line</li>
 * <li>Writing data to CSV files</li>
 * <li>Error handling and logging for file operations</li>
 * <li>Comma-separated value parsing and formatting</li>
 * </ul>
 * </p>
 *
 * @author Twinker Development Team
 */
public class CsvHandler {
    private static final Logger logger = Logger.getLogger(CsvHandler.class.getName());

    private final String filePath;

    /**
     * Constructs a new CsvHandler for the specified file.
     *
     * @param filePath the path to the CSV file to handle
     */
    public CsvHandler(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Reads data from the CSV file.
     * Each line is split on commas and returned as a string array.
     *
     * @return a list of string arrays, where each array represents a row
     *         from the CSV file
     */
    public List<String[]> readCSV() {
        List<String[]> data = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                data.add(line.split(","));
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error reading the CSV file from the path: " + filePath);
        }
        return data;
    }

    /**
     * Writes data to the CSV file.
     * Each string array is joined with commas and written as a line.
     *
     * @param datos the list of string arrays to write to the file,
     *              where each array represents a row
     */
    public void writeCSV(List<String[]> datos) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (String[] row : datos) {
                bw.write(String.join(",", row));
                bw.newLine();
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error writing the CSV file to the path: " + filePath);
        }
    }
}
