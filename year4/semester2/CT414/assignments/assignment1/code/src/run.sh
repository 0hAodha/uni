#!/bin/sh

src_path="/home/andrew/edu/year4/semester2/CT414/assignments/assignment1/code/src"

# compile code
javac -d . server/*.java client/*.java interfaces/*.java implementations/*.java                                                                                                                                             

# start rmi registry
rmiregistry -J-Djava.rmi.server.codebase=file:$src_path &
trap "kill $! 2>/dev/null" EXIT

# start server
java -cp $src_path -Djava.rmi.server.codebase=file:$src_path/ server.ApplicationServer &
trap "kill $! 2>/dev/null" EXIT
