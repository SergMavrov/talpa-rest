package com.talpasolutions.maverick.services;

import com.talpasolutions.maverick.protocol.LocationMapper;
import com.talpasolutions.maverick.protocol.LocationType;
import com.talpasolutions.maverick.protocol.RouteLocation;
import com.talpasolutions.maverick.protocol.SimpleLocation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

@Slf4j
@Service
public class LocationsProcessor implements DataProcessor {

    private List<SimpleLocation> preparedLocationRoutes;

    public List<RouteLocation> readDepots() {
        return filterLocations.apply(preparedLocationRoutes, LocationType.DEPOT);
    }

    public List<RouteLocation> readStores() {
        return filterLocations.apply(preparedLocationRoutes, LocationType.STORE);
    }

    public List<RouteLocation> readCustomers() {
        return filterLocations.apply(preparedLocationRoutes, LocationType.CUSTOMER);
    }

    @Override
    public void prepare(List<SimpleLocation> locationRoutes) {
        this.preparedLocationRoutes = locationRoutes;
    }

    private final BiFunction<List<SimpleLocation>, LocationType, List<RouteLocation>> filterLocations = (locations, type) -> {
        if (Optional.ofNullable(locations).isEmpty()) {
            throw new IllegalStateException("There is no prepared locations.");
        }
        List<RouteLocation> result = locations.stream().filter(location -> location.getType().equals(type))
                .map(LocationMapper::from).collect(Collectors.toList());
        if (result.isEmpty()) {
            throw new IllegalStateException(String.format("Cannot find %s locations.", type.name()));
        }
        return result;
    };

}
