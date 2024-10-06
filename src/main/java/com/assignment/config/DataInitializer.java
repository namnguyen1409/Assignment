package com.assignment.config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.sql.SQLException;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
public class DataInitializer {

    @Autowired
    private DataSource dataSource;

    @PostConstruct
    @Transactional
    public void init() {
        try {
            var path = Paths.get(DataInitializer.class.getClassLoader().getResource("sql/role_permission.sql").toURI());
            System.out.println("path: " + path.toAbsolutePath());
        } catch(URISyntaxException e) {
            System.out.println(e);
        }
        
        runSqlScript("sql/role_permission.sql");

        // check number of locations
        try (var connection = dataSource.getConnection();
             var statement = connection.createStatement();
             var resultSet = statement.executeQuery("SELECT COUNT(*) FROM provinces")) {
            resultSet.next();
            var count = resultSet.getInt(1);
            if (count == 0) {
                runSqlScript("sql/address.sql");
            }
        } catch (SQLException e) {
            System.out.println("Error checking number of locations: " + e.getMessage());
        }

    }

    private void runSqlScript(String scriptPath) {
        try (var connection = dataSource.getConnection();
             var statement = connection.createStatement();
             var reader = new BufferedReader(new InputStreamReader(
                     getClass().getClassLoader().getResourceAsStream(scriptPath), StandardCharsets.UTF_8))) {
            
            String line;
            var sql = new StringBuilder();
            
            while ((line = reader.readLine()) != null) {
                if (line.trim().equalsIgnoreCase("GO")) {
                    if (sql.length() > 0) {
                        statement.execute(sql.toString().trim());
                        sql.setLength(0);
                    }
                } else {
                    sql.append(line).append("\n"); 
                }
            }
            
            if (sql.length() > 0) {
                statement.execute(sql.toString().trim());
            }
    
        } catch (IOException | SQLException e) {
            System.out.println("Error running SQL script: " + e.getMessage());
        }
    }
    
}
