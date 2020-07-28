# IP-Fragmentation-Calculator
A calculator that displays the number of fragments needed for any given IP Data Packet size and MTU. It also calculates the values of Total Length, MF Flag, Offset.

## How Fragmentation Works
When a host sends an IP packet onto the network it cannot be larger than the maximum size supported by that local network. This size is determined by the network’s data link and IP Maximum Transmission Units (MTUs) which are usually the same. A typical contemporary office, campus or data centre network provided over Ethernet will have 1500 byte MTUs.
However, packets that are initially transmitted over a network supporting one MTU may need be routed across networks (such as a WAN or VPN tunnel) with a smaller MTU. In these cases, if the packet size exceeds the lower MTU the data in the packet must be fragmented (if possible). This means it is broken into pieces carried within new packets (fragments) that are equal to or smaller than the lower MTU. This is called Fragmentation and the data in these fragments is then typically reassembled when they reach their destination.

### Pros and Cons of Fragmentation
Fragmentation allows for:
Transport layer protocols to be ignorant of the underlying network architecture, reducing overheads.
IP And higher layer protocols to work across variable and diverse network paths and mediums without the need and overhead of a path discovery protocol (but see the PMTUD section).
Fragmentation has a number of drawbacks which result in it’s use being avoided where possible, primarily:
The loss of a single fragment results in all the fragments having to be resent where a reliable transport layer protocol such as TCP is in use (in fact the sender resends one packet and fragmentation occurs once again).
Only the first fragment contains the high layer headers which can cause issues with firewalls, middle-boxes and routers (i.e. NAT functionality) that rely on inspecting those headers.
Fragmentation may result in out of order packet delivery and the need for reordering (especially if only some packets are fragmented or if link aggregation or other path splitting technologies are in use).

## IPv4 Header Fields Used
Fragmentation’s operation relies upon three IP header fields (32 bits in total), all of which will have very different values in the fragments compared to the original packet:

The Identification field (16 bits) is populated with an ID number unique for the combination of source & destination addresses and Protocol field value of the original packet, allowing the destination to distinguish between the fragments of different packets (from the same source). This does not mean the same ID should be used when fragmenting packets where the source, destination and protocol are the same but that the same ID could be used when they are not. To make this very clear, if three packets are sent from host A to host B and each must be fragmented into four fragments:
the four fragments of the first packet will share the same Identification field value
the four fragments of the second packet will share the same Identification field value, which will be different to the value used with the fragments created from the first packet
the four fragments of the third packet will share the same Identification field value, which will be different to the value used with the fragments created from the first and second packets
Like the original packet, the first, reserved bit of the Flags field (3 bits) will be 0 (unset) and the second bit, Don’t Fragment (DF), will also be unset. Unlike the original packet, all but the last fragment will have the third bit of the field, More Fragments (MF), set to 1. The last packet will have all bits in this field set to 0 just like the original packet (unless it was a fragment itself). If the Don’t Fragment flag was set in the original packet, this prevents fragmentation and results in packets that require it being discarded. An ICMP error of type 3: ‘Destination Unreachable’, code 4: ‘Fragmentation required, and DF set’ should be sent to the sender. See the following PMTUD section for more on this.
The Fragment Offset field (13 bits) is used to indicate the starting position of the data in the fragment in relation to the start of the data in the original packet. This information is used to reassemble the data from all the fragments (whether they arrive in order or not). In the first fragment the offset is 0 as the data in this packet starts in the same place as the data in the original packet (at the beginning). In subsequent fragments, the value is the offset of the data the fragment contains from the beginning of the data in the first fragment (offset 0), in 8 byte ‘blocks’ (aka octawords). If a packet containing 800 bytes of data is split into two equal fragments carrying 400 bytes of data, the fragment offset of the first fragment is 0, of the second fragment 50 (400/8). The offset value must be the number of 8 byte blocks of data, which means the data in the prior fragment must be a multiple of 8 bytes. The last fragment can carry data that isn’t a multiple of 8 bytes as there won’t be a further fragment with an offset that must meet the 8 byte ‘rule’.
These header field values also change either because they normally would at every hop, or as a by-product of fragmentation:

The Total Length field (16 bits) changes based on the reduced size of the data in a fragment (plus IP header) which equals or is smaller than the MTU. Because the Fragment Offset field in the following fragments must be a multiple of 8 the fragment’s size isn’t always as large as the MTU allows. Note in IPv4 this field indicates the total packet size including the header.
The Header Checksum field (16 bits) is recalculated based on the value of all of the other fields in the header
The Time To Live field (8 bits) value is reduced by one

## Running the Calculator
```
javac IPFragmentationCalculator.java
java IPFragmentationCalculator.java
```
You will be asked to insert the Data Packet size and MTU of your choice.The program will display the number of fragments,values of total length,MF flags and offset.
