package com.talpasolutions.maverick.protocol;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class MapRoutes {

    private Set<RouteLocation> routes;

    public Set<RouteLocation> getRoutes() {
        if (routes == null) {
            routes = new HashSet<>();
        }
        return routes;
    }

    public void addRoute(RouteLocation route) {
        getRoutes().add(route);
    }

    public void cleanRoutes() {
        routes = new HashSet<>();
    }
}
