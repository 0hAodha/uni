#include <stdio.h>

int main() {
    int my_int;
    int* my_int_pointer;
    long my_long;
    double * my_double_pointer;
    char ** my_char_pointer_pointer;

    printf("The size of my_int is %lu bytes\n", sizeof(my_int));
    printf("The size of my_int_pointer is %lu bytes\n", sizeof(my_int_pointer));
    printf("The size of my_long is %lu bytes\n", sizeof(my_long));
    printf("The size of my_double_pointer is %lu bytes\n", sizeof(my_double_pointer));
    printf("The size of my_char_pointer_pointer is %lu bytes\n", sizeof(my_char_pointer_pointer));
}
