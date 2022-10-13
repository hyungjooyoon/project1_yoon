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
        System.out.println(ticket_id);
        System.out.println(status);
        return "";

    }
}
