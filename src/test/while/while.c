#include <stdio.h>
#include "while.h"

void printWhileSuccess(char* name, int x,  int expected, int result) {
  printf("SUCCESS:\t%s(%d)\tGOT: %d\tExpected: %d\n", name, x, result, expected);
}

void printWhileError(char* name, int x, int expected, int result) {
  printf("ERROR:\t\t%s(%d)\tGOT: %d\tExpected: %d\n", name, x, result, expected);
}

int whileTest(char* name, int x, int expected, int result) {
  if (expected == result) {
    printWhileSuccess(name, x, expected, result);
    return 0;
  } else {
    printWhileError(name, x, expected, result);
    return 1;
  }
}

int runWhileTests() {
  printf("\nWhile Tests \n");
  int failed = 0;
  failed += whileTest("while", 5, 5, myWhile(5));
  return failed;
}