package com.github.kafka.utils;

public class ConversionUtils {

    private ConversionUtils() {
    }

    public static String valueOrDefault(CharSequence value) {
        return value != null ? String.valueOf(value) : "";
    }

}
