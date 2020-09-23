package com.login_signup_screendesign_demo.models;

public class User {
    private int id ;
    private String name;
    private String email;
    private String password;
    private String gender;
    private String location;
    private String phonenumber;
    private String token;

    public User(String name,String email,String password,String gender){
        this.name = name;
        this.email = email;
        this.password = password;
        this.gender = gender;

    }
    public User(int id,String name,String email,String gender){
        this.id = id;
        this.name = name;
        this.email = email;
        this.gender = gender;

    }
    public User(String name,String email,String password,String phonenumber, String location){

        this.name = name;
        this.email = email;
        this.password = password;
        this.phonenumber = phonenumber;
        this.location = location;

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getGender() {
        return gender;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public void setToken(String token) {
        this.token = token;
    }
}
