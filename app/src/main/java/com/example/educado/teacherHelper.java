package com.example.educado;

public class teacherHelper {

    String email, password, userName,userType;

    public teacherHelper() {
    }

    public teacherHelper(String email, String password, String userName, String userType) {
        this.email = email;
        this.password = password;
        this.userName = userName;
        this.userType = userType;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
