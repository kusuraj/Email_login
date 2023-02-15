package com.example.emaillogin;

public class UserInfo {
    private String userName;

    // string variable for storing
    // employee contact number
    private String userContact;

    // string variable for storing
    // employee address.


    // an empty constructor is
    // required when using
    // Firebase Realtime Database.
    public UserInfo() {

    }

    // created getter and setter methods
    // for all our variables.
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserContact() {
        return userContact;
    }

    public void setUserContact(String userContact) {
        this.userContact = userContact;
    }

}
