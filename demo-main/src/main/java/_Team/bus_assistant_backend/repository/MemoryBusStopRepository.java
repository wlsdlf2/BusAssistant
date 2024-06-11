package _Team.bus_assistant_backend.repository;

import _Team.bus_assistant_backend.domain.BusStop;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
public class MemoryBusStopRepository implements BusStopRepository {

    private final List<BusStop> busStops = Collections.synchronizedList(new ArrayList<>());

    @Override
    public void addBusStop(BusStop busStop) {
        busStops.add(busStop);
        System.out.println(busStop.getBusNumber());
        System.out.println(busStop.getRouteID());
        System.out.println(busStop.getStationID());
    }

    // 특정 busNumber를 가진 모든 BusStop을 찾는 메서드
    public List<BusStop> findByBusNumber(String busNumber) {
        List<BusStop> result = new ArrayList<>();
        for (BusStop busStop : busStops) {
            if (busStop.getBusNumber().equals(busNumber)) {
                result.add(busStop);
            }
        }
        return result;
    }

    @Override
    public List<BusStop> getAllBusStops() {
        return new ArrayList<>(busStops);
    }
}
