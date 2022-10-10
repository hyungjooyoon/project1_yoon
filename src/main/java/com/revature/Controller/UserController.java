package com.revature.Controller;

import com.revature.Model.User;
import com.revature.Util.Auth;
import com.revature.DAO.UserDao;

public class UserController {
    public static String register(User user) {
        user.setPassword(Auth.hashPassword(user.getPassword()));
        int registered = UserDao.addUser(user);
        if (registered == 1) {
            return "You have successfully registered";
        }
        return "This username is already in use";
    }

    public static String login(User user) {
        User account = UserDao.getUser(user.getUsername());
        boolean same = Auth.checkPassword(user.getPassword(), account.getPassword());
        if (same == true) {
            return "Successfully logged in";
        } else {
            return "Invalid credentials";
        }
    }
}
