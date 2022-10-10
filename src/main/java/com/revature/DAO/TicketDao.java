package com.revature.DAO;

import java.sql.*;
import db.Database;
import com.revature.Model.Ticket;

public class TicketDao {
    public int addTicket(Ticket ticket) {
        try (Connection conn = Database.getConnection();
            PreparedStatement stmt = conn.prepareStatement(
            "INSERT INTO ticketapi.tickets(user_id, amount, desc) VALUES(?, ?, ?)")) {
            stmt.setInt(1, ticket.getUserId());
            stmt.setFloat(2, ticket.getAmount());
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
