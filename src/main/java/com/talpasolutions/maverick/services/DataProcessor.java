package com.talpasolutions.maverick.services;


import com.talpasolutions.maverick.protocol.RouteLocation;
import com.talpasolutions.maverick.protocol.SimpleLocation;

import java.util.List;

public interface DataProcessor {

    List<RouteLocation> readDepots();

    List<RouteLocation> readStores();

    List<RouteLocation> readCustomers();

    void prepare(List<SimpleLocation> locationRoutes);

}
