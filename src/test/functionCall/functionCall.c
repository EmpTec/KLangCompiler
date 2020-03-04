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

int argumentTest_f(char* name, int expected, int result) {
  if (expected == result) {
    succ_f(name, expected, result);
    return 0;
  } else {
    err_f(name, expected, result);
    return 1;
  }
}

int runFunctionCallTests () {
  printf("\nFunction Call Tests \n");
  // Checks that parameters are correctly passed from gcc to functions
  argumentTest("arg1(...args)", 1, arg1(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
  argumentTest("arg2(...args)", 2, arg2(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
  argumentTest("arg3(...args)", 3, arg3(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
  argumentTest("arg4(...args)", 4, arg4(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
  argumentTest("arg5(...args)", 5, arg5(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
  argumentTest("arg6(...args)", 6, arg6(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
  argumentTest("arg7(...args)", 7, arg7(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
  argumentTest("arg8(...args)", 8, arg8(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
  argumentTest("arg9(...args)", 9, arg9(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
  argumentTest("arg10(...args)", 10, arg10(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
  // Checks that parameters are correctly passed from klang to functions
  argumentTest("get1(...args)", 1, get1());
  argumentTest("get2(...args)", 2, get2());
  argumentTest("get3(...args)", 3, get3());
  argumentTest("get4(...args)", 4, get4());
  argumentTest("get5(...args)", 5, get5());
  argumentTest("get6(...args)", 6, get6());
  argumentTest("get7(...args)", 7, get7());
  argumentTest("get8(...args)", 8, get8());
  argumentTest("get9(...args)", 9, get9());
  argumentTest("get10(...args)", 10, get10());

  printf("\nFunction Call Tests With Floats \n");
  argumentTest_f("farg1(...args)", 1.0, farg1(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0));
  argumentTest_f("farg2(...args)", 2.0, farg2(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0));
  argumentTest_f("farg3(...args)", 3.0, farg3(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0));
  argumentTest_f("farg4(...args)", 4.0, farg4(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0));
  argumentTest_f("farg5(...args)", 5.0, farg5(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0));
  argumentTest_f("farg6(...args)", 6.0, farg6(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0));
  argumentTest_f("farg7(...args)", 7.0, farg7(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0));
  argumentTest_f("farg8(...args)", 8.0, farg8(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0));
  argumentTest_f("farg9(...args)", 9.0, farg9(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0));
  argumentTest_f("farg10(...args)", 10.0, farg10(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0));
  // Checks that parameters are correctly passed from klang to functions
  argumentTest_f("fget1(...args)", 1.0, fget1());
  argumentTest_f("fget2(...args)", 2.0, fget2());
  argumentTest_f("fget3(...args)", 3.0, fget3());
  argumentTest_f("fget4(...args)", 4.0, fget4());
  argumentTest_f("fget5(...args)", 5.0, fget5());
  argumentTest_f("fget6(...args)", 6.0, fget6());
  argumentTest_f("fget7(...args)", 7.0, fget7());
  argumentTest_f("fget8(...args)", 8.0, fget8());
  argumentTest_f("fget9(...args)", 9.0, fget9());
  argumentTest_f("fget10(...args)", 10.0, fget10());
}