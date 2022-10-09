package com.revature;

import io.javalin.Javalin;
import static io.javalin.apibuilder.ApiBuilder.*;

import java.security.NoSuchAlgorithmException;

import db.Database;
import com.revature.Model.*;
import com.revature.DAO.UserDao;
import com.revature.Util.Auth;
/**
 * Hello world!
 *
 */
public class App 
{   
     public static void main( String[] args ) {
        Javalin app = Javalin.create().start(7070);
        app.get("/", ctx -> ctx.result("Hello!") );

        
        app.routes(() -> {
            path("register", () -> {
                get(ctx -> {
                    ctx.result("Please Register");
                });
                post(ctx -> {
                    User user = ctx.bodyAsClass(User.class);
                    UserDao.addUser(user);
                    ctx.result("You have Registered");
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
                    System.out.println(user);
                    ctx.result("Logged In!");
                });
            });
        });
    }
}
