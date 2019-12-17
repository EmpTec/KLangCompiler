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
  printf("\nFunction Call Tests \n");
  // Checks that parameters are correctly passed from gcc to functions
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
  // Checks that parameters are correctly passed from klang to functions
  failed += argumentTest("get1()", 1, get1());
  failed += argumentTest("get2()", 2, get2());
  failed += argumentTest("get3()", 3, get3());
  failed += argumentTest("get4()", 4, get4());
  failed += argumentTest("get5()", 5, get5());
  failed += argumentTest("get6()", 6, get6());
  failed += argumentTest("get7()", 7, get7());
  failed += argumentTest("get8()", 8, get8());
  failed += argumentTest("get9()", 9, get9());
  failed += argumentTest("get10()", 10, get10());
  return failed;
}