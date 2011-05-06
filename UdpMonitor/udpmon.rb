require 'socket'
 s2 = UDPSocket.new
 s2.bind("127.0.0.1", 0)
 s2.connect(*s1.addr.values_at(3,1))
 IO.select([s2])
 p s2.recvfrom(10)  #=> ["aaa", ["AF_INET", 33302, "localhost.localdomain", "127.0.0.1"]]
 
