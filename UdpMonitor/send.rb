

require 'socket'
s1 = UDPSocket.new
s1.bind("127.0.0.1", 0)

s1.connect(*s2.addr.values_at(3,1))
s1.send "Hallo Welt", 0
