#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "linkedList.h"

typedef struct listElementStruct{
  char* data;
  size_t size;
  struct listElementStruct* next;
} listElement;

//Creates a new linked list element with given content of size
//Returns a pointer to the element
listElement* createEl(char* data, size_t size){
  listElement* e = malloc(sizeof(listElement));
  if(e == NULL){
    //malloc has had an error
    return NULL; //return NULL to indicate an error.
  }
  char* dataPointer = malloc(sizeof(char)*size);
  if(dataPointer == NULL){
    //malloc has had an error
    free(e); //release the previously allocated memory
    return NULL; //return NULL to indicate an error.
  }
  strcpy(dataPointer, data);
  e->data = dataPointer;
  e->size = size;
  e->next = NULL;
  return e;
}

//Prints out each element in the list
void traverse(listElement* start){
  listElement* current = start;
  while(current != NULL){
    printf("%s\n", current->data);
    current = current->next;
  }
}

//Inserts a new element after the given el
//Returns the pointer to the new element
listElement* insertAfter(listElement* el, char* data, size_t size){
  listElement* newEl = createEl(data, size);
  listElement* next = el->next;
  newEl->next = next;
  el->next = newEl;
  return newEl;
}

//Delete the element after the given el
void deleteAfter(listElement* after){
  listElement* delete = after->next;
  listElement* newNext = delete->next;
  after->next = newNext;
  //need to free the memory because we used malloc
  free(delete->data);
  free(delete);
}

// returns the number of elements in the list
int length(listElement* list) {
    int length = 0;
    listElement* current = list;

    // traversing the list and counting each element
    while(current != NULL){
        length++;
        current = current->next;
    }

    return length;
}

// push a new element onto the head of a list and update the list reference using side effects
void push(listElement** list, char* data, size_t size) {
    // create the new element
    listElement* newElement = createEl(data, size);

    // handle malloc errors
    if (newElement == NULL) {
        fprintf(stderr, "Memory allocation failed.\n");
        exit(EXIT_FAILURE);
    }

    // make the the new element point to the current head of the list
    newElement->next = *list;

    // make the list reference to point to the new head element 
    *list = newElement;
}


// pop an element from the head of a list and update the list reference using side effects
// assuming that the desired return value here is the popped element, as is standard for POP operations
listElement* pop(listElement** list) {
    // don't bother if list is non existent
    if (*list == NULL) { return NULL; }
;  
    // getting reference to the element to be popped
    listElement* poppedElement = *list;

    // make the the second element the new head of the list -- this could be NULL, so the list would be NULL also
    *list = (*list)->next;

    // detach the popped element from the list
    poppedElement->next = NULL;

    return poppedElement;
}


// enque a new element onto the head of the list and update the list reference using side effects
// essentially the same as push
void enqueue(listElement** list, char* data, size_t size) {
    // create the new element
    listElement* newElement = createEl(data, size);

    // handle malloc errors
    if (newElement == NULL) {
        fprintf(stderr, "Memory allocation failed.\n");
        exit(EXIT_FAILURE);
    }

    // make the the new element point to the current head of the list
    newElement->next = *list;

    // make the list reference to point to the new head element 
    *list = newElement;
} 


// dequeue an element from the tail of the list by removing the element from the list via side effects, and returning the removed item
// assuming that we want to return the dequeued element rather than the list itself, as enqueue returns nothing and uses side effects, so dequeue should also use side effects
listElement* dequeue(listElement* list) {
    // there are three cases that we must consider: a list with 0 elements, a list with 1 element, & a list with >=2 elements

    // don't bother if list is non existent
    if (list == NULL) { return NULL; }

    // if there is only one element in the list, i.e. the head element is also the tail element, just returning this element
    // this means that the listElement pointer that was passed to this function won't be updated
    // ideally, we would set it to NULL but we can't do that since `list` is a pointer that has been passed by value, so we can't update the pointer itself. we would need a pointer to a pointer to have been passed 
    if (list->next == NULL) {
        return list;
    }

    // traversing the list to find the second-to-last element
    listElement* current = list;
    while (current->next->next != NULL) {
        current = current->next;
    }

    // get reference to the element to be dequeued
    listElement* dequeuedElement = current->next;

    // make the penultimate element the tail by removing reference to the old tail
    current->next = NULL;

    return list;
}
