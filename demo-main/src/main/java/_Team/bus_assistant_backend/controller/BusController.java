package _Team.bus_assistant_backend.controller;

import _Team.bus_assistant_backend.domain.Bus;
import _Team.bus_assistant_backend.domain.BusStop;
import _Team.bus_assistant_backend.service.ApiService;
import _Team.bus_assistant_backend.service.BusService;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bus")
public class BusController {

    private final BusService busService;
    private final ApiService apiService;
    private boolean resultSent = false; // 결과를 이미 보냈는지 추적하는 플래그
    private final String api_url = "http://apis.data.go.kr/6410000/busarrivalservice/getBusArrivalItem";
    private final String api_key = "api_key"; // API 키

    private String loggedInBusNumber; // 로그인된 버스 번호

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam HashMap<String, Object> inputBusNumber) {
        System.out.println("Post 요청");

        // 앱으로부터 받은 버스 번호를 저장

        loggedInBusNumber = (String) inputBusNumber.get("busNumber");
        System.out.println(loggedInBusNumber);

        Bus bus = new Bus(loggedInBusNumber);

        busService.addBus(bus);
        return ResponseEntity.ok("Login successful. Bus number: " + loggedInBusNumber);
    }

    @GetMapping("/busNumber")
    public ResponseEntity<String> checkLocationNo1() {

        System.out.println("Get 요청");

        try {

            List<BusStop> busStops = busService.getBusStopByBusNumber(loggedInBusNumber);
            if (busStops.isEmpty()) {
                System.out.println("busStops is empty");
                return ResponseEntity.ok("No bus stops found for the logged-in bus number.");
            }

            BusStop requestedBusStop = busStops.get(0);

            String stationId = requestedBusStop.getStationID();
            String routeId = requestedBusStop.getRouteID();
            String busNumber = requestedBusStop.getBusNumber();

            String xmlResponse = apiService.call_api(stationId, routeId);
            String locationNo1 = apiService.getLocationNo1(xmlResponse);

            boolean busNumbersMatch = loggedInBusNumber != null && loggedInBusNumber.equals(busNumber);

            JSONObject jsonResponse = new JSONObject();

            if ("2".equals(locationNo1) && busNumbersMatch) {

                if (!resultSent) {
                    resultSent = true;
                    jsonResponse.put("alarm", 1);
                    jsonResponse.put("busStopName", "와우지구");
                    return ResponseEntity.ok(jsonResponse.toString());
                } else {
                    jsonResponse.put("alarm", 0);
                    return ResponseEntity.ok(jsonResponse.toString());
                }
            } else {
                jsonResponse.put("alarm", 0);
                return ResponseEntity.ok(jsonResponse.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }
}
