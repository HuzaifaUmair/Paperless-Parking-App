package com.example.myapplication;

public class model {
    String number,status,exit_time;

    public model() {
    }

    public model(String number, String status,String exit_time) {
        this.number = number;
        this.status = status;
        this.exit_time = exit_time;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getExit_time() {
        return exit_time;
    }

    public void setExit_time(String exit_time) {
        this.exit_time = exit_time;
    }
}
