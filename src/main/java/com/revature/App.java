package com.revature;

import io.javalin.Javalin;
import io.javalin.http.UnauthorizedResponse;
import static io.javalin.apibuilder.ApiBuilder.*;

import java.util.List;

import db.Database;
import com.revature.Model.*;
import com.revature.DAO.UserDao;
import com.revature.Controller.UserController;
import com.revature.DAO.TicketDao;
import com.revature.Controller.TicketController;
import com.revature.Util.SessionUtil;



public class App 
{   
     public static void main( String[] args ) {
        Javalin app = Javalin.create(config -> {
            config.jetty.sessionHandler(SessionUtil.supplier);
        }).start(7070);
        app.get("/", ctx -> ctx.result("Hello!") );

        
        app.routes(() -> {
            path("register", () -> {
                get(ctx -> {
                    ctx.result("Please Register");
                });
                post(ctx -> {
                    User user = ctx.bodyAsClass(User.class);
                    String res = UserController.register(user);
                    ctx.result(res);
                });
            });
        });

        app.routes(() -> {
            path("login", () -> {
                get(ctx -> {
                    ctx.result("Log In");
                });
                post(ctx -> {
                    User user = ctx.bodyAsClass(User.class);
                    int loggedIn = UserController.login(user);
                    if (loggedIn == 1) {
                        ctx.sessionAttribute("username", user.getUsername());
                        ctx.sessionAttribute("role", user.getRole());
                        ctx.result("Successfully Logged In");
                    } else {
                        ctx.result("Invalid Credentials");
                    }
                });
            });
        });

        app.routes(() -> {
            path("tickets", () -> {
                before(ctx -> {
                    String username = ctx.sessionAttribute("username");
                    if (username == null) {
                        throw new UnauthorizedResponse();
                    }
                });
                get(ctx -> {
                    ctx.result("Previously Submitted Tickets");
                });
                post(ctx -> {
                    Ticket ticket = ctx.bodyAsClass(Ticket.class);
                    String res = TicketController.register(ticket);
                    System.out.println(ticket.getId());
                    System.out.println(ticket.getAmount());
                    System.out.println(ticket.getDesc());
                    ctx.result(res);                  
                });
            });
        });

        app.routes(() -> {
            path("pending", () -> {
                before(ctx -> {
                    String role = ctx.sessionAttribute("role");
                    if (role == null || !role.equals("manager")) {
                        throw new UnauthorizedResponse();
                    }
                });
                get(ctx -> {
                    List<Ticket> tickets = TicketController.getList();
                    if (tickets.size() == 0) {
                        ctx.result("No more pending ticktes");
                    } else {
                        ctx.json(tickets);
                    }
                });
                post(ctx -> {
                    byte[] jsonData = ctx.bodyAsBytes();
                    ctx.result(TicketController.processTicket(jsonData));
                });
            });
        });
    }
}
