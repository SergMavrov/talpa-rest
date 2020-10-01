package com.talpasolutions.maverick.protocol;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.TimeUnit;

@AllArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode
public class RouteResult {

    private SimpleLocation depot;
    private SimpleLocation store;
    private SimpleLocation customer;

    private Double duration;
    private Double distance;

    public String getMinSec() {
        long minutes = TimeUnit.SECONDS.toMinutes(duration.longValue());
        long restOfSeconds = duration.longValue() - minutes*60;
        long seconds = TimeUnit.SECONDS.toSeconds(restOfSeconds);
        return minutes + ":" + seconds;
    }

    @Override
    public String toString() {
        return "\nBEST ROUTE -----------------> " + "\n" +
                "1. DEPOT                  = " + depot + "\n" +
                "2. STORE                  = " + store + "\n" +
                "3. CUSTOMER               = " + customer + "\n" +
                "INFO: DURATION IN MIN:SEC = " + getMinSec()+ "\n" +
                "INFO: DISTANCE IN METERS  = " + distance + "\n" +
                "----------------------------";
    }
}
