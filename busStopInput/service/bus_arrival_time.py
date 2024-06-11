import requests
import xml.etree.ElementTree as ET

class BusArrivalTime:
    def __init__(self):
        self.api_key = 'my_api_key'
        # 버스도착정보 항목조회
        self.api_url = 'http://apis.data.go.kr/6410000/busarrivalservice/getBusArrivalList'

    def get_bus_arrival_time(self, station_id, route_id):
        self.params = {'serviceKey': self.api_key, 'stationId': station_id, 'routeId': route_id}
        response = requests.get(self.api_url, params=self.params)

        if response.status_code == 200 and 'application/xml' in response.headers.get('Content-Type', ''):
            try:
                root = ET.fromstring(response.text)
                result_code = root.findtext('.//resultCode')
                if result_code == '0':
                    arrival_info = self.parse_arrival_data(root)
                    return arrival_info
                else:
                    result_message = root.findtext('.//resultMessage')
                    print(f"API 오류 메시지: {result_message}")
            except ET.ParseError as e:
                print(f"XML 파싱 오류: {e}")
        else:
            print(f"오류: XML 응답이 아니거나 상태 코드가 올바르지 않습니다.")
        return None
    
    def parse_arrival_data(self, root):
        # 차량 도착 정보 파싱
        arrival_info = {
            'location': root.findtext('.//locationNo1'),
            'predict_time': root.findtext('.//predictTime1'),
            'plate_no': root.findtext('.//plateNo1'),
            'remain_seat_cnt': root.findtext('.//remainSeatCnt1'),
            'low_plate': root.findtext('.//lowPlate1')
        }
        return arrival_info