from loop_system import LoopSystem

if __name__ == "__main__":
    row_pins = [17, 27, 22, 5] # GPIO 17, 27, 22, 5 핀
    col_pins = [6, 13, 19, 26] # GPIO 6, 13, 19, 26 핀

    station_id = '233000945' # 와우지구(와우농협앞 방면)
    city_code = '31240' # 화성시

    loop = LoopSystem(row_pins, col_pins, station_id, city_code)
    loop.start()