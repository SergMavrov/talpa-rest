package com.talpasolutions.maverick.services;

import com.talpasolutions.maverick.protocol.RouteResult;
import com.talpasolutions.maverick.protocol.SimpleLocation;

import java.util.List;

public interface DispatcherService {

    /**
     * Prepare best routes for all found in the data customers.
     *
     * @return list of best routes related with each found customer.
     */
    List<RouteResult> getMinimalRoutesForRegisteredCustomers(List<SimpleLocation> locationRoutes);

}
