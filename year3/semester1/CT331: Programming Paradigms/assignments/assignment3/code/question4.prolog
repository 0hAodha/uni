% predicate to merge two lists
% base case: if the first list is empty, just return the second
mergeTwoLists([], List, List).

% recursive predicate to merge two lists
% split the first list into head and tail, and recurse with its tail and the second list until the first list is empty (base case)
% then merge the original head of the first list with the resulting tail
mergeTwoLists([Head | Tail], List2, [Head | ResultTail]) :- mergeTwoLists(Tail, List2, ResultTail).

% predicate to merge 3 lists 
% base case: merging an empty list and two others is the same as merging two lists
mergeLists([], List2, List3, Merged) :- mergeTwoLists(List2, List3, Merged).

% split the first list into head and tail, and recurse with its tail and the other two lists until the first list is empty (base case)
mergeLists([Head1 | Tail1], List2, List3, [Head1 | MergedTail]) :- mergeLists(Tail1, List2, List3, MergedTail).

