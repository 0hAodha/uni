#include <stdio.h>
#include "tests.h"
#include "linkedList.h"

void runTests(){
    printf("Tests running...\n");

    listElement* l = createEl("Test String (1).", sizeof("Test String (1)."));
    //printf("%s\n%p\n", l->data, l->next);
    //Test create and traverse
    traverse(l);
    printf("\n");

    //Test insert after
    printf("Testing insertAfter()\n");
    listElement* l2 = insertAfter(l, "another string (2)", sizeof("another test string(2)"));
    insertAfter(l2, "a final string (3)", sizeof("a final string(3)"));
    traverse(l);
    printf("\n");

    // test length function
    printf("Testing length()\n");
    int l_length = length(l);
    printf("The length of l is %d\n\n", l_length);

    // test push
    printf("Testing push()\n");
    push(&l, "yet another test string", sizeof("yet another test string"));
    traverse(l);
    printf("\n\n");

    // test pop
    printf("Testing pop()\n");
    listElement* popped = pop(&l);
    traverse(l);
    printf("\n\n");

    // Test delete after
    printf("Testing deleteAfter()\n");
    deleteAfter(l);
    traverse(l);
    printf("\n");

    // test enqueue
    printf("Testing enqueue()\n");
    enqueue(&l, "enqueued test string", sizeof("enqueued test string"));
    traverse(l);
    printf("\n");

    // test dequeue
    printf("Testing dequeue()\n");
    dequeue(l);
    traverse(l);
    printf("\n");

    printf("\nTests complete.\n");
}
