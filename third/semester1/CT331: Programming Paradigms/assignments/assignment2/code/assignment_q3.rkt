#lang racket

;; function to display the contents of a binary search tree in sorted order
(define (display_contents bst)
    (cond
        ;; if the binary search tree is null, print an empty string (nothing)
        [(null? bst) (display "")]

        ;; if the binary search tree has nodes
        [else 
            ;; display the contents of the left sub-tree of the current node
            (display_contents (cadr bst))

            ;; display the current node
            (display (car bst))
            (newline)

            ;; display the contents of the right sub-tree of the current node
            (display_contents (caddr bst))
        ]
    )
)

;; function to search a tree and tell whether a given item is presesnt in a given tree
(define (search_tree item bst)
    (cond
        ;; return false if we've reached the end of the tree without finding a match
        ((null? bst) #f)

        ;; return true if the current node is equal to the item
        ((equal? item (car bst)) #t)

        ;; else return whether the item was found in the left sub-tree or the right sub-tree
        (else
            (or
                (search_tree item (cadr bst))   ;; search left sub-tree
                (search_tree item (caddr bst))  ;; search right sub-tree
            )
        )
    )
)

;; function to insert an item into a binary search tree
(define (insert_item item bst)
    (cond
        ;; if there are no nodes in the tree, create a new tree with the item as the root
        ((null? bst)
            (list item '() '())
        )

        ;; if the item is less than the current node, insert it into the left-hand side of the tree
        ((< item (car bst))
            ;; create new bst with same root node, same right-hand side, but a left-hand side that has had the item inserted
            (list (car bst) (insert_item item (cadr bst)) (caddr bst))
        )

        ;; if the item is greater than the current node, insert it into the right-hand side of the tree
        ((> item (car bst))
            ;; create new bst with same root node, same left-hand side, but a right-hand side that has had the item inserted
            (list (car bst) (cadr bst) (insert_item item (caddr bst)))
        )

        ;; else the item is equal to the current node, so do nothing
        (else bst)
    )
)

;; function to insert a list of items into a binary search tree
(define (insert_list lst bst)
    (if (null? lst)
        ;; if the list is null, just return the bst with no changes
        bst

        ;; otherwise, recurse with the remainder of the list and the binary tree produced by inserting the first item of the list into bst
        (insert_list (cdr lst) (insert_item (car lst) bst))
    ) 
)

;; tree-sort function
(define (tree_sort lst)
    ;; inserting the list into a tree structure to sort it and then displaying the contents of that tree 
    (display_contents (insert_list lst '()))
)

;; function to insert an item into a binary search tree based off a sorting function
;; the sorting function should return accept two items and arguments, and return true if they were passed in order, and false otherwise or if they are equal
(define (insert_item_custom item bst sorter)
    (cond
        ;; if there are no nodes in the tree, create a new tree with the item as the root
        ((null? bst)
            (list item '() '())
        )

        ;; if the item is goes before the current node, insert it into the left-hand side of the tree
        ((sorter item (car bst))
            ;; create new bst with same root node, same right-hand side, but a left-hand side that has had the item inserted
            (list (car bst) (insert_item_custom item (cadr bst) sorter) (caddr bst))
        )

        ;; if the item goes after the current node, insert it into the right-hand side of the tree
        ((sorter (car bst) item)
            ;; create new bst with same root node, same left-hand side, but a right-hand side that has had the item inserted
            (list (car bst) (cadr bst) (insert_item_custom item (caddr bst) sorter))
        )

        ;; else the item is equal to the current node, so do nothing
        (else bst)
    )
)

;; sorter function which states whether the two arguments were supplied in strictly ascending order (i.e., if item == item2, return false)
(define (sort_ascending item1 item2)
    (if (< item1 item2)
        #t
        #f
    )
)


;; sorter function which states whether the two arguments were supplied in strictly descending order (i.e., if item == item2, return false)
(define (sort_descending item1 item2)
    (if (> item1 item2)
        #t
        #f
    )
)

;; sorter function which states whether the two arguments were supplied in strictly ascending order based on the final digit (i.e., if item == item2, return false)
(define (sort_ascending_last item1 item2)
    (if (< (modulo item1 10) (modulo item2 10))
        #t
        #f
    )
)

;; function to insert a list of items into a binary search tree in the order determined by a sorting function
(define (insert_list_custom lst bst sorter)
    (if (null? lst)
        ;; if the list is null, just return the bst with no changes
        bst

        ;; otherwise, recurse with the remainder of the list and the binary tree produced by inserting the first item of the list into bst
        (insert_list_custom (cdr lst) (insert_item_custom (car lst) bst sorter) sorter)
    ) 
)
