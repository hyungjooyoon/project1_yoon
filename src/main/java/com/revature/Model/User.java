package com.revature.Model;

public class User {
    private String username = "";
    private String password = "";
    private String role = "";

    public void setUserName (String username) {
        this.username = username;
    }

    public String getUserName () {
        return this.username;
    }

    public void setPassword (String password) {
        this.password = password;
    }

    public String getPassword () {
        return this.password;
    }

    public void setRole (String role) {
	this.role = role;
    }

    public String getRole () {
	return this.role;
    }
}
