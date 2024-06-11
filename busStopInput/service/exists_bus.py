import requests
import xml.etree.ElementTree as ET

class ExistsBus:

    def __init__(self):
        self.api_key = 'my_api_key'
        # 경유 정류소 목록 조회
        self.api_url = 'http://apis.data.go.kr/6410000/busrouteservice/getBusRouteStationList'
        

    def check_bus_existence(self, route_id, station_id):
        routeId = str(route_id)
        params ={'serviceKey' : self.api_key, 'routeId' : routeId }

        response = requests.get(self.api_url, params=params)

        if response.status_code == 200:
            # API 응답을 XML 형식으로 파싱
            root = ET.fromstring(response.content)
            
            # 모든 stationId 요소를 찾고 비교
            for station_list in root.findall('.//busRouteStationList'):
                station_id_element = station_list.find('stationId')
                if station_id_element is not None:
                    station_id_text = station_id_element.text
                    
                    if station_id_text == station_id:
                        return True
            
            return False
        
        else:
            print(f"API request failed with status code {response.status_code}")
            return False
