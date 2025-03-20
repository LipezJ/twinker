package com.twinker.persistence.repository;

import com.twinker.domain.entity.Entity;
import com.twinker.persistence.utils.CsvHandler;
import com.twinker.persistence.utils.EntityMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class Repository<T extends Entity> {
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
                T obj = EntityMapper.arrayToEntity(type, row);
                list.add(obj);
            } catch (Exception e) {
                logger.log(Level.SEVERE, e.toString());
            }
        }
        return list;
    }

    public void insert(T object) {
        List<String[]> data = csvHandler.readCSV();
        data.add(EntityMapper.entityToArray(object));
        csvHandler.writeCSV(data);
    }

    public void insertAll(List<T> objects) {
        List<String[]> data = csvHandler.readCSV();
        for (T object : objects) {
            data.add(EntityMapper.entityToArray(object));
        }
        csvHandler.writeCSV(data);
    }

    public void update(T object) {
        List<T> data = getAll();
        List<String[]> newData = new ArrayList<>();
        for (T datum : data) {
            if (!object.getId().equals(datum.getId())) {
                newData.add(EntityMapper.entityToArray(object));
            } else {
                newData.add(EntityMapper.entityToArray(datum));
            }
        }
        csvHandler.writeCSV(newData);
    }

    public void delete(T object) {
        deleteById(object.getId());
    }

    public void deleteAll(T object) {
        csvHandler.writeCSV(new ArrayList<>());
    }

    public Optional<T> searchById(String id) {
        return getAll().stream()
                .filter(obj -> id.equals(obj.getId()))
                .findFirst();
    }

    public void deleteById(String id) {
        List<String[]> data = new ArrayList<>();
        for (T obj : getAll()) {
            if (!id.equals(obj.getId())) {
                data.add(EntityMapper.entityToArray(obj));
            }
        }
        csvHandler.writeCSV(data);
    }
}
