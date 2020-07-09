package com.alben.destinations.controllers;

import com.alben.destinations.controllers.models.ConnectedApiResponse;
import com.alben.destinations.services.DestinationsService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/connected")
public class DestinationsController {

    @Autowired
    private DestinationsService destinationsService;

    @GetMapping
    @ApiOperation(value = "Connected API", notes = "Returns 'yes' or 'no' in the result attribute when provided origin and destination locations are connected.")
    public ResponseEntity<ConnectedApiResponse> connected(
            @RequestParam String origin,
            @RequestParam String destination) {

        boolean isConnected = destinationsService.isConnected(origin, destination);

        return ResponseEntity.ok(ConnectedApiResponse.builder()
                .result(isConnected? "yes" : "no")
                .build());
    }

}
