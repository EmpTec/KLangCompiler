#include <stdio.h>
#include "functionCall.h"

void printArgSuccess(char* name, int expected, int result) {
  printf("SUCCESS:\t%s(<argumentList>)\tGOT: %d\tExpected: %d\n", name, result, expected);
}

void printArgError(char* name, int expected, int result) {
  printf("ERROR:\t\t%s(<argumentList>)\tGOT: %d\tExpected: %d\n", name, result, expected);
}

int argumentTest(char* name, int expected, int result) {
  if (expected == result) {
    printArgSuccess(name, expected, result);
    return 0;
  } else {
    printArgError(name, expected, result);
    return 1;
  }
}

int runFunctionCallTests () {
  int failed = 0;
  printf("\nFunctionCallTests Tests \n");
  failed += argumentTest("arg1", 1, arg1(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
  failed += argumentTest("arg2", 2, arg2(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
  failed += argumentTest("arg3", 3, arg3(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
  failed += argumentTest("arg4", 4, arg4(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
  failed += argumentTest("arg5", 5, arg5(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
  failed += argumentTest("arg6", 6, arg6(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
  failed += argumentTest("arg7", 7, arg7(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
  failed += argumentTest("arg8", 8, arg8(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
  failed += argumentTest("arg9", 9, arg9(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
  failed += argumentTest("arg10", 10, arg10(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
  return failed;
}