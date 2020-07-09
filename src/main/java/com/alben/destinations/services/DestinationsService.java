package com.alben.destinations.services;

import com.alben.destinations.repositories.RoutesRepository;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class DestinationsService {

    private RoutesRepository routesRepository;

    @PostConstruct
    public void loadRoutes() {

    }

    public boolean isConnected(String origin, String destination) {
        if(origin.equalsIgnoreCase("boston") && destination.equalsIgnoreCase("newark"))
            return true;

        if(origin.equalsIgnoreCase("boston") && destination.equalsIgnoreCase("philadelphia"))
            return true;
        return false;
    }
}
