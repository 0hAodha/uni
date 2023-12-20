% call the helper predicate with the list to be reversed and an empty Accumulator to build up
reverseList(List, Reversed) :- reverseListHelper(List, [], Reversed).

% base case fact: when the list to reverse is empty, the accumulator is the reversed list
reverseListHelper([], Accumulator, Accumulator).

% recurse with the tail after prepending the head to the accumulator
reverseListHelper([Head | Tail], Accumulator, Reversed) :- reverseListHelper(Tail, [Head | Accumulator], Reversed).
