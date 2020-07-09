package com.alben.destinations.repositories;

import com.alben.destinations.models.Route;
import org.springframework.stereotype.Repository;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.function.Consumer;
import java.util.stream.Stream;

@Repository
public class RoutesRepository {
    public void retrieveAll(Consumer<Route> routeConsumer) {
        try (Stream<String> stream = Files.lines(Paths.get((getClass().getClassLoader()
                .getResource("city.txt").toURI())))) {
            stream.map(this::buildRoute)
            .forEach(r -> routeConsumer.accept(r));

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private Route buildRoute(String line) {
        String[] locations = line.split(",");
        return Route.builder().pointA(locations[0].trim()).pointB(locations[1].trim()).build();
    }
}
