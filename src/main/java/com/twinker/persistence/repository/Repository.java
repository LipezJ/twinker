package com.twinker.persistence.repository;

import com.twinker.persistence.model.Model;
import com.twinker.persistence.utils.CsvHandler;
import com.twinker.persistence.utils.ModelMapper;

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
        List<T> list = new ArrayList<>();
        List<String[]> data = csvHandler.readCSV();

        for (String[] row : data) {
            try {
                T obj = ModelMapper.arrayToModel(type, row);
                list.add(obj);
            } catch (Exception e) {
                logger.log(Level.SEVERE, e.toString());
            }
        }
        return list;
    }

    public Optional<T> searchById(String id) {
        return getAll().stream()
                .filter(obj -> id.equals(obj.getId()))
                .findFirst();
    }

    public void save(T object) {
        List<String[]> data = csvHandler.readCSV();
        data.add(ModelMapper.modelToArray(object));
        csvHandler.writeCSV(data);
    }

    public void deleteById(String id) {
        List<String[]> data = new ArrayList<>();
        for (T obj : getAll()) {
            if (!id.equals(obj.getId())) {
                data.add(ModelMapper.modelToArray(obj));
            }
        }
        csvHandler.writeCSV(data);
    }
}
