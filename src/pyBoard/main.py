 # main.py -- put your code here!
import pyb
from pyb import UART
import math
import sensors, alarm
import os
import micropython
micropython.alloc_emergency_exception_buf(100)

mainSensor =  sensors.DeviceSensor()
keyInput = sensors.Keypad()
alert = alarm.Alert()
count = 0
red = pyb.LED(1)
green = pyb.LED(3)
	#UART
uart = UART(6,115200)
uart.init(115200, bits = 8, parity = None, stop = 1)
degree0 = 0
lux0 = 0
while True:
	degree1 = mainSensor.tmprt()
	lux1 = mainSensor.light()
	uart.write(str(abs(lux1-lux0))+"$"+str(degree1))
	if count > 0:
		if abs(degree1-degree0) > 1.5:
			alert.trigger(keyInput,False)
			degree1 = mainSensor.tmprt()
		elif abs(lux1-lux0) > 50:
			alert.trigger(keyInput,True)
			lux1 = mainSensor.light()
		elif mainSensor.motion() == 0:
			alert.trigger(keyInput,True)
	else: count = 1
	degree0 = degree1
	lux0 = lux1
	pyb.delay(1000)
	
i2c.deinit()