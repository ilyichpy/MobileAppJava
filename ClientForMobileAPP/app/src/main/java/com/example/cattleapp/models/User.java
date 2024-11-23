package com.example.cattleapp.models;

public class User {
    private int id;
    private String email;
    private String password;
    private String code;

    // Конструкторы

    public User() {
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // Геттеры и сеттеры

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
