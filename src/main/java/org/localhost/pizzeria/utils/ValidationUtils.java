package org.localhost.pizzeria.utils;

public final class ValidationUtils {

    private ValidationUtils() {
    }

    public static <T> void validateNotNull(T value, String fieldName) {
        if (value == null) {
            throw new IllegalArgumentException(String.format("%s cannot be null", fieldName));
        }
    }

    public static void validateNotNull(String fieldName, Object... values) {
        for (Object value : values) {
            if (value == null) {
                throw new IllegalArgumentException(String.format("%s cannot be null", fieldName));
            }
        }
    }
}
