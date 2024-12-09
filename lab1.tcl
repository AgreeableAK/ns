# Create a new simulator instance
set ns [new Simulator]

# Open a NAM trace file in write mode
set nf [open lab1.nam w]
$ns namtrace-all $nf

# Open a trace file in write mode
set tf [open lab1.tr w]
$ns trace-all $tf

# Define the finish procedure
proc finish {} {
    global ns nf tf
    $ns flush-trace
    close $nf
    close $tf
    exec nam lab1.nam &
    exit 0
}

# Create 4 nodes
set n0 [$ns node]
set n1 [$ns node]
set n2 [$ns node]
set n3 [$ns node]

# Create duplex links between nodes with specified bandwidth, delay, and queue type
$ns duplex-link $n0 $n2 200Mb 10ms DropTail
$ns duplex-link $n1 $n2 100Mb 5ms DropTail
$ns duplex-link $n2 $n3 1Mb 1000ms DropTail

# Set queue limits for the links
$ns queue-limit $n0 $n2 5
$ns queue-limit $n1 $n2 5

# Attach UDP agents to nodes
set udp0 [new Agent/UDP]
$ns attach-agent $n0 $udp0

set cbr0 [new Application/Traffic/CBR]
$cbr0 set packetSize_ 1000
$cbr0 set interval 0.005
$cbr0 attach-agent $udp0

set udp1 [new Agent/UDP]
$ns attach-agent $n1 $udp1

set cbr1 [new Application/Traffic/CBR]
$cbr1 attach-agent $udp1

set udp2 [new Agent/UDP]
$ns attach-agent $n2 $udp2

set cbr2 [new Application/Traffic/CBR]
$cbr2 attach-agent $udp2

# Attach a Null agent to node n3
set null0 [new Agent/Null]
$ns attach-agent $n3 $null0

# Connect agents to the Null agent
$ns connect $udp0 $null0
$ns connect $udp1 $null0

# Schedule events
$ns at 0.1 "$cbr0 start"
$ns at 0.2 "$cbr1 start"
$ns at 1.0 "finish"

# Run the simulation
$ns run




