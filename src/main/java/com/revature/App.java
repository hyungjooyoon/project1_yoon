package com.revature;

import io.javalin.Javalin;
import io.javalin.http.UnauthorizedResponse;



import static io.javalin.apibuilder.ApiBuilder.*;
import java.util.List;

import com.revature.Model.*;
import com.revature.Controller.UserController;
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
                    User user = ctx.bodyValidator(User.class).get();
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
                    User user = ctx.bodyValidator(User.class).get();
                    int loggedIn = UserController.login(user);
                    if (loggedIn == 1) {
                        ctx.sessionAttribute("user_id", user.getId());
                        ctx.sessionAttribute("username", user.getUsername());
                        ctx.sessionAttribute("role", user.getRole());
                        ctx.result("Successfully Logged In");
                    } else {
                        ctx.status(401).result("Invalid Credentials");
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
                    List<Ticket> tickets = TicketController.getPreviousTickets(ctx.sessionAttribute("user_id"));
                    if (tickets.size() == 0) {
                        ctx.result("No previously submitted tickets");
                    } else {
                        ctx.json(tickets);
                    }
                });
                post(ctx -> {
                    Ticket ticket = ctx.bodyValidator(Ticket.class).get();
                    ticket.setUserId(ctx.sessionAttribute("user_id"));
                    int res = TicketController.submit(ticket);
                    if (res == 1) {
                        ctx.status(200).result("Ticket submitted succesfully");
                    }
                    ctx.status(400).result("Error submitting ticket");                  
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
                    int res = TicketController.processTicket(jsonData);
                    if (res == 0) {
                        ctx.status(400).result("Error processing ticket");
                    } else if (res == 1) {
                        ctx.status(200).result("Succesfully processed ticket");
                    } else if (res == 2) {
                        ctx.status(404).result("Ticket doesn't exist");
                    } else if (res == 3) {
                        ctx.status(405).result("Ticket is already processed");
                    }
                });
            });
        });

        app.routes(() -> {
            path("role", () -> {
                before(ctx -> {
                    String role = ctx.sessionAttribute("role");
                    if (role == null || !role.equals("manager")) {
                        throw new UnauthorizedResponse();
                    }
                });

                post(ctx -> {
                    byte[] jsonData = ctx.bodyAsBytes();
                    int res = UserController.changeRole(jsonData);
                    if (res == 0) {
                        ctx.status(400).result("Invalid input");
                    } else if (res == 1) {
                        ctx.status(200).result("Role changed succesfully");
                    } else if (res == 2)  {
                        ctx.status(404).result("User doesn't exist");
                    }
                });
            });
        });
    }
}
