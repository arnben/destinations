package com.alben.destinations.repositories;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class RoutesRepositoryTest {

    private RoutesRepository routesRepository;

    private static Map<String, String> expectedRoutes = new HashMap<>();

    @BeforeAll
    public static void initAll() {
        expectedRoutes.put("Boston", "New York");
        expectedRoutes.put("Philadelphia", "Newark");
        expectedRoutes.put("Newark", "Boston");
        expectedRoutes.put("Trenton", "Albany");
    }

    @BeforeEach
    public void initEach() {
        routesRepository = new RoutesRepository();
    }

    @Test
    @DisplayName("getAll method should read the list of Route instance from city.txt")
    public void testLoadFile() {
        routesRepository.retrieveAll(route ->
            assertThat(route.getPointB()).isEqualTo(expectedRoutes.get(route.getPointA()))
        );
    }
}