package com.revature.DAO;

import java.sql.*;
import com.revature.Model.User;
import db.Database;


public class UserDao {
    public static int addUser(User user) {
        try (Connection conn = Database.getConnection();
            PreparedStatement stmt = conn.prepareStatement(
        "INSERT INTO ticketapi.accounts(username, password) VALUES(?, ?)")){
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            try {
                stmt.executeUpdate();
                return 1;
            } catch (SQLException e) {
                e.printStackTrace();
                return 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static User getUser(String username) {
        User user = new User();
        try (Connection conn = Database.getConnection();
            PreparedStatement stmt = conn.prepareStatement(
            "SELECT * FROM ticketapi.accounts WHERE username = ?")) {
            stmt.setString(1, username);
            try {
                ResultSet rs = stmt.executeQuery();
                rs.next();
                user.setId(rs.getInt("user_id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setRole(rs.getString("role"));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
}
