require 'socket'
 s2 = UDPSocket.new
 s2.bind("127.0.0.1", 10001)
 #IO.select([s2])
 
while (true) 

	p s2.recvfrom(10)
end 
