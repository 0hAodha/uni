#lang racket

;; a cons pair of two numbers
(cons 1 2)

;; a list of 3 numbers using only the cons function
;; this could be more easily done using the single quote `'` (i.e., `'(1 2 3)`) but i don't use it as it seemed against the spirit of the question
(cons 1 (cons 2 (cons 3 empty)))   

;; a list containing a string, a number, and a nested list of three numbers using only the cons function
(cons "a string"
    (cons 0 
        (cons (cons 1 (cons 2 (cons 3 empty))) empty)
    )
)

;; a list containing a string, a number, and a nested list of three numbers, using only the list function
(list "a string" 0 (list 1 2 3))

;; a list containing a string, a number, and a nested list of three numbers, using only the append function
;; using `'` as the arguments of the `append` function must be themselves lists
(append '("a string") '(0) '((1 2 3)))
