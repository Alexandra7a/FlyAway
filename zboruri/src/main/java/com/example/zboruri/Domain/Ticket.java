package com.example.zboruri.Domain;

import java.time.LocalDateTime;

public class Ticket extends Entity<Long>{
    private String username;
    private LocalDateTime purchaseTime;
    private long FlightID;

    public Ticket(String username, LocalDateTime purchaseTime, long flightID) {
        this.username = username;
        this.purchaseTime = purchaseTime;
        FlightID = flightID;

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDateTime getPurchaseTime() {
        return purchaseTime;
    }

    public void setPurchaseTime(LocalDateTime purchaseTime) {
        this.purchaseTime = purchaseTime;
    }

    public long getFlightID() {
        return FlightID;
    }

    public void setFlightID(long flightID) {
        FlightID = flightID;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "username='" + username + '\'' +
                ", purchaseTime=" + purchaseTime +
                ", FlightID=" + FlightID +
                ", id=" + id +
                '}';
    }
}
