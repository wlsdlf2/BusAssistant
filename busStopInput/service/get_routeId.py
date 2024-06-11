import requests
import xml.etree.ElementTree as ET

def get_routeId(bus_number):
    api_url = 'my_api_key'
    api_key = "8IUN6Mgf0R1P09LBFA/lr+EvH1UxkcBuiXcQ+3NVnLfzMUYdgQg25u3Eezkw6ZEzsWodaiuHB6fiZWcTVCXXjw=="
    bus_number = bus_number
    
    params = {'serviceKey':api_key, 'keyword':bus_number}
    response = requests.get(url=api_url, params=params)

    if response.status_code == 200:
        # API 응답을 XML 형식으로 파싱
        root = ET.fromstring(response.content)
        
        for bus_route in root.findall('.//busRouteList'):
            region_name_element = bus_route.find('regionName')
            if region_name_element is not None:
                region_name = region_name_element.text
                
                if '화성' in region_name:
                    route_id = bus_route.find('routeId').text
                    return route_id 
