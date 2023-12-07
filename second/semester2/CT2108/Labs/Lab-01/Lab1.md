#1 hex vals of source & destination MA addresses in ethernet frame containing arp request messages
Sender MAC address: IntelCor_4b:81:47 (20:1e:88:4b:81:47)
Sender MAC address: IntelCor_4b:81:47 (20:1e:88:4b:81:47) 

# 2hex val two byte ether frane type in arp request messages
packet type: sent by us (4) 
Port numbers are the way the TRANSPORT layer recognizes which packet belongs to what process at the end systems.
They're used to let the process-to-process delivery work; but ICMP, from a functional point of view, is not a transport layer protocol.

ICMP is a messaging protocol at the Network layer(on top of the IP; but not really in the transport layer), it's got a lot of responsibilities but none of them has anything to do with process-to-process delivery, so having a port number there wouldn't make any sense.

# 3ip addr of host and ip addr of dest host for ping. why icmp not have source n destination 
Source Address: 140.203.135.95
Destination Address: 104.18.143.17

#4 icmp type n code nums of ping reqs sent by host. what other fields does this icmp pack have. no bytes checkusum seq num identify fields

