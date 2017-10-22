package com.example.kacper.geopinion;

/**
 * Created by kacper on 21.10.17.
 */

class User {
    int id;

    public String getLogin() {
        return login;
    }


    String getPassword() {
        return password;
    }


    private String login,password,fName,lName,mail;
    private String settings;



    User(String fName, String lName, String login, String password, String mail, String settings){
        this.login=login;
        this.password=password;
        this.fName=fName;
        this.lName=lName;
        this.mail=mail;
        this.settings=settings;


    }

   String getfName(){
        return fName;
    }
    String getMail(){
        return mail;
    }
    String getlName(){
        return lName;
    }

    String getSettings() {
        return settings;
    }

}
