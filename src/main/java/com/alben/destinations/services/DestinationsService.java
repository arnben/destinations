package com.alben.destinations.services;

import com.alben.destinations.models.Route;
import com.alben.destinations.repositories.RoutesRepository;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class DestinationsService {

    @Autowired
    private RoutesRepository routesRepository;

    private LinearRouteHolder linearRouteHolder;

    @PostConstruct
    public void loadRoutes() {
        log.info("Pre-calculating routes...");
        linearRouteHolder = new LinearRouteHolder();
        routesRepository.retrieveAll(linearRouteHolder::addRoute);
    }

    public boolean isConnected(String origin, String destination) {
        log.info("Checking connection from " + origin + " to " +destination);
        boolean hasFoundDestination = linearRouteHolder.searchLeft(origin, destination);
        if(!hasFoundDestination)
            hasFoundDestination = linearRouteHolder.searchRight(origin, destination);

        return hasFoundDestination;
    }

    private static class LinearRouteHolder {
        private Map<String, RouteNode> routeHolder = new HashMap<>();

        public void addRoute(Route route) {
            log.info("Adding route from " + route.getPointA() + " and " + route.getPointB());
            if(!routeHolder.containsKey(route.getPointA())) {
                routeHolder.put(route.getPointA(), new RouteNode(route.getPointA()));
            }

            if(!routeHolder.containsKey(route.getPointB())) {
                routeHolder.put(route.getPointB(), new RouteNode(route.getPointB()));
            }

            RouteNode pointARouteNode = routeHolder.get(route.getPointA());
            RouteNode pointBRouteNode = routeHolder.get(route.getPointB());

            pointARouteNode.setRight(pointBRouteNode);
            pointBRouteNode.setLeft(pointARouteNode);
        }

        public boolean searchRight(String origin, String destination) {
            RouteNode routeNode = routeHolder.get(origin);
            while(routeNode.getRight().isPresent()) {
                if(destination.equalsIgnoreCase(routeNode.getRight().get().getLocation()))
                    return true;
                else
                    routeNode = routeNode.getRight().get();
            }
            return false;
        }

        public boolean searchLeft(String origin, String destination) {
            RouteNode routeNode = routeHolder.get(origin);
            while(routeNode.getLeft().isPresent()) {
                if(destination.equalsIgnoreCase(routeNode.getLeft().get().getLocation()))
                    return true;
                else
                    routeNode = routeNode.getLeft().get();
            }
            return false;
        }
    }

    @Getter
    @ToString(exclude = {"left", "right"})
    @EqualsAndHashCode(exclude = {"left", "right"})
    public static class RouteNode {
        private Optional<RouteNode> left = Optional.empty();
        private Optional<RouteNode> right = Optional.empty();
        private String location;

        public RouteNode(String location) {
            this.location = location;
            this.left = Optional.empty();
            this.right = Optional.empty();
        }

        public void setRight(RouteNode routeNode) {
            this.right = Optional.ofNullable(routeNode);
        }

        public void setLeft(RouteNode routeNode) {
            this.left = Optional.ofNullable(routeNode);
        }
    }
}
