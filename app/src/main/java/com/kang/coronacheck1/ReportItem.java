package com.kang.coronacheck1;

public class ReportItem {
    String title;
    String patient;
    String daily;
    String death;

    public ReportItem() {
        this.title = title;
        this.patient = patient;
        this.daily = daily;
        this.death = death;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPatient() {
        return patient;
    }

    public void setPatient(String patient) {
        this.patient = patient;
    }

    public String getDaily() {
        return daily;
    }

    public void setDaily(String daily) {
        this.daily = daily;
    }

    public String getDeath() {
        return death;
    }

    public void setDeath(String death) {
        this.death = death;
    }
}


