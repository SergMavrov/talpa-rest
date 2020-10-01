package com.talpasolutions.maverick.protocol;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The object contains geo coordinates and address. Additionally it contains the information about
 * possible sources of delivery. The class will be used for the shortest route to end destination calculation.
 * It means that after calculation each GeoInfo knows the shortest distance and shortest combination of location
 * which can rule us to the final destination. Usually the final destination is the customer so we can expect
 * that DEPOT will contains two locations: STORE->CUSTOMER, the STORE will know closest CUSTOMER, the CUSTOMER
 * usually has nothing since it is the final destination.
 */

@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Slf4j
public class RouteLocation extends SimpleLocation {


    /**
     * The combination of location which rule as the the final destination by shortest way.
     */
    @EqualsAndHashCode.Exclude
    private List<RouteLocation> shortestRoute;

    /**
     * The distance to the final destination after calculation.
     */
    @EqualsAndHashCode.Exclude
    private Double distance;

    /**
     * The list of locations which can potentially send us goods. Also we have the distances to these locations.
     */
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Map<RouteLocation, Double> sources;

    public Double getDistance() {
        if (distance == null) {
            distance = Double.MAX_VALUE;
        }
        return distance;
    }

    public List<RouteLocation> getShortestRoute() {
        if (shortestRoute == null) {
            shortestRoute = new LinkedList<>();
        }
        return shortestRoute;
    }

    public Map<RouteLocation, Double> getSources() {
        if (sources == null) {
            sources = new HashMap<>();
        }
        return sources;
    }

    public void addSource(RouteLocation source, Double distance) {
        log.info(String.format("%s <- %s = %s", name, source.getName(), distance));
        getSources().put(source, distance);
    }

    /**
     * Forget all pre-calculated routes and back to init values.
     */
    public void clearRoutes() {
        distance = null;
        shortestRoute = null;
    }

    @Override
    public String toString() {
        return "\nRouteLocation{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", type=" + type +
                ", shortestRoute=" + shortestRoute +
                ", distance=" + distance +
                ", sources=" + getSources().keySet().stream().map(SimpleLocation::getName).collect(Collectors.joining()) +
                '}';
    }
}
