package db;

import java.sql.*;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Database {
    private static Properties prop = new Properties();
    private static String url = "";
    private static String username = "";
    private static String password = "";

    private static void getProps() {
        try {
            prop.load(Database.class.getClassLoader().getResourceAsStream("db.properties"));
            url = prop.getProperty("url");
            username = prop.getProperty("user ");
            password = prop.getProperty("pass");
            System.out.println(url);
            System.out.println(username);
            System.out.println(password);  
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void connect() {
        Database.getProps();
        return;
    }
}
