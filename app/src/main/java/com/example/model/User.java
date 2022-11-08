package com.example.model;

public class User {
    private int id;
    private String username;
    private String password;
    private int id_auth;  //Mã quyền

    public User() {
    }

    public User(int id, String username, String password, int id_auth) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.id_auth = id_auth;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId_auth() {
        return id_auth;
    }

    public void setId_auth(int id_auth) {
        this.id_auth = id_auth;
    }
}
