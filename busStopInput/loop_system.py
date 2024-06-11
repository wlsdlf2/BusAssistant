import time
from module.keypad import Keypad
from module.tts import TTS
from module.httpRequest import HttpRequest
from repository.bus_repository import BusRepository
from service.bus_arrival_time import BusArrivalTime
from service.exists_bus import ExistsBus
from service.get_routeId import get_routeId

class LoopSystem:

    def __init__(self, row_pins, col_pins, station_id, city_code):
        self.bus_number = ''
        self.running = False
        self.station_id = station_id
        self.city_code = city_code

        self.keypad = Keypad(row_pins, col_pins)
        self.tts = TTS()
        self.exists_bus = ExistsBus()
        self.arrival_time = BusArrivalTime()
        self.bus_list = BusRepository()
        self.http_request = HttpRequest()

    def keypad_callback(self):

        key = self.keypad.scan_key()

        if key is not None:
            if key == '#':
                
                # 버스 route_id 반환
                route_id = get_routeId(self.bus_number)
                print("route_id : " + route_id)
                print("station_id : " + self.station_id)
                # 버스 번호 존재 검사
                bus_existence = self.exists_bus.check_bus_existence(route_id, self.station_id)

                if bus_existence:
                    # "000번 버스를 등록하시겠습니까?"
                    text1 = self.bus_number + "번 버스를 등록하시겠습니까?"
                    self.tts.play_tts(text1)

                    check_key = self.keypad.scan_key()

                    # 여기에 사용자 확인 로직 추가 (예: 추가 버튼 클릭 대기)
                    if check_key == '#':
                        # 버스 등록
                        # 웹서버로 station_id, route_id, bus_number 데이터 전송
                        print("route_id : " + route_id)
                        print("station_id : " + self.station_id)
                        if self.http_request.send_data(self.station_id, route_id, self.bus_number):
                            # "000번 버스가 등록되었습니다."
                            text2 = self.bus_number + "번 버스가 등록되었습니다."
                            self.tts.play_tts(text2)
                            
                            self.bus_list.add_bus(self.bus_number)

                            # 버스 도착 예정 시간 조회
                            arrival_info = self.arrival_time.get_bus_arrival_time(self.station_id, route_id)
                            predict_time = arrival_info['predict_time']
                            # "000번 버스는 0분후 도착 예정입니다."
                            text3 = self.bus_number + "번 버스는 " + predict_time + "분 후 도착 예정입니다."
                            self.tts.play_tts(text3)


                        else:
                            # "이미 등록된 버스입니다. 다시 입력해주세요."
                            self.tts.play_tts_file("/home/lenovo/busInput/sound/dup.mp3")

                            self.bus_number = ''
                            
                        self.start()
                    else:
                        # 버스 번호 입력 초기화
                        # "등록이 취소되었습니다."
                        self.bus_number = ''
                        self.start()
                else:
                    # "존재하지 않는 버스입니다."
                    self.tts.play_tts_file("/home/lenovo/busInput/sound/noexist.mp3")
                    self.bus_number = ''
            elif key == 'c':
                self.bus_number = ''
            else:
                self.bus_number += str(key)
                
                text4 = key
                self.tts.play_tts(text4)

    def start(self):
        # "등록하실 버스의 번호를 입력해주세요."
        self.tts.play_tts_file("/home/lenovo/busInput/sound/intro.mp3")

        self.running = True
        try:
            while self.running:
                
                self.keypad_callback()
                time.sleep(0.3)  # 메인 루프에서 다른 작업을 수행할 수 있음
        except KeyboardInterrupt:
            print("Exiting...")
            self.stop()
        finally:
            self.keypad.cleanup()

    def stop(self):
        self.running = False
