package com.revature.DAO;

import java.sql.*;
import db.Database;
import com.revature.Model.Ticket;

public class TicketDao {
    public static int addTicket(Ticket ticket) {
        try (Connection conn = Database.getConnection();
            PreparedStatement stmt = conn.prepareStatement(
            "INSERT INTO ticketapi.tickets(user_id, amount, description) VALUES(?, ?, ?)")) {
            stmt.setInt(1, ticket.getUserId());
            stmt.setInt(2, (int) (ticket.getAmount() * 100));
            stmt.setString(3, ticket.getDesc());
            try {
                stmt.executeUpdate();
                return 1;
            } catch (SQLException e) {
                e.printStackTrace();
                return 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    } 
}
