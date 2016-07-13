import pyb
import time

class Alert():
	def __init__(self):
		self.sound = pyb.Pin('X5', pyb.Pin.OUT_PP)
		self.password = "*1234#"
		self.red = pyb.LED(1)
		self.yellow = pyb.LED(3)
		self.value = True
		self.timer = pyb.Timer(4)
		self.soundtimer = pyb.Timer(7)
		self.second = 0
	def tick(self):
		self.second = self.second + 1
	def returnValue(self):
		return self.value
	def enterPassword(self,a):
		self.timer.init(freq=2)
		self.soundtimer.init(freq=1)
		self.timer.counter(0)
		self.timer.callback(lambda t: self.yellow.toggle())
		self.soundtimer.callback(lambda t: self.tick())
		pw = str("")
		key = self.password
		while self.second < 11:
			char = a.keypad()
			pw = pw + str(char)
			if (char == '#'):
				break
		self.timer.deinit()
		self.soundtimer.deinit()
		self.yellow.off()
		self.second = 0
		if (pw == key):
			self.value = False
		else: 
			self.value = True
	def makesound(self):
		for i in range(0,33):
			self.sound.high()
			pyb.delay(5)
			self.sound.low()
			pyb.delay(10)
	def annoyingAlarm(self,a):
		self.timer.init(freq=2)
		self.soundtimer.init(freq=1)
		self.timer.callback(lambda t: self.red.toggle())
		self.soundtimer.callback(lambda t: self.makesound())
		pw = str("")
		key = self.password
		while self.value:
			char = a.keypad()
			pw = pw + str(char)
			if (char == '#'):
				if (pw == key):
					self.value = False
				else: 
					pw = ""
		self.timer.deinit()
		self.soundtimer.deinit()
		self.red.off()
	def trigger(self, a, code):
		self.value = True
		if code: self.enterPassword(a)
		if self.value: self.annoyingAlarm(a)