#include <stdio.h>
#include "comparison.h"

void printSuccessComp(char* name, int x, int y, int expected, int result) {
  printf("SUCCESS:\t%d %s %d\tGOT: %d\tExpected: %d\n", x, name, y, result, expected);
}

void printErrorComp(char* name, int x, int y, int expected, int result) {
  printf("ERROR:\t\t%d %s %d\tGOT: %d\tExpected: %d\n", x, name, y, result, expected);
}

int comparisonTest(char* name, int x, int y, int expected, int result) {
  if (expected == result) {
    printSuccessComp(name, x, y, expected, result);
    return 0;
  } else {
    printErrorComp(name, x, y, expected, result);
    return 1;
  }
}

int runComparisonTests() {
  printf("\nComparison Tests \n");
  int failed = 0;
  failed += comparisonTest("==", 1, 1, 1, eq(1, 1));
  failed += comparisonTest("==", 1, 0, 0, eq(1, 0));
  failed += comparisonTest("==", 0, 1, 0, eq(0, 1));
  failed += comparisonTest("==", 0, 0, 1, eq(0, 0));

  failed += comparisonTest("<", 1, 1, 0, lt(1, 1));
  failed += comparisonTest("<", 1, 0, 0, lt(1, 0));
  failed += comparisonTest("<", 0, 1, 1, lt(0, 1));
  failed += comparisonTest("<", 0, 0, 0, lt(0, 0));

  failed += comparisonTest("<=", 1, 1, 1, lte(1, 1));
  failed += comparisonTest("<=", 1, 0, 0, lte(1, 0));
  failed += comparisonTest("<=", 0, 1, 1, lte(0, 1));
  failed += comparisonTest("<=", 0, 0, 1, lte(0, 0));

  failed += comparisonTest(">", 1, 1, 0, gt(1, 1));
  failed += comparisonTest(">", 1, 0, 1, gt(1, 0));
  failed += comparisonTest(">", 0, 1, 0, gt(0, 1));
  failed += comparisonTest(">", 0, 0, 0, gt(0, 0));

  failed += comparisonTest(">=", 1, 1, 1, gte(1, 1));
  failed += comparisonTest(">=", 1, 0, 1, gte(1, 0));
  failed += comparisonTest(">=", 0, 1, 0, gte(0, 1));
  failed += comparisonTest(">=", 0, 0, 1, gte(0, 0));

  return failed;
}