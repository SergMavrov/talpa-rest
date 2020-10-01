package com.talpasolutions.maverick.web;

import com.talpasolutions.maverick.protocol.RouteResult;
import com.talpasolutions.maverick.protocol.SimpleLocation;
import com.talpasolutions.maverick.services.DispatcherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class DronController {

    private final DispatcherService dispatcherService;

    @Autowired
    public DronController(DispatcherService dispatcherService) {
        this.dispatcherService = dispatcherService;
    }

    @PostMapping(path = "/routes", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RouteResult>> calculateRoutes(@RequestBody List<SimpleLocation> locations) {
        try {
            return ResponseEntity.ok(dispatcherService.getMinimalRoutesForRegisteredCustomers(locations));
        } catch (IllegalStateException ex) {
            log.error("Something is going wrong", ex);
            return ResponseEntity.badRequest().build();
        }
    }

}
