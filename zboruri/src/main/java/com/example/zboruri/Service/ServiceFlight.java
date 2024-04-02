package com.example.zboruri.Service;

import com.example.zboruri.AppExceptions.ServiceException;
import com.example.zboruri.Domain.Flight;
import com.example.zboruri.Repository.FlightRepo;
import com.example.zboruri.Utils.Observer.Observable;

public class ServiceFlight implements Observable {
    FlightRepo flightRepo;

    public ServiceFlight(FlightRepo flightRepo) {
        this.flightRepo = flightRepo;
    }
    public  Iterable<Flight> findAll()
    {
        return flightRepo.findAll();
    }

    public void update(Flight selection) {
        var response=flightRepo.update(selection);
            if (response.isEmpty()) {
                throw new ServiceException("Zbor inexistent!");
            }
        this.notifyAllObservers(null);
    }
}
