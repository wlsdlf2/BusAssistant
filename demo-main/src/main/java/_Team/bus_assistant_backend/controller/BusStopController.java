package _Team.bus_assistant_backend.controller;

import _Team.bus_assistant_backend.domain.BusStop;
import _Team.bus_assistant_backend.service.BusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequiredArgsConstructor
@RestController
public class BusStopController {

    private final BusService busService;

    @PostMapping("/busStopData")
    public ResponseEntity<String> handleSensorData(@RequestBody Map<String, Object> json) {
        System.out.println("rasberry");

        String stationId = (String) json.get("stationId");
        String routeId = (String) json.get("routeId");
        String busNumber = (String) json.get("busNumber");

        BusStop busStop = new BusStop(stationId, routeId, busNumber);

        busService.addBusStop(busStop);

        System.out.println(json);

        return ResponseEntity.ok("Received bus stop data: " + json);
    }

}
