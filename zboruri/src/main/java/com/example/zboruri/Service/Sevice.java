package com.example.zboruri.Service;

import com.example.zboruri.Repository.ClientRepository;
import com.example.zboruri.Repository.FlightRepo;
import com.example.zboruri.Repository.TicketRepo;

public class Sevice {

    private TicketRepo ticketRepo;
    private FlightRepo flightRepo;

    public Sevice(ClientRepository clientRepo, TicketRepo ticketRepo, FlightRepo flightRepo) {
        this.ticketRepo = ticketRepo;
        this.flightRepo = flightRepo;
    }




}
