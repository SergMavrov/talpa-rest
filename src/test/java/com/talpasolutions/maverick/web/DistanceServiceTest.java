package com.talpasolutions.maverick.web;

import com.talpasolutions.maverick.protocol.LocationMapper;
import com.talpasolutions.maverick.services.DistanceService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Order;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class DistanceServiceTest extends GeneralConfigurationTest {

    @Autowired
    private DistanceService distanceService;

    @Order(0)
    @Test
    public void testDistanceBetweenTwoLocations() {
        Double distance = distanceService.defineDistanceInMeters(
                LocationMapper.from(givenStart()),LocationMapper.from(givenFinish()));
        Assert.assertEquals(Double.valueOf(7382.191545564012), distance);
    }

    @Order(1)
    @Test
    public void testDistanceBetweenTheSameLocations() {
        Double distance = distanceService.defineDistanceInMeters(
                LocationMapper.from(givenStart()),LocationMapper.from(givenStart()));
        Assert.assertEquals(Double.valueOf(0), distance);
    }
}
