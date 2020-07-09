package com.alben.destinations.integration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DestinationsApiIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Two locations that are directly connected should return 'yes' as response.")
    public void directConnectionTest() throws Exception {
        mockMvc.perform(
                get("/connected?origin=Boston&destination=Newark"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value("yes"));
    }

    @Test
    @DisplayName("Two locations that are indirectly connected should return 'yes' as response.")
    public void indirectConnectionTest() throws Exception {
        mockMvc.perform(
                get("/connected?origin=Boston&destination=Philadelphia"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value("yes"));
    }

    @Test
    @DisplayName("Two locations that are not connected should return 'no' as response.")
    public void noConnectionTest() throws Exception {
        mockMvc.perform(
                get("/connected?origin=Philadelphia&destination=Albany"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value("no"));
    }

    @Test
    @DisplayName("Only origin is provided then connected API returns 404")
    public void originOnlyProvided() throws Exception {
        mockMvc.perform(
                get("/connected?origin=Boston"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Only destination is provided then connected API returns 404")
    public void destinationOnlyProvided() throws Exception {
        mockMvc.perform(
                get("/connected?destination=Philadelphia"))
                .andExpect(status().isBadRequest());
    }
}
