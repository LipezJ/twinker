package com.twinker.persistence.utils;

import com.twinker.domain.entity.Entity;

import java.lang.reflect.Field;

public class EntityMapper {
    private static final String NULL = "null-value";

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

    private static Object castValue(Class<?> fieldType, String value) {
        if (value == null) return null;
        if (fieldType == int.class || fieldType == Integer.class) return Integer.parseInt(value);
        if (fieldType == double.class || fieldType == Double.class) return Double.parseDouble(value);
        if (fieldType == boolean.class || fieldType == Boolean.class) return Boolean.parseBoolean(value);
        return value;
    }
}
