package _Team.bus_assistant_backend.service;

import _Team.bus_assistant_backend.domain.Bus;
import _Team.bus_assistant_backend.domain.BusStop;
import _Team.bus_assistant_backend.repository.MemoryBusRepository;
import _Team.bus_assistant_backend.repository.MemoryBusStopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

// BusService.java
@Service
@RequiredArgsConstructor
public class BusService {

    private final MemoryBusStopRepository memoryBusStopRepository;
    private final MemoryBusRepository memoryBusRepository;

    public void addBusStop(BusStop busStop) {
        memoryBusStopRepository.addBusStop(busStop);
    } // 추가

    public void addBus(Bus bus) {
        memoryBusRepository.addBus(bus);
    }

    public List<BusStop> getBusStopByBusNumber(String busNumber) {
        return memoryBusStopRepository.findByBusNumber(busNumber);
    }

    public List<BusStop> getAllBusStops() {
        return memoryBusStopRepository.getAllBusStops();
    } // 모든 정보 내뱉기
}

