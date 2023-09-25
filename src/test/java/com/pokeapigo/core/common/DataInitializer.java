package com.pokeapigo.core.common;

import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Component
public class DataInitializer {

    public static void insertInitDataOnce(DataSource dataSource, String sqlScript) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            ScriptUtils.executeSqlScript(connection, new ClassPathResource("sql/cleanUp.sql"));
            ScriptUtils.executeSqlScript(connection, new ClassPathResource(sqlScript));
        }
    }
}
