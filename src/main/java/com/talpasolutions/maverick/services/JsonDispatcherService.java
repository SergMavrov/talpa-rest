package com.talpasolutions.maverick.services;

import com.talpasolutions.maverick.protocol.LocationMapper;
import com.talpasolutions.maverick.protocol.LocationType;
import com.talpasolutions.maverick.protocol.MapRoutes;
import com.talpasolutions.maverick.protocol.RouteLocation;
import com.talpasolutions.maverick.protocol.RouteResult;
import com.talpasolutions.maverick.protocol.SimpleLocation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * The class implements Dijkstra algorithm to calculate the shortest route between nodes of graph.
 * The mapOfLocationAndRoutes is indeed graph which contains Locations as nodes.
 */
@Slf4j
@Service
public class JsonDispatcherService implements DispatcherService {

    private static final Double SPEED_METER_PRO_SEC = 16.6667; // 60 km/h = 16.6667 m/s

    private final DataProcessor dataProcessor;
    private final DistanceService distanceService;

    private MapRoutes mapOfLocationsAndRoutes; // map of all possible locations and routes

    @Autowired
    public JsonDispatcherService(DataProcessor dataProcessor, DistanceService distanceService) {
        this.dataProcessor = dataProcessor;
        this.distanceService = distanceService;
    }

    /**
     * Define all possible locations and routes based on the data from storage and put them into the map.
     */
    private void initPossibleGeoRoutes() {
        log.info("Init Geo Routes CUSTOMER <- STORE <- DEPOT, distance in METERS");
        mapOfLocationsAndRoutes = new MapRoutes();
        //We know that we are starting from DEPOT, next stop is STORE and
        //the last destination is CUSTOMER. So the 3 stages all possible routes must be described.
        List<RouteLocation> customers = dataProcessor.readCustomers();
        List<RouteLocation> stores = dataProcessor.readStores();
        List<RouteLocation> depots = dataProcessor.readDepots();
        customers.forEach(
                routeCustomer -> {
                    stores.forEach(routeStore -> {
                        //define all potential STORES with distances for the each CUSTOMER
                        routeCustomer.addSource(routeStore, distanceService.defineDistanceInMeters(routeCustomer, routeStore));
                        depots.forEach(routeDepot -> {
                            //define all potential DEPOTS with instances for the each STORE
                            routeStore.addSource(routeDepot, distanceService.defineDistanceInMeters(routeStore, routeDepot));
                            mapOfLocationsAndRoutes.addRoute(routeDepot);
                        });
                        mapOfLocationsAndRoutes.addRoute(routeStore);
                    });
                    mapOfLocationsAndRoutes.addRoute(routeCustomer);
                });
        log.info("Init Geo Routes is finished");
    }

    /**
     * Calculate the shortest path for all Locations using provided Location as destination.
     *
     * @param destination the location which must end point for all other location.
     */
    private void calculateShortestRouteFromDestination(RouteLocation destination) {
        destination.setDistance((double) 0);
        Set<RouteLocation> visitedLocationRoutes = new HashSet<>();
        Set<RouteLocation> unvisitedLocationRoutes = new HashSet<>();
        unvisitedLocationRoutes.add(destination);
        while (!unvisitedLocationRoutes.isEmpty()) {
            Optional<RouteLocation> closestLocation = unvisitedLocationRoutes.stream()
                    .min(Comparator.comparing(RouteLocation::getDistance));
            closestLocation.ifPresent(current -> { //will always present since we have checked the unvisitedLocations is not empty
                unvisitedLocationRoutes.remove(current);
                  for (Map.Entry<RouteLocation, Double> entry  : current.getSources().entrySet()) {
                      if (!visitedLocationRoutes.contains(entry.getKey())) {
                          calculateMinimumDistance(entry.getKey(), entry.getValue(), current);
                          unvisitedLocationRoutes.add(entry.getKey());
                      }
                  }
                visitedLocationRoutes.add(current);
            });
        }
    }

    private void calculateMinimumDistance(RouteLocation checkLocationRoute, Double distance, RouteLocation currentLocationRoute) {
        Double sourceDistance = currentLocationRoute.getDistance();
        if (sourceDistance + distance < checkLocationRoute.getDistance()) {
            checkLocationRoute.setDistance(sourceDistance + distance);
            List<RouteLocation> shortestPath = new LinkedList<>(currentLocationRoute.getShortestRoute());
            shortestPath.add(currentLocationRoute);
            checkLocationRoute.setShortestRoute(shortestPath);
        }
    }

    public List<RouteResult> getMinimalRoutesForRegisteredCustomers(List<SimpleLocation> locationRoutes) {
        dataProcessor.prepare(locationRoutes);
        initPossibleGeoRoutes();
        log.info(String.format("Map %s", mapOfLocationsAndRoutes));
        // take all CUSTOMERS from the map and prepare the best route to the closest combination STORE-DEPOT
        return mapOfLocationsAndRoutes.getRoutes().stream()
                .filter(l -> l.getType().equals(LocationType.CUSTOMER))
                .sorted(Comparator.comparing(RouteLocation::getName))
                .map(this::getBestRoute).collect(Collectors.toList());
    }

    private RouteResult getBestRoute(RouteLocation customer) {
        // clean route information from previous calculation
        mapOfLocationsAndRoutes.getRoutes().forEach(RouteLocation::clearRoutes);
        calculateShortestRouteFromDestination(customer); //calculate all minim routes from the CUSTOMER to DEPOTS
        log.info(String.format("Map %s", mapOfLocationsAndRoutes));
        // for all possible locations
        // We know that we must start from DEPOT so take the DEPOT from the closest combination DEPOT-STORE
        RouteLocation closestDepot = mapOfLocationsAndRoutes.getRoutes().stream()
                .filter(l -> l.getType().equals(LocationType.DEPOT))
                .min(Comparator.comparing(RouteLocation::getDistance))
                .orElseThrow(() -> new IllegalStateException(String.format("There is no closest DEPOT for %s", customer)));
        // take the STORE from the closest combination DEPOT-STORE route
        RouteLocation closestStore = closestDepot.getShortestRoute().stream()
                .filter(l -> l.getType().equals(LocationType.STORE)).findFirst()
                .orElseThrow(() -> new IllegalStateException(String.format("There is no closest STORE for %s", customer)));
        return RouteResult.builder()
                .customer(LocationMapper.from(customer))
                .store(LocationMapper.from(closestStore))
                .depot(LocationMapper.from(closestDepot))
                .duration(closestDepot.getDistance() / SPEED_METER_PRO_SEC)
                .distance(closestDepot.getDistance())
                .build();
    }

}
