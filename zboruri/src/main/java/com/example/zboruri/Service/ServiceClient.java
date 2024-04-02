package com.example.zboruri.Service;

import com.example.zboruri.Domain.Client;
import com.example.zboruri.Repository.ClientRepository;

import java.util.Optional;

public class ServiceClient {
    private ClientRepository clientRepo;

    public ServiceClient(ClientRepository clientRepo) {
        this.clientRepo = clientRepo;
    }

    public Iterable<Client> getAll()
    {
        return clientRepo.findAll();
    }

    public Optional<Client> findOne(String text) {
     return clientRepo.findOne(text);
    }
}
