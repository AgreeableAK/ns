# Create a new simulator instance
set ns [new Simulator]

# Open files for trace and NAM output
set tf [open lab3.tr w]
$ns trace-all $tf
set nf [open lab3.nam w]
$ns namtrace-all $nf

# Create and configure nodes
set n0 [$ns node]
$n0 color "magenta"
$n0 label "src1"

set n1 [$ns node]

set n2 [$ns node]
$n2 color "magenta"
$n2 label "src2"

set n3 [$ns node]
$n3 color "blue"
$n3 label "dest2"

set n4 [$ns node]

set n5 [$ns node]
$n5 color "blue"
$n5 label "dest1"

# Set up LAN router with parameters
LanRouter set debug_ 0
$ns make-lan "$n0 $n1 $n2 $n3 $n4" 100Mb 100ms LL Queue/DropTail Mac/802_3

# Set up duplex link between nodes n4 and n5
$ns duplex-link $n4 $n5 1Mb 1ms DropTail

# Create TCP agent and attach to source node n0
set tcp0 [new Agent/TCP]
$ns attach-agent $n0 $tcp0

# Set up FTP application and configure its parameters
set ftp0 [new Application/FTP]
$ftp0 attach-agent $tcp0
$ftp0 set packetSize_ 500
$ftp0 set interval_ 0.0001

# Create TCP Sink and connect to destination node n5
set sink5 [new Agent/TCPSink]
$ns attach-agent $n5 $sink5
$ns connect $tcp0 $sink5

# Create second TCP agent and attach to source node n2
set tcp2 [new Agent/TCP]
$ns attach-agent $n2 $tcp2

# Set up second FTP application and configure its parameters
set ftp2 [new Application/FTP]
$ftp2 attach-agent $tcp2
$ftp2 set packetSize_ 600
$ftp2 set interval_ 0.001

# Create second TCP Sink and connect to destination node n3
set sink3 [new Agent/TCPSink]
$ns attach-agent $n3 $sink3
$ns connect $tcp2 $sink3

# Open trace files for TCP agents
set file1 [open file1.tr w]
$tcp0 attach $file1
set file2 [open file2.tr w]
$tcp2 attach $file2

# Trace congestion window for both TCP agents
$tcp0 trace cwnd_
$tcp2 trace cwnd_

# Define finish procedure to flush and close trace files
proc finish { } {
    global ns nf tf
    $ns flush-trace
    close $tf
    close $nf
    exec nam lab3.nam &
    exit 0
}

# Schedule FTP application start and stop events for both sources
$ns at 0.1 "$ftp0 start"
$ns at 5 "$ftp0 stop"
$ns at 7 "$ftp0 start"
$ns at 0.2 "$ftp2 start"
$ns at 8 "$ftp2 stop"
$ns at 14 "$ftp0 stop"
$ns at 10 "$ftp2 start"
$ns at 15 "$ftp2 stop"

# Schedule finish event to end the simulation
$ns at 16 "finish"

# Run the simulation
$ns run

