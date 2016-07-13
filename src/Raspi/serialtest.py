import serial
import time
import subprocess
import os


n = 0
port = serial.Serial("/dev/ttyAMA0", baudrate=115200, timeout=0.5)

def graphRRD(startTime, endTime):
	subprocess.call(['rrdtool','graph','test.png',
			 '-w','1000','-h','400',
			 '--start',str(startTime),
			 '--end',endTime,
			 'DEF:temperature=test.rrd:temp:MAX',
			 'DEF:light=test.rrd:light:MAX',
			 'LINE1:temperature#ff0000:Temp',
			 'LINE2:light#00ff00:Light'])

def createRRD():
	subprocess.call(['rrdtool','create','test.rrd','--step','1',
			 'DS:temp:GAUGE:120:-50:50',
			 'DS:light:GAUGE:120:0:100',		
			 'RRA:MAX:0.5:1:1440'])
def addData(temperature, light,time):
	subprocess.call(['rrdtool','update','test.rrd',
			 str(time)+":"+str(temperature)+":"+str(light)])


createRRD()
count = 0
while True:
	
	x = port.readline().decode('ascii').strip()
	
	datas = x.split('$')


	try:
		if(float(datas[0].strip())>50):
			command = 'fswebcam '+str(n)+'.jpeg'
			os.system(command)
			n+=1
	except ValueError:
		print('The alarm has been triggered.')
	print(datas[0])
	if len(datas)>1:
		addData(datas[0],datas[1],time.time())
		
	if count = 0: graphRRD(-500,'now')
	count = (count + 1)%30
	time.sleep(1)
