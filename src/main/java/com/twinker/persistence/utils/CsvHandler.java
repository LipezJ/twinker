package com.twinker.persistence.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CsvHandler {
    private static final Logger logger = Logger.getLogger(CsvHandler.class.getName());

    private final String filePath;

    public CsvHandler(String filePath) {
        this.filePath = filePath;
    }

    public List<String[]> readCSV() {
        List<String[]> datos = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                datos.add(linea.split(","));
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error reading the CSV file from the path: " + filePath);
        }
        return datos;
    }

    public void writeCSV(List<String[]> datos) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (String[] fila : datos) {
                bw.write(String.join(",", fila));
                bw.newLine();
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error writing the CSV file to the path: " + filePath);
        }
    }
}
