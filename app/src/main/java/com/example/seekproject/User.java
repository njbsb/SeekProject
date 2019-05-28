package com.example.seekproject;

public class User {
    private String userID, name, email, icNum, phoneNum, address;
    private static User ourInstance = new User();

    public static User getInstance() {
        if (ourInstance == null) {
            ourInstance = new User();
        }
        return ourInstance;
    }


    private User() {
    }

    public static User getOurInstance() {
        return ourInstance;
    }

    public static void setOurInstance(User ourInstance) {
        User.ourInstance = ourInstance;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIcNum() {
        return icNum;
    }

    public void setIcNum(String icNum) {
        this.icNum = icNum;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
