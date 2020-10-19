package com.kang.coronacheck1.Item;

public class WorldItem {
    String nation;
    String patient;
    String rank;
    String death;

    public WorldItem() {
        this.nation = nation;
        this.patient = patient;
        this.rank = rank;
        this.death = death;
    }


    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getPatient() {
        return patient;
    }

    public void setPatient(String patient) {
        this.patient = patient;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getDeath() {
        return death;
    }

    public void setDeath(String death) {
        this.death = death;
    }
}
