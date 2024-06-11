import RPi.GPIO as GPIO
import time

class Keypad:
    def __init__(self, row_pins, col_pins):
        GPIO.setwarnings(False)

        self.row_pins = row_pins
        self.col_pins = col_pins
        self.keypad_layout = [
            ['1', '2', '3', '-'],
            ['4', '5', '6', 'A'],
            ['7', '8', '9', 'H'],
            ['*', '0', '#', 'c']
        ]

        GPIO.setmode(GPIO.BCM)

        # 행을 출력으로 설정
        # 열을 입력으로 설정
        for i in range(4):
            GPIO.setup(self.row_pins[i], GPIO.OUT)
            GPIO.setup(self.col_pins[i], GPIO.IN, pull_up_down=GPIO.PUD_UP)

        for i in range(4):
            GPIO.output(self.row_pins[i], GPIO.LOW)

    # 각 행의 핀을 설정하는 함수
    def set_row(self, row):
        for i in range(4):
            GPIO.output(self.row_pins[i], GPIO.LOW if row == i else GPIO.HIGH)
    
    # 키패드 입력 스캔 함수
    def scan_key(self):
        while True:
            for row in range(4):
                self.set_row(row)
                for col in range(4):
                    if GPIO.input(self.col_pins[col]) == GPIO.LOW:
                        print(self.keypad_layout[row][col])
                        time.sleep(0.05)  # 디바운싱을 위한 딜레이


                        return self.keypad_layout[row][col]

    def cleanup(self):
        GPIO.cleanup()