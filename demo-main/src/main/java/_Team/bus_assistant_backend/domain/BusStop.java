package _Team.bus_assistant_backend.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BusStop {

    private String stationID; // 정류소 아이디를 나타내는 문자열
    private String routeID; // 노선 아이디를 나타내는 문자열
    private String busNumber;

}

