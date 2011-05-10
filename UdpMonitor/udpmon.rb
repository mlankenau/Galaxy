require 'socket'
 s2 = UDPSocket.new
 s2.bind("127.0.0.1", 10001)
 #IO.select([s2])
 
while (true) 
	msg = s2.recvfrom(10000)
	p  msg[0]
end 
