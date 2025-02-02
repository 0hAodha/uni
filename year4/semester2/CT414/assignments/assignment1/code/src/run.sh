#!/bin/bash

src_path="/home/andrew/edu/year4/semester2/CT414/assignments/assignment1/code/src"

# compile code
javac -d . server/*.java client/*.java interfaces/*.java implementations/*.java

# start rmi registry (in a new terminal)
$TERM -e rmiregistry -J-Djava.rmi.server.codebase=file:$src_path &
sleep 1

# start server (in a new terminal)
$TERM -e java -cp $src_path -Djava.rmi.server.codebase=file:$src_path/ server.ApplicationServer &
sleep 1

# start client (in a new terminal)
$TERM -e bash -c "java -cp $src_path -Djava.rmi.server.codebase=file:$src_path/ client.ApplicationClient ; zsh"
