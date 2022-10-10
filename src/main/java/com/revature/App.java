package com.revature;

import io.javalin.Javalin;
import static io.javalin.apibuilder.ApiBuilder.*;

import db.Database;
import com.revature.Model.*;
import com.revature.DAO.UserDao;
import com.revature.Controller.UserController;
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
    }
}
