package com.talpasolutions.maverick.services;

import com.talpasolutions.maverick.protocol.RouteLocation;

public interface DistanceService {

    /**
     * Calculate the distance in meters between two locations.
     * @param start the location as start point
     * @param finish the location as finish point
     * @return the distance in meters.
     */
    Double defineDistanceInMeters(RouteLocation start, RouteLocation finish);

}
