- To get network interface information use: `ip address`. 
- To view the ARP cache: `ip neigh`. 
- To delete the ARP cache: `ip -s neigh flush all`. 
- To run a ping with a large payload and not allow IP fragmentation: `ping -M do -s 1600 www.rte.ie`. 
- To display the routing table on your device: `ip route`. 
- To run a traceroute without DNS name lookoup: `traceroute -n www.rte.ie`. 