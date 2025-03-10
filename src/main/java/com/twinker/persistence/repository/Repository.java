package com.twinker.persistence.repository;

import com.twinker.persistence.model.Model;
import com.twinker.persistence.utils.CsvHandler;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class Repository<T extends Model> {
    private static final Logger logger = Logger.getLogger(Repository.class.getName());

    private final CsvHandler csvHandler;
    private final Class<T> type;

    public Repository(String filePath, Class<T> type) {
        this.csvHandler = new CsvHandler(filePath);
        this.type = type;
    }

    public List<T> getAll() {
        List<T> lista = new ArrayList<>();
        List<String[]> datos = csvHandler.readCSV();

        for (String[] fila : datos) {
            try {
                Constructor<T> constructor = type.getDeclaredConstructor(String[].class);
                T obj = constructor.newInstance((Object) fila);
                lista.add(obj);
            } catch (Exception e) {
                logger.log(Level.SEVERE, e.toString());
            }
        }
        return lista;
    }

    public Optional<T> searchById(String id) {
        return getAll().stream()
                .filter(obj -> id.equals(obj.getId()))
                .findFirst();
    }

    public void save(T objeto) {
        List<String[]> datos = csvHandler.readCSV();
        datos.add(objeto.toString().split(","));
        csvHandler.writeCSV(datos);
    }

    public void deleteById(String id) {
        List<String[]> datos = new ArrayList<>();
        for (T obj : getAll()) {
            if (!id.equals(obj.getId())) {
                datos.add(obj.toString().split(","));
            }
        }
        csvHandler.writeCSV(datos);
    }
}
