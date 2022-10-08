package com.revature.DAO;

import java.sql.*;
import com.revature.Model.User;
import db.Database;


public class UserDao {
    public static void addUser(User user) {
        try (Connection conn = Database.getConnection();
            PreparedStatement stmt = conn.prepareStatement(
        "INSERT INTO ticketapi.accounts(username, password) VALUES(?, ?)")){
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            ResultSet rs = stmt.executeQuery();
            System.out.println(rs);
        } catch (SQLException e) {
            e.getStackTrace();
        } catch (Exception e) {
            e.getStackTrace();
        }
    }
}
