#include <stdio.h>
#include "loop.h"
#include "../print/print.h"
#include "../test.h"

int loopTest(char* name, long x, long expected, long result) {
  if (expected == result) {
    succPrefixOne(name, x, expected, result);
    return 0;
  } else {
    errPrefixOne(name, x, expected, result);
    return 1;
  }
}

int runLoopTests() {
  printf("\nLoop Tests \n");

  loopTest("while", 1, 1, myWhile(1));
  loopTest("while", 0, 0, myWhile(0));
  loopTest("while", 10, 10, myWhile(10));
  loopTest("while", 5, 5, myWhile(5));
  loopTest("doWhile", 0, 1, myDoWhile(0));
  loopTest("doWhile", 1, 1, myDoWhile(1));
  loopTest("for", 5, 5, myFor(5));
  loopTest("for", 0, 0, myFor(0));
}