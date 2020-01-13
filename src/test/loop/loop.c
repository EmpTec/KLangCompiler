#include <stdio.h>
#include "loop.h"

void printLoopSuccess(char* name, int x,  int expected, int result) {
  printf("SUCCESS:\t%s(%d)\tGOT: %d\tExpected: %d\n", name, x, result, expected);
}

void printLoopError(char* name, int x, int expected, int result) {
  printf("ERROR:\t\t%s(%d)\tGOT: %d\tExpected: %d\n", name, x, result, expected);
}

int loopTest(char* name, int x, int expected, int result) {
  if (expected == result) {
    printLoopSuccess(name, x, expected, result);
    return 0;
  } else {
    printLoopError(name, x, expected, result);
    return 1;
  }
}

int runLoopTests() {
  printf("\nLoop Tests \n");
  int failed = 0;
  failed += loopTest("while", 5, 5, myWhile(5));
  failed += loopTest("doWhile", 0, 1, myDoWhile(0));
  failed += loopTest("doWhile", 1, 1, myDoWhile(1));
  
  return failed;
}