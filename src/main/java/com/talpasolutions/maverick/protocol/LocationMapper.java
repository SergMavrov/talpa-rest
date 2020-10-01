package com.talpasolutions.maverick.protocol;

public final class LocationMapper {

    private LocationMapper() {
    }

    public static RouteLocation from(SimpleLocation location) {
        return RouteLocation.builder()
                .name(location.getName())
                .address(location.getAddress())
                .latitude(location.getLatitude())
                .longitude(location.getLongitude())
                .type(location.getType())
                .build();
    }

    public static SimpleLocation from(RouteLocation location) {
        return SimpleLocation.builder()
                .name(location.getName())
                .address(location.getAddress())
                .latitude(location.getLatitude())
                .longitude(location.getLongitude())
                .type(location.getType())
                .build();
    }

}
