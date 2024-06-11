import requests
import json

class HttpRequest:
    def __init__(self):
        self.url = "http://192.168.0.15:8080/busStopData"
        self.headers = {'Content-Type': 'application/json'}

    def send_data(self, station_id, route_id, bus_number):
        
        print(station_id)
        print(route_id)
        print(bus_number)
        
        data = {
            "stationId" : station_id,
            "routeId" : route_id,
            "busNumber" : bus_number
        }
        
        print(data['stationId'])
        print(data['routeId'])
        print(data['busNumber'])
        
        try:
            response = requests.post(self.url, data = json.dumps(data), headers = self.headers)
            if response.status_code == 200:
                print(data)
                print("True")
                return True
            
            print(response.text)
        
        except Exception as e:
            print('Error:', e)
