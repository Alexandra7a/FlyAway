package com.example.zboruri.Repository;


public class DBConnection {
    public String DB_URL;
    public String DB_USER;
    public String DB_PASSWD;

    public DBConnection(String dbUrl, String dbUser, String dbPasswd) {
        DB_URL = dbUrl;
        DB_USER = dbUser;
        DB_PASSWD = dbPasswd;
    }
    public DBConnection(){
        DB_URL = "jdbc:postgresql://localhost:5432/Zboruri";
        DB_USER = "postgres";
        DB_PASSWD = "Alexandra10";
    }

}
