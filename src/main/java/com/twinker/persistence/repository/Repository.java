package com.twinker.persistence.repository;

import com.twinker.domain.entity.Entity;
import com.twinker.persistence.utils.CsvHandler;
import com.twinker.persistence.utils.EntityMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Abstract base class for all repositories in the Twinker application.
 * Provides generic CRUD operations for entities using CSV file storage.
 *
 * <p>
 * This class implements the Repository pattern and provides:
 * <ul>
 * <li>Basic CRUD operations for entities</li>
 * <li>CSV file-based persistence</li>
 * <li>Generic type support for different entities</li>
 * <li>Error logging and exception handling</li>
 * </ul>
 * </p>
 *
 * @param <T> the type of entity managed by this repository
 *
 * @author Twinker Development Team
 * @see com.twinker.domain.entity.Entity
 * @see com.twinker.persistence.utils.CsvHandler
 * @see com.twinker.persistence.utils.EntityMapper
 */
public abstract class Repository<T extends Entity> {
    private static final Logger logger = Logger.getLogger(Repository.class.getName());

    private final CsvHandler csvHandler;
    private final Class<T> type;

    /**
     * Constructs a new Repository for the specified entity type.
     *
     * @param filePath the path to the CSV file for storage
     * @param type     the class of the entity type
     */
    public Repository(String filePath, Class<T> type) {
        this.csvHandler = new CsvHandler(filePath);
        this.type = type;
    }

    /**
     * Retrieves all entities from the repository.
     *
     * @return a list of all entities
     */
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

    /**
     * Retrieves an entity by its ID.
     *
     * @param id the ID of the entity to retrieve
     * @return an Optional containing the entity if found
     */
    public Optional<T> getById(String id) {
        List<T> values = getAll();

        if (id == null) return Optional.empty();

        return values.stream()
                .filter(value -> id.equals(value.getId()))
                .findFirst();
    }

    /**
     * Inserts a new entity into the repository.
     *
     * @param object the entity to insert
     */
    public void insert(T object) {
        List<String[]> data = csvHandler.readCSV();
        data.add(EntityMapper.entityToArray(object));
        csvHandler.writeCSV(data);
    }

    /**
     * Inserts multiple entities into the repository.
     *
     * @param objects the list of entities to insert
     */
    public void insertAll(List<T> objects) {
        List<String[]> data = csvHandler.readCSV();
        for (T object : objects) {
            data.add(EntityMapper.entityToArray(object));
        }
        csvHandler.writeCSV(data);
    }

    /**
     * Updates an existing entity in the repository.
     *
     * @param object the entity to update
     */
    public void update(T object) {
        List<T> data = getAll();
        List<String[]> newData = new ArrayList<>();
        for (T datum : data) {
            if (object.getId().equals(datum.getId())) {
                newData.add(EntityMapper.entityToArray(object));
            } else {
                newData.add(EntityMapper.entityToArray(datum));
            }
        }
        csvHandler.writeCSV(newData);
    }

    /**
     * Deletes an entity from the repository.
     *
     * @param object the entity to delete
     */
    public void delete(T object) {
        deleteById(object.getId());
    }

    /**
     * Deletes all entities from the repository.
     *
     * @param object a sample object of the entity type (unused)
     */
    public void deleteAll(T object) {
        csvHandler.writeCSV(new ArrayList<>());
    }

    /**
     * Searches for an entity by its ID.
     *
     * @param id the ID to search for
     * @return an Optional containing the entity if found
     */
    public Optional<T> searchById(String id) {
        return getAll().stream()
                .filter(obj -> id.equals(obj.getId()))
                .findFirst();
    }

    /**
     * Deletes an entity by its ID.
     *
     * @param id the ID of the entity to delete
     */
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
