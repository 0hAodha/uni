% base case: any element is not in an empty list
isNotElementInList(_, []).

% return true if Element is not the Head of the list and it's not found recursively searching the rest of the list
isNotElementInList(Element, [Head | Tail]) :- Element \= Head, isNotElementInList(Element, Tail).
