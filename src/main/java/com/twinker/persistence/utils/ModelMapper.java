package com.twinker.persistence.utils;

import com.twinker.persistence.model.Model;

import java.lang.reflect.Field;

public class ModelMapper {
    public static String[] modelToArray(Model model) {
        Field[] declaredFields = model.getClass().getDeclaredFields();
        String[] list = new String[declaredFields.length];

        for (int i = 0; i < declaredFields.length; i++) {
            declaredFields[i].setAccessible(true);
            try {
                Object fieldObject = declaredFields[i].get(model);
                list[i] = fieldObject.toString();
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Error mapping model to data", e);
            }
        }
        return list;
    }

    public static <T extends Model> T arrayToModel(Class<T> clazz, String[] data) {
        try {
            T instance = clazz.getDeclaredConstructor().newInstance();
            Field[] fields = clazz.getDeclaredFields();

            for (int i = 0; i < fields.length && i < data.length; i++) {
                fields[i].setAccessible(true);
                fields[i].set(instance, castValue(fields[i].getType(), data[i]));
            }
            return instance;
        } catch (Exception e) {
            throw new RuntimeException("Error mapping data to model", e);
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
