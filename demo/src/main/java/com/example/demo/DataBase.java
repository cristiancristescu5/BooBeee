package com.example.demo;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBase {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/BooDB";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "STUD3NT";
    private static final HikariConfig config;
    private static final HikariDataSource dataSource;
    static {
        config = new HikariConfig();
        config.setUsername(DB_USER);
        config.setPassword(DB_PASSWORD);
        config.setJdbcUrl(DB_URL);
        config.setMaximumPoolSize(20);
        config.setDriverClassName("org.postgresql.Driver");
        dataSource = new HikariDataSource(config);
    }
//    public DataBase(){
//        config.setUsername(DB_USER);
//        config.setPassword(DB_PASSWORD);
//        config.setJdbcUrl(DB_URL);
//        config.setMaximumPoolSize(20);
//        dataSource = new HikariDataSource(config);
//    }
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
    private static HikariDataSource getDataSource() {
        HikariConfig config = new HikariConfig();
        config.setUsername(DB_USER);
        config.setPassword(DB_PASSWORD);
        config.setJdbcUrl(DB_URL);
        config.setMaximumPoolSize(20);
        return new HikariDataSource(config);
    }
    public static void closeConnection() throws SQLException {
        dataSource.getConnection().close();
    }
    public void rollBack() throws SQLException {
        dataSource.getConnection().rollback();
    }
}