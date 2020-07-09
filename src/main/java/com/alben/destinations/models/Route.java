package com.alben.destinations.models;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Builder
@Getter
@EqualsAndHashCode
public class Route {
    private String pointA;
    private String pointB;
}
