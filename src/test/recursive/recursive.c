#include <stdio.h>
#include "recursive.h"
#include "../print/print.h"

int recursiveTest(char* name, int x, int expected, int result) {
  if (expected == result) {
    succPrefixOne(name, x, expected, result);
    return 0;
  } else {
    errPrefixOne(name, x, expected, result);
    return 1;
  }
}

int runRecursiveTests() {
  printf("\nRecursive Tests \n");

  recursiveTest("fac", 5, 120, fac(5));
}