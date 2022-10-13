package com.revature.DAO;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;
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

    public static List<Ticket> getPendingTickets() {
        List<Ticket> tickets = new ArrayList<>();
        try (Connection conn = Database.getConnection();
            PreparedStatement stmt = conn.prepareStatement(
            "SELECT * FROM ticketapi.tickets WHERE status='pending'")) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Ticket ticket = new Ticket();
                ticket.setId(rs.getInt("user_id"));
                ticket.setUserId(rs.getInt("user_id"));
                ticket.setAmount((float) rs.getInt("amount") / 100);
                ticket.setDesc(rs.getString("description"));
                tickets.add(ticket);
            }
            return tickets;
        } catch (SQLException e) {
            e.printStackTrace();
            return tickets;
        } catch (Exception e) {
            e.printStackTrace();
            return tickets;
        }
    }
}
