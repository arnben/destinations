package com.alben.destinations.controllers;

import com.alben.destinations.services.DestinationsService;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
class DestinationsControllerTest {

    @InjectMocks
    private DestinationsController destinationsController;

    @Mock
    private DestinationsService destinationsService;

    @BeforeEach
    public void init() {
        RestAssuredMockMvc.standaloneSetup(destinationsController);
    }

    @Test
    @DisplayName("Two locations that are directly connected should returns true.")
    public void directConnectionTest() {
        Mockito.when(destinationsService.isConnected(eq("Boston"), eq("Newark")))
                .thenReturn(true);

        given()
            .contentType(ContentType.JSON)
        .when()
            .get("/connected?origin=Boston&destination=Newark")
        .then()
            .assertThat()
                .status(HttpStatus.OK)
                .body("result", equalTo("yes"));
    }

    @Test
    @DisplayName("Two locations that are not connected should returns false.")
    public void noConnectionTest() {

        Mockito.when(destinationsService.isConnected("Philadelphia", "Albany"))
                .thenReturn(false);

        given()
            .contentType(ContentType.JSON)
        .when()
            .get("/connected?origin=Philadelphia&destination=Albany")
        .then()
            .assertThat()
                .status(HttpStatus.OK)
                .body("result", equalTo("no"));
    }
}