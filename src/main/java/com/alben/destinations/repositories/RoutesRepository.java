package com.alben.destinations.repositories;

import com.alben.destinations.models.Route;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.function.Consumer;

@Repository
public class RoutesRepository {
    public void retrieveAll(Consumer<Route> routeConsumer) {
        //TODO
    }
}
