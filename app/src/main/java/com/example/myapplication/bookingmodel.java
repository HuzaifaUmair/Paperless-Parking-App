package com.example.myapplication;

public class bookingmodel {
    String car_number ,date,entrance_time,exit_time,payment_status,slot_id,user_id;

    public bookingmodel() {
    }

    public bookingmodel(String car_number, String date, String entrance_time, String exit_time,String payment_status , String slot_id, String user_id) {
        this.car_number = car_number;
        this.date = date;
        this.entrance_time = entrance_time;
        this.exit_time = exit_time;
        this.payment_status = payment_status;
        this.slot_id = slot_id;
        this.user_id = user_id;
    }

    public String getCar_number() {
        return car_number;
    }

    public void setCar_number(String car_number) {
        this.car_number = car_number;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEntrance_time() {
        return entrance_time;
    }

    public void setEntrance_time(String entrance_time) {
        this.entrance_time = entrance_time;
    }

    public String getExit_time() {
        return exit_time;
    }

    public String getPayment_status() {
        return payment_status;
    }

    public void setPayment_status(String payment_status) {
        this.payment_status = payment_status;
    }

    public void setExit_time(String exit_time) {
        this.exit_time = exit_time;
    }

    public String getSlot_id() {
        return slot_id;
    }

    public void setSlot_id(String slot_id) {
        this.slot_id = slot_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
