#include <stdio.h>
#include "recursive.h"

void printRecSuccess(char* name, int x,  int expected, int result) {
  printf("SUCCESS:\t%s(%d)\tGOT: %d\tExpected: %d\n", name, x, result, expected);
}

void printRecError(char* name, int x, int expected, int result) {
  printf("ERROR:\t\t%s(%d)\tGOT: %d\tExpected: %d\n", name, x, result, expected);
}

int recursiveTest(char* name, int x, int expected, int result) {
  if (expected == result) {
    printRecSuccess(name, x, expected, result);
    return 0;
  } else {
    printRecError(name, x, expected, result);
    return 1;
  }
}

int runRecursiveTests() {
  printf("\nRecursive Tests \n");
  int failed = 0;
  failed += recursiveTest("fac", 5, 120, fac(5));

  return failed;
}