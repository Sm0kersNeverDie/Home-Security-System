import pyb
import math

class DeviceSensor():
	def __init__(self):
		self.i2c = pyb.I2C(1)
		self.i2c.init(pyb.I2C.MASTER,baudrate=10000)
		self.adc = pyb.ADC(pyb.Pin.board.X1)
		self.pin = pyb.Pin('Y12', pyb.Pin.IN, pyb.Pin.PULL_UP)
	def tmprt(self):		#Function returns temperature value
		temp = float(self.adc.read())
		vo = 3.3/4095*temp
		rt = (vo*1500)/(3.3-vo)
		v = 0.06*rt-95.61
		return v
	def light(self):		#Function returns light ambient value
		self.i2c.send(0x43,0x39)
		data = bytearray(1)
		self.i2c.recv(data,0x39)
		data0C = (data[0]&0x70)>>4
		data0S = (data[0]&0x0F)
		data0 = int(16.5*((2**data0C)-1))+data0S*(2**data0C)
		self.i2c.send(0x83,0x39)
		self.i2c.recv(data,0x39)
		data1C = (data[0]&0x70)>>4
		data1S = (data[0]&0x0F)
		data1 = int(16.5*((2**data1C)-1))+data1S*(2**data1C)
		data = data0*0.46*(math.e**(-3.13*data1/data0))
		return data
	def motion(self):		#Function returns motion value
		return self.pin.value()
	def destroy():
		self.i2c.deinit()
		
class Keypad():
	def __init__(self):
		self.r1 = pyb.Pin('Y3',pyb.Pin.IN, pyb.Pin.PULL_UP)
		self.r2 = pyb.Pin('Y8',pyb.Pin.IN, pyb.Pin.PULL_UP)
		self.r3 = pyb.Pin('Y7',pyb.Pin.IN, pyb.Pin.PULL_UP)
		self.r4 = pyb.Pin('Y5',pyb.Pin.IN, pyb.Pin.PULL_UP)
		self.c1 = pyb.Pin('Y4',pyb.Pin.IN, pyb.Pin.PULL_UP)
		self.c2 = pyb.Pin('X6',pyb.Pin.IN, pyb.Pin.PULL_UP)
		self.c3 = pyb.Pin('Y6',pyb.Pin.IN, pyb.Pin.PULL_UP)
	def isPressed(self,a):	#Loop function for keypad
		while (a.value() == 0):
			continue
	def keypad(self):		#Function to scan keypad
		out = ''
		self.c1.init(pyb.Pin.OUT_PP, pyb.Pin.PULL_NONE)
		self.c1.low()
		if (self.r1.value() == 0):
			out = '1'
			self.isPressed(self.r1)
		elif (self.r2.value() == 0):
			out = '4'
			self.isPressed(self.r2)
		elif (self.r3.value() == 0):
			out = '7'
			self.isPressed(self.r3)
		elif (self.r4.value() == 0):
			out = '*'
			self.isPressed(self.r4)
		else:
			self.c1.init(pyb.Pin.IN, pyb.Pin.PULL_UP)
			self.c2.init(pyb.Pin.OUT_PP, pyb.Pin.PULL_NONE)
			self.c2.low()
			if (self.r1.value() == 0):
				out = '2'
				self.isPressed(self.r1)
			elif (self.r2.value() == 0):
				out = '5'
				self.isPressed(self.r2)
			elif (self.r3.value() == 0):
				out = '8'
				self.isPressed(self.r3)
			elif (self.r4.value() == 0):
				out = '0'
				self.isPressed(self.r4)
			else:
				self.c2.init(pyb.Pin.IN, pyb.Pin.PULL_UP)
				self.c3.init(pyb.Pin.OUT_PP, pyb.Pin.PULL_NONE)
				self.c3.low()
				if (self.r1.value() == 0):
					out = '3'
					self.isPressed(self.r1)
				elif (self.r2.value() == 0):
					out = '6'
					self.isPressed(self.r2)
				elif (self.r3.value() == 0):
					out = '9'
					self.isPressed(self.r3)
				elif (self.r4.value() == 0):
					out = '#'
					self.isPressed(self.r4)
		self.c1.init(pyb.Pin.IN, pyb.Pin.PULL_UP)
		self.c2.init(pyb.Pin.IN, pyb.Pin.PULL_UP)
		self.c3.init(pyb.Pin.IN, pyb.Pin.PULL_UP)
		return out