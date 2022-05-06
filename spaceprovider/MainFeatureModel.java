package com.example.spaceprovider;

public class MainFeatureModel {
    String name,seats,day_pass,monthly_pass;

    MainFeatureModel(){

    }

    public MainFeatureModel(String name, String seats, String day_pass, String monthly_pass) {
        this.name = name;
        this.seats = seats;
        this.day_pass = day_pass;
        this.monthly_pass = monthly_pass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSeats() {
        return seats;
    }

    public void setSeats(String seats) {
        this.seats = seats;
    }

    public String getDay_pass() {
        return day_pass;
    }

    public void setDay_pass(String day_pass) {
        this.day_pass = day_pass;
    }

    public String getMonthly_pass() {
        return monthly_pass;
    }

    public void setMonthly_pass(String monthly_pass) {
        this.monthly_pass = monthly_pass;
    }
}
