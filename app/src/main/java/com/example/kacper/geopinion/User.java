package com.example.kacper.geopinion;

/**
 * Created by kacper on 21.10.17.
 */

public class User {
    int id;
    private String login;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String password;
    private String fName;
    private String lName;
    private String mail;
    private char[] settings;



    public User(String fName, String lName, String mail, char[] settings){
        this.fName=fName;
        this.lName=lName;
        this.mail=mail;
        this.settings=settings;


    }

   public  String getfName(){
        return fName;
    }
    public String getMail(){
        return mail;
    }
    public String getlName(){
        return lName;
    }

    public char[] getSettings() {
        return settings;
    }
    public void setfName(String fName){
        this.fName=fName;
    }

    public void setlName(String lName){
        this.lName=lName;
    }
    public void setMail(String mail){
        this.mail=mail;
    }
    public void setSettings(char[] settings){
        this.settings=settings;
    }
}
