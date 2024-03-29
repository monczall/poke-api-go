package com.pokeapigo.core.common.util.constants;

public final class Constants {

    public static final String PRIVATE_CONSTRUCTOR_EXCEPTION_MESSAGE = "This is utility class and cannot be instantiated";
    public static final String EMAIL_REGEXP = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}";
    public static final String CSV_EXPORT_FILE_NAME = "CSVExport";

    private Constants() {
        throw new UnsupportedOperationException(PRIVATE_CONSTRUCTOR_EXCEPTION_MESSAGE);
    }
}
