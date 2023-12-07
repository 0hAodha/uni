% base fact: if the list is empty, the list to be returned is just the element
insertInOrder(Element, [], [Element]).

% if the element to be inserted is <= the head of the list, insert it at the head of the list
insertInOrder(Element, [Head | Tail], [Element, Head | Tail]) :- Element =< Head.

% if the element to be inserted is greater than the head of the list, recurse with the tail of the list until 
insertInOrder(Element, [Head | Tail], [Head | NewTail]) :- Element > Head, insertInOrder(Element, Tail, NewTail).
