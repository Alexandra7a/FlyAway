package com.example.zboruri.Service;

import com.example.zboruri.Domain.Ticket;
import com.example.zboruri.Repository.TicketRepo;
import com.example.zboruri.Utils.Observer.Observable;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ServiceTickets implements Observable {
    private TicketRepo ticketRepo;

    public ServiceTickets(TicketRepo ticketRepo) {
        this.ticketRepo = ticketRepo;
    }

    public Iterable<Ticket> findAll()
    {
        return ticketRepo.findAll();
    }
    /**Atunci cand se cumpara un loc se va fda update la tabelul de locuri si notifyALl cei inscrisi ca sa li se modifice si
     * lor tabelul cu locuri disponibile*/
    public Optional<Ticket> save(String usename, Long flightID, LocalDateTime date)
    {
        Ticket tiket=new Ticket(usename,date,flightID);
        this.notifyAllObservers(null); /// momentan il lasam null si vedem dupa
        return ticketRepo.save(tiket);
    }

    public int takenSeats(Long id) {
        int sold= StreamSupport.stream(ticketRepo.findAll().spliterator(),false)
                .filter(ticket -> ticket.getFlightID()==id)
                .collect(Collectors.toList())
                .size();
        return sold;
    }
}
