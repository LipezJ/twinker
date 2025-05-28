package com.twinker.persistence.utils;

import com.twinker.domain.entity.Entity;

import java.lang.reflect.Field;

/**
 * Utility class for mapping between entities and arrays using reflection.
 * This class provides functionality to convert entity objects to string arrays
 * and vice versa, supporting the CSV-based persistence layer.
 *
 * <p>
 * Features include:
 * <ul>
 * <li>Entity to string array conversion</li>
 * <li>String array to entity conversion</li>
 * <li>Automatic type casting for primitive types</li>
 * <li>Null value handling</li>
 * <li>Reflection-based field access</li>
 * </ul>
 * </p>
 *
 * @author Twinker Development Team
 * @see com.twinker.domain.entity.Entity
 */
public class EntityMapper {
    private static final String NULL = "null-value";

    /**
     * Converts an entity object to a string array.
     * Uses reflection to access all declared fields and convert their values
     * to strings.
     *
     * @param entity the entity to convert
     * @return an array of strings representing the entity's field values
     * @throws RuntimeException if there is an error accessing the fields
     */
    public static String[] entityToArray(Entity entity) {
        Field[] declaredFields = entity.getClass().getDeclaredFields();
        String[] list = new String[declaredFields.length];

        for (int i = 0; i < declaredFields.length; i++) {
            declaredFields[i].setAccessible(true);
            try {
                Object fieldObject = declaredFields[i].get(entity);
                if (fieldObject == null) fieldObject = NULL;
                list[i] = fieldObject.toString();
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Error mapping entity to data", e);
            }
        }
        return list;
    }

    /**
     * Creates an entity from a string array.
     * Uses reflection to create a new instance and set its fields from
     * the string values.
     *
     * @param <T>   the type of entity to create
     * @param clazz the class of the entity
     * @param data  the array of strings containing field values
     * @return a new instance of the entity with fields set from the data
     * @throws RuntimeException if there is an error creating or setting fields
     */
    public static <T extends Entity> T arrayToEntity(Class<T> clazz, String[] data) {
        try {
            T instance = clazz.getDeclaredConstructor().newInstance();
            Field[] fields = clazz.getDeclaredFields();

            Class<?> type;
            String value;

            for (int i = 0; i < fields.length && i < data.length; i++) {
                fields[i].setAccessible(true);
                type = fields[i].getType();
                value = data[i];

                if (type.equals(String.class) && value.equals(NULL)) value = null;

                fields[i].set(instance, castValue(type, value));
            }
            return instance;
        } catch (Exception e) {
            throw new RuntimeException("Error mapping data to entity", e);
        }
    }

    /**
     * Casts a string value to the appropriate type for a field.
     * Supports primitive types (int, double, boolean) and their wrapper classes.
     *
     * @param fieldType the type to cast to
     * @param value     the string value to cast
     * @return the value cast to the appropriate type
     */
    private static Object castValue(Class<?> fieldType, String value) {
        if (value == null) return null;
        if (fieldType == int.class || fieldType == Integer.class) return Integer.parseInt(value);
        if (fieldType == double.class || fieldType == Double.class) return Double.parseDouble(value);
        if (fieldType == boolean.class || fieldType == Boolean.class) return Boolean.parseBoolean(value);
        return value;
    }
}
