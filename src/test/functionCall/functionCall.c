#include <stdio.h>
#include "functionCall.h"
#include "../print/print.h"

int argumentTest(char* name, int expected, int result) {
  if (expected == result) {
    succ(name, expected, result);
    return 0;
  } else {
    err(name, expected, result);
    return 1;
  }
}

int runFunctionCallTests () {
  int failed = 0;
  printf("\nFunction Call Tests \n");
  // Checks that parameters are correctly passed from gcc to functions
  failed += argumentTest("arg1(...args)", 1, arg1(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
  failed += argumentTest("arg2(...args)", 2, arg2(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
  failed += argumentTest("arg3(...args)", 3, arg3(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
  failed += argumentTest("arg4(...args)", 4, arg4(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
  failed += argumentTest("arg5(...args)", 5, arg5(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
  failed += argumentTest("arg6(...args)", 6, arg6(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
  failed += argumentTest("arg7(...args)", 7, arg7(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
  failed += argumentTest("arg8(...args)", 8, arg8(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
  failed += argumentTest("arg9(...args)", 9, arg9(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
  failed += argumentTest("arg10(...args)", 10, arg10(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
  // Checks that parameters are correctly passed from klang to functions
  failed += argumentTest("get1(...args)", 1, get1());
  failed += argumentTest("get2(...args)", 2, get2());
  failed += argumentTest("get3(...args)", 3, get3());
  failed += argumentTest("get4(...args)", 4, get4());
  failed += argumentTest("get5(...args)", 5, get5());
  failed += argumentTest("get6(...args)", 6, get6());
  failed += argumentTest("get7(...args)", 7, get7());
  failed += argumentTest("get8(...args)", 8, get8());
  failed += argumentTest("get9(...args)", 9, get9());
  failed += argumentTest("get10(...args)", 10, get10());
  return failed;
}