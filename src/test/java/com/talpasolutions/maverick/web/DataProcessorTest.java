package com.talpasolutions.maverick.web;

import com.talpasolutions.maverick.protocol.LocationMapper;
import com.talpasolutions.maverick.protocol.SimpleLocation;
import com.talpasolutions.maverick.services.DataProcessor;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Order;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RunWith(SpringRunner.class)
public class DataProcessorTest extends GeneralConfigurationTest{

    @Autowired
    private DataProcessor dataProcessor;

    @Order(0)
    @Test
    public void testGetDepots() {
        List<SimpleLocation> locationRoutes =
                Arrays.asList(LocationMapper.from(givenTestDepot1()), givenTestDepot2(), givenTestStore1(),
                givenTestStore2(), givenTestCustomer1());
        dataProcessor.prepare(locationRoutes);
        Assert.assertEquals(Arrays.asList(LocationMapper.from(givenTestDepot1()),
                LocationMapper.from(givenTestDepot2())), dataProcessor.readDepots());
    }

    @Order(1)
    @Test
    public void testGetStores() {
        List<SimpleLocation> locationRoutes = Arrays.asList(givenTestDepot1(), givenTestDepot2(), givenTestStore1(),
                givenTestStore2(), givenTestCustomer1());
        dataProcessor.prepare(locationRoutes);
        Assert.assertEquals(Arrays.asList(LocationMapper.from(givenTestStore1()),LocationMapper.from(givenTestStore2())),
                dataProcessor.readStores());
    }

    @Order(2)
    @Test
    public void testGetCustomers() {
        List<SimpleLocation> locationRoutes = Arrays.asList(givenTestDepot1(), givenTestDepot2(), givenTestStore1(),
                givenTestStore2(), givenTestCustomer1());
        dataProcessor.prepare(locationRoutes);
        Assert.assertEquals(Collections.singletonList(LocationMapper.from(givenTestCustomer1())),
                dataProcessor.readCustomers());
    }

}
