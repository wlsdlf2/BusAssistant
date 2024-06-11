package _Team.bus_assistant_backend.repository;

import _Team.bus_assistant_backend.domain.Bus;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
public class MemoryBusRepository implements BusRepository {

    private final List<Bus> buses = Collections.synchronizedList(new ArrayList<>());

    @Override
    public void addBus(Bus bus) {
        buses.add(bus);
    }
}
