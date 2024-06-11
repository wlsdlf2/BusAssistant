package _Team.bus_assistant_backend.repository;

import _Team.bus_assistant_backend.domain.BusStop;

import java.util.List;

public interface BusStopRepository {
    void addBusStop(BusStop busStop);
    List<BusStop> getAllBusStops();
}

//extends JpaRepository<BusStop, Long>