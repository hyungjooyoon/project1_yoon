package com.revature.Controller;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.revature.Model.Ticket;
import com.revature.DAO.TicketDao;


public class TicketController {
    public static String register(Ticket ticket) {
        int added = TicketDao.addTicket(ticket);
        if (added == 1) {
            return "Ticket submitted succesfully";
        } else {
            return "Somthing went wrong. Try again!";
        }
    }

    public static List<Ticket> getList() {
        List<Ticket> tickets = TicketDao.getPendingTickets();
        return tickets;
    }

    public static String processTicket(byte[] jsonData) {
        ObjectMapper objectMapper = new ObjectMapper();
        int ticket_id = 0;
        String status = "";
        try {
            JsonNode rootNode = objectMapper.readTree(jsonData);
            JsonNode idNode = rootNode.path("ticket_id");
            ticket_id = idNode.asInt();
            JsonNode statusNode = rootNode.path("status");
            status = statusNode.asText();
        } catch (IOException e) {
            e.printStackTrace();
            return "Error processing ticket";
        }
        String ticketStatus = TicketDao.getStatus(ticket_id);
        System.out.println(ticketStatus);
        if (ticketStatus.equals("pending")) {
            int updated = TicketDao.updateStatus(ticket_id, status);
            if (updated == 1) {
                return "Ticket was processed";
            } else {
                return "Error processing ticket";
            }
        } else if (ticketStatus.equals("")) {
            return "Ticket doesn't exist";    
        } else {
            return "Ticket is already processed";
        }
    }

    public static List<Ticket> getPreviousTickets(int user_id) {
        List<Ticket> tickets = TicketDao.getAllTicketsById(user_id);
        return tickets;
    }
}
