#lang racket

(provide ins_beg)
(provide ins_end)
(provide count_top_level)
(provide count_instances)
(provide count_instances_tr)
(provide count_instances_deep)

;; function to insert an element at the beginning of a list
(define (ins_beg el lst)
    ;; assuming that the second element is always a list
    (cons el lst)
)

;; function to insert an element at the end of a list
(define (ins_end el lst)
    ;; making el into a list if it isn't already
    (append lst (cons el empty))
)

;; function to count the number of top-level items in a list
(define (count_top_level lst)
    (if (null? lst)
        0                                   ;; return 0 if we've reached the end of the list
        (+ 1 (count_top_level (cdr lst)))   ;; return 1 plus the count_top_level of the second element of the cons pair (the rest of the list)
    )
)

;; non-tail recursive function to count the number of times a given item occurs in a list (assuming items are atomic)
(define (count_instances item lst)
    (if (null? lst)
        0   ;; return 0 if at the end of the list
        (+
            (if (equal? item (car lst))
                1   ;; if the item is equal to the first element of the list, add 1
                0   ;; if the item is not equal to the first element of the list, add 0
            )
            (count_instances item (cdr lst))    ;; recurse with the remainder of the list
        )
    )
)

;; helper function for count_instances_tr
(define (count_instances_tr_helper item lst cnt)
    (cond
        ;; return the count if the end of the list is reached (0 for empty list)
        ((null? lst)
            cnt
        )
        ;; if the first element of the list is equal to the item 
        ((eq? (car lst) item)
            ;; recurse with the remainder of the list and an incremented count
            (count_instances_tr_helper item (cdr lst) (+ cnt 1))
        )
        ;; if the first element of the list is not equal to the item
        (else
            ;; recurse with the remainder of the list and an unchanged count
            (count_instances_tr_helper item (cdr lst) cnt)
        )
    )
)

;; tail recursive function to count the number of times a given item occurs in a list (assuming items are atomic)
(define (count_instances_tr item lst)
    ;; calling helper function with the list and the count so far (0)
    (count_instances_tr_helper item lst 0)
)

;; function to count the number of times an item occurs in a list and its sub-lists
(define (count_instances_deep item lst)
    (cond
        ;; return nothing if we've reached the end of the list
        ((null? lst)
            0
        )

        ;; if the first item is a list, recurse through the first element and then the rest and return the sum of the two results
        ((pair? (car lst))
            (+ (count_instances_deep item (car lst)) (count_instances_deep item (cdr lst)))
        )

        ;; if the first element is equal to the item, add 1 to the count and recurse with the rest of the list
        ((eq? item (car lst)) ; If the first element is equal to the item, increment count
            (+ 1 (count_instances_deep item (cdr lst)))
        )

        ;; else if the first element is not equal to the item, recurse with the rest of the list
        (else
            (count_instances_deep item (cdr lst))
        )
    )
)
