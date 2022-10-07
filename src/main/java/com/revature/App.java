package com.revature;

import io.javalin.Javalin;
import static io.javalin.apibuilder.ApiBuilder.*;
import db.Database;
import com.revature.Model.*;
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
        Database.connect();
    }
}
