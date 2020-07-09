package com.alben.destinations.services;


import com.alben.destinations.models.Route;
import com.alben.destinations.repositories.RoutesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DestinationsServiceTest {

    @InjectMocks
    private DestinationsService destinationsService;

    @Mock
    private RoutesRepository routesRepository;

    @BeforeEach
    public void initAll() {
        List<Route> routes = new ArrayList<>();
        routes.add(Route.builder().pointA("Boston").pointB("New York").build());
        routes.add(Route.builder().pointA("Philadelphia").pointB("Newark").build());
        routes.add(Route.builder().pointA("Newark").pointB("Boston").build());
        routes.add(Route.builder().pointA("Trenton").pointB("Albany").build());

        doAnswer(invocation -> {
            Consumer<Route> consumer = invocation.getArgument(0, Consumer.class);
            routes.stream().forEach(r -> consumer.accept(r));
            return null;
        }).when(routesRepository).retrieveAll(any());

        destinationsService.loadRoutes();
    }

    @Test
    @DisplayName("Two locations that are directly connected should returns true.")
    public void directConnectionTest() throws Exception {
        assertThat(destinationsService.isConnected("Boston", "Newark"))
                .isTrue();
    }

    @Test
    @DisplayName("Two locations that are indirectly connected should returns true.")
    public void indirectConnectionTest() throws Exception {
        assertThat(destinationsService.isConnected("Boston", "Philadelphia"))
                .isTrue();
    }

    @Test
    @DisplayName("Two locations that are not connected should returns false")
    public void noConnectionTest() throws Exception {
        assertThat(destinationsService.isConnected("Philadelphia", "Albany"))
                .isFalse();
    }

}