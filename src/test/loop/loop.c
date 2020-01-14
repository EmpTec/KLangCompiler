#include <stdio.h>
#include "loop.h"
#include "../print/print.h"

int loopTest(char* name, int x, int expected, int result) {
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
  int failed = 0;
  failed += loopTest("while", 5, 5, myWhile(5));
  failed += loopTest("doWhile", 0, 1, myDoWhile(0));
  failed += loopTest("doWhile", 1, 1, myDoWhile(1));
  failed += loopTest("for", 5, 5, myFor(5));
  failed += loopTest("for", 0, 0, myFor(0));
  
  return failed;
}