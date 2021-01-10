package com.example.educado;

public class studentPDFHelper {
    String name,url,studentNo,subSec,subYear,subCode,subTitle;

    public studentPDFHelper() {
    }

    public studentPDFHelper(String name, String url, String studentNo, String subSec, String subYear, String subCode, String subTitle) {
        this.name = name;
        this.url = url;
        this.studentNo = studentNo;
        this.subSec = subSec;
        this.subYear = subYear;
        this.subCode = subCode;
        this.subTitle = subTitle;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }

    public String getSubSec() {
        return subSec;
    }

    public void setSubSec(String subSec) {
        this.subSec = subSec;
    }

    public String getSubYear() {
        return subYear;
    }

    public void setSubYear(String subYear) {
        this.subYear = subYear;
    }

    public String getSubCode() {
        return subCode;
    }

    public void setSubCode(String subCode) {
        this.subCode = subCode;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }
}
