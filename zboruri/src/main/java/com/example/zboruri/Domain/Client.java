package com.example.zboruri.Domain;

public class Client extends Entity<String>{

    private String usernaem;
    private String name;

    public Client(String usernaem, String name) {
        this.usernaem = usernaem;
        this.name = name;
    }

    public String getUsernaem() {
        return usernaem;
    }

    public void setUsernaem(String usernaem) {
        this.usernaem = usernaem;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Client{" +
                "usernaem='" + usernaem + '\'' +
                ", name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
