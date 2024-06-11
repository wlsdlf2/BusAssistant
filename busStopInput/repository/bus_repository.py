class BusRepository:
    def __init__(self):
        self.bus_list = []

    def add_bus(self, bus_number):
        if bus_number not in self.bus_list:
            self.bus_list.append(bus_number)
            return True
        return False
    
    def remove_bus(self, bus_number):
        if bus_number in self.bus_list:
            self.bus_list.remove(bus_number)
            return True
        return False
    
    def get_buses(self):
        return self.bus_list
    
    def clear_buses(self):
        self.bus_list = []