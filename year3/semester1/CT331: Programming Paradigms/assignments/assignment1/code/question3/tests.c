#include <stdio.h>
#include "tests.h"
#include "genericLinkedList.h"

// functions to print out different data types
// a more professional design might be to put these in the genericLinkedList header file but i only need these for testing purposes
void printChar(void* data) {
    printf("%c\n", *(char*) data);
}

void printStr(void* data) {
    printf("%s\n", (char*) data);
}

void printInt(void* data) {
    printf("%d\n", *(int*) data);
}

void runTests(){
    printf("Tests running...\n");

    listElement* l = createEl("Test String (1).", sizeof("Test String (1)."), printStr);
    //printf("%s\n%p\n", l->data, l->next);
    //Test create and traverse
    traverse(l);
    printf("\n");

    //Test insert after
    printf("Testing insertAfter()\n");
    listElement* l2 = insertAfter(l, "another string (2)", sizeof("another string (2)"), printStr);
    insertAfter(l2, "a final string (3)", sizeof("a final string (3)"), printStr);
    traverse(l);
    printf("\n");

    // test length function
    printf("Testing length()\n");
    int l_length = length(l);
    printf("The length of l is %d\n\n", l_length);

    // test push
    printf("Testing push()\n");
    push(&l, "yet another test string", sizeof("yet another test string"), printStr);
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
    enqueue(&l, "enqueued test string", sizeof("enqueued test string"), printStr);
    traverse(l);
    printf("\n");

    // test dequeue
    printf("Testing dequeue()\n");
    dequeue(l);
    traverse(l);
    printf("\n");

    printf("Testing pushing different data types\n");
    int myint = 42;
    push(&l, &myint, sizeof(myint), printInt);
    char mychar = 'c';
    push(&l, &mychar, sizeof(mychar), printChar);
    traverse(l);
    printf("\n\n");

    printf("\nTests complete.\n");
}
