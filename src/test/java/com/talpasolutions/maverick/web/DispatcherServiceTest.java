package com.talpasolutions.maverick.web;

import com.talpasolutions.maverick.protocol.LocationMapper;
import com.talpasolutions.maverick.protocol.RouteResult;
import com.talpasolutions.maverick.protocol.SimpleLocation;
import com.talpasolutions.maverick.services.DispatcherService;
import com.talpasolutions.maverick.services.DistanceService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Order;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
public class DispatcherServiceTest extends GeneralConfigurationTest {

    private static final Double SPEED_METER_PRO_SEC = 16.6667; // 60 km/h = 16.6667 m/s

    @Autowired
    private DispatcherService dispatcherService;

    @Autowired
    private DistanceService earthDistanceService;

    @Order(0)
    @Test
    public void testBestRouteForTestData() {
        List<SimpleLocation> locationRoutes = Arrays.asList(givenTestDepot1(), givenTestDepot2(),
                givenTestStore1(), givenTestStore2(), givenTestCustomer1());
        List<RouteResult> testResult = dispatcherService.getMinimalRoutesForRegisteredCustomers(locationRoutes);
        Assert.assertNotNull(testResult);
        Assert.assertEquals(1, testResult.size()); // one customer in test data - one route
        Assert.assertEquals(Collections.singletonList(givenBestRouteResult()), testResult);
        Double distanceDepotStore = earthDistanceService.defineDistanceInMeters(
                LocationMapper.from(givenTestDepot1()), LocationMapper.from(givenTestStore1()));
        Assert.assertEquals(Double.valueOf(7045.686070946999), distanceDepotStore);
        Double distanceStoreCustomer = earthDistanceService.defineDistanceInMeters(
                LocationMapper.from(givenTestStore1()), LocationMapper.from(givenTestCustomer1()));
        Assert.assertEquals(Double.valueOf(4995.107389227975), distanceStoreCustomer);
        Double distanceDepotCustomer = distanceDepotStore + distanceStoreCustomer; //full distance must be a sum depot->store->customer
        Assert.assertEquals(distanceDepotCustomer, testResult.get(0).getDistance());
        Assert.assertEquals(Double.valueOf(distanceDepotCustomer/SPEED_METER_PRO_SEC), testResult.get(0).getDuration());
    }

}
