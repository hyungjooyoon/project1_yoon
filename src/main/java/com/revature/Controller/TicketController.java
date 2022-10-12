package com.revature.Controller;

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
}
