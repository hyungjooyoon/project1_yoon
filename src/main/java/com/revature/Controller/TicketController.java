package com.revature.Controller;

import java.util.List;
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
}
