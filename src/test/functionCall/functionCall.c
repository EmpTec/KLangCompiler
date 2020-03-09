#include <stdio.h>
#include "functionCall.h"
#include "../print/print.h"

int argumentTest(char* name, long expected, long result) {
  if (expected == result) {
    succ(name, expected, result);
    return 0;
  } else {
    err(name, expected, result);
    return 1;
  }
}

int argumentTest_f(char* name, long expected, long result) {
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

  printf("\nFunction Call Tests With Floats And Integers \n");
  argumentTest_f("fargMix1(...args)", 1.0, fargMix1(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0));
  argumentTest_f("fargMix2(...args)", 2, fargMix2(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0));
  argumentTest_f("fargMix3(...args)", 3.0, fargMix3(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0));
  argumentTest_f("fargMix4(...args)", 4, fargMix4(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0));
  argumentTest_f("fargMix5(...args)", 5.0, fargMix5(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0));
  argumentTest_f("fargMix6(...args)", 6, fargMix6(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0));
  argumentTest_f("fargMix7(...args)", 7.0, fargMix7(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0));
  argumentTest_f("fargMix8(...args)", 8, fargMix8(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0));
  argumentTest_f("fargMix9(...args)", 9.0, fargMix9(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0));
  argumentTest_f("fargMix10(...args)", 10, fargMix10(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0));
  // Checks that parameters are correctly passed from klang to functions
  argumentTest_f("fgetMix1(...args)", 1.0, fgetMix1());
  argumentTest("fgetMix2(...args)", 2, fgetMix2());
  argumentTest_f("fgetMix3(...args)", 3.0, fgetMix3());
  argumentTest("fgetMix4(...args)", 4, fgetMix4());
  argumentTest_f("fgetMix5(...args)", 5.0, fgetMix5());
  argumentTest("fgetMix6(...args)", 6, fgetMix6());
  argumentTest_f("fgetMix7(...args)", 7.0, fgetMix7());
  argumentTest("fgetMix8(...args)", 8, fgetMix8());
  argumentTest_f("fgetMix9(...args)", 9.0, fgetMix9());
  argumentTest("fgetMix10(...args)", 10, fgetMix10());

  printf("\nTail Call Tests \n");
  // Checks that tails calls are properly invoked
  argumentTest("arg1Tail(...args)", 1, arg1Tail(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 10));
  argumentTest("arg2Tail(...args)", 2, arg2Tail(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 10));
  argumentTest("arg3Tail(...args)", 3, arg3Tail(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 10));
  argumentTest("arg4Tail(...args)", 4, arg4Tail(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 10));
  argumentTest("arg5Tail(...args)", 5, arg5Tail(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 10));
  argumentTest("arg6Tail(...args)", 6, arg6Tail(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 10));
  argumentTest("arg7Tail(...args)", 7, arg7Tail(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 10));
  argumentTest("arg8Tail(...args)", 8, arg8Tail(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 10));
  argumentTest("arg9Tail(...args)", 9, arg9Tail(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 10));
  argumentTest("arg10Tail(...args)", 10, arg10Tail(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 10));
  // Checks that parameters are correctly passed from klang to functions
  argumentTest("get1Tail(...args)", 1, get1Tail(10));
  argumentTest("get2Tail(...args)", 2, get2Tail(10));
  argumentTest("get3Tail(...args)", 3, get3Tail(10));
  argumentTest("get4Tail(...args)", 4, get4Tail(10));
  argumentTest("get5Tail(...args)", 5, get5Tail(10));
  argumentTest("get6Tail(...args)", 6, get6Tail(10));
  argumentTest("get7Tail(...args)", 7, get7Tail(10));
  argumentTest("get8Tail(...args)", 8, get8Tail(10));
  argumentTest("get9Tail(...args)", 9, get9Tail(10));
  argumentTest("get10Tail(...args)", 10, get10Tail(10));
}