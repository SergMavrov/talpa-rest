package com.talpasolutions.maverick.web;

import com.talpasolutions.maverick.protocol.LocationType;
import com.talpasolutions.maverick.protocol.RouteResult;
import com.talpasolutions.maverick.protocol.SimpleLocation;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith({SpringExtension.class})
@SpringBootTest
@ActiveProfiles("test")
public abstract class GeneralConfigurationTest {


    // service methods
    /*
    Depo2|Ludenberger Str. 1, 40629 Düsseldorf|51.2412426|6.828366|DEPOT
    Store1|Willstätterstraße 24, 40549 Düsseldorf|51.238400|6.722420|STORE
     */
    protected SimpleLocation givenStart() {
        return SimpleLocation.builder()
                .address("Ludenberger Str. 1, 40629 Düsseldorf")
                .name("Depo2")
                .latitude(51.2412426)
                .longitude(6.828366)
                .type(LocationType.DEPOT)
                .build();
    }

    protected SimpleLocation givenFinish() {
        return SimpleLocation.builder()
                .address("Willstätterstraße 24, 40549 Düsseldorf")
                .name("Store1")
                .latitude(51.238400)
                .longitude(6.722420)
                .type(LocationType.STORE)
                .build();
    }
    /*
Depot1|Depot1Address|51.2346752|6.8234472|DEPOT
Depot2|Depot2Address|60.2412426|10.828366|DEPOT
Store1|Store1Address|51.238400|6.722420|STORE
Store2|Store2Address|61.210770|11.774410|STORE
Customer1|Customer1Address|51.208450|6.775880|CUSTOMER
     */

    protected RouteResult givenBestRouteResult() {
        return RouteResult.builder()
                .customer(givenTestCustomer1())
                .store(givenTestStore1())
                .depot(givenTestDepot1())
                .distance(12040.793460174973)
                .duration(722.446162718173)
                .build();
    }

    protected SimpleLocation givenTestCustomer1() {
        return SimpleLocation.builder()
                .address("Customer1Address")
                .name("Customer1")
                .latitude(51.208450)
                .longitude(6.775880)
                .type(LocationType.CUSTOMER)
                .build();
    }

    protected SimpleLocation givenTestStore1() {
        return SimpleLocation.builder()
                .address("Store1Address")
                .name("Store1")
                .latitude(51.238400)
                .longitude(6.722420)
                .type(LocationType.STORE)
                .build();
    }

    protected SimpleLocation givenTestStore2() {
        return SimpleLocation.builder()
                .address("Store2Address")
                .name("Store2")
                .latitude(61.210770)
                .longitude(11.774410)
                .type(LocationType.STORE)
                .build();
    }

    protected SimpleLocation givenTestDepot1() {
        return SimpleLocation.builder()
                .address("Depot1Address")
                .name("Depot1")
                .latitude(51.2346752)
                .longitude(6.8234472)
                .type(LocationType.DEPOT)
                .build();
    }

    protected SimpleLocation givenTestDepot2() {
        return SimpleLocation.builder()
                .address("Depot2Address")
                .name("Depot2")
                .latitude(60.2412426)
                .longitude(10.828366)
                .type(LocationType.DEPOT)
                .build();
    }


}
