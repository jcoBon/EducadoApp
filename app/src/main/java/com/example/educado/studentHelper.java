package com.example.educado;

public class studentHelper {

    String mobileNumber,emailAdd,fName,lName,password,studentNo,userType, year,section ;

    public studentHelper() {
    }

    public studentHelper(String mobileNumber, String emailAdd, String fName, String lName, String password, String studentNo, String userType, String year, String section) {
        this.mobileNumber = mobileNumber;
        this.emailAdd = emailAdd;
        this.fName = fName;
        this.lName = lName;
        this.password = password;
        this.studentNo = studentNo;
        this.userType = userType;
        this.year = year;
        this.section = section;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEmailAdd() {
        return emailAdd;
    }

    public void setEmailAdd(String emailAdd) {
        this.emailAdd = emailAdd;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }
}
