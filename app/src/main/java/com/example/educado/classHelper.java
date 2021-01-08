package com.example.educado;

public class classHelper {

    String subjectCode, subjectTitle,subjectYear,subjectSection;

    public classHelper() {
    }

    public classHelper(String subjectCode, String subjectTitle, String subjectYear, String subjectSection) {
        this.subjectCode = subjectCode;
        this.subjectTitle = subjectTitle;
        this.subjectYear = subjectYear;
        this.subjectSection = subjectSection;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getSubjectTitle() {
        return subjectTitle;
    }

    public void setSubjectTitle(String subjectTitle) {
        this.subjectTitle = subjectTitle;
    }

    public String getSubjectYear() {
        return subjectYear;
    }

    public void setSubjectYear(String subjectYear) {
        this.subjectYear = subjectYear;
    }

    public String getSubjectSection() {
        return subjectSection;
    }

    public void setSubjectSection(String subjectSection) {
        this.subjectSection = subjectSection;
    }
}
