#include <stdio.h>
#include "comparison.h"
#include "../print/print.h"

int comparisonTest(char* name, int x, int y, int expected, int result) {
  if (expected == result) {
    succInfixTwo(name, x, y, expected, result);
    return 0;
  } else {
    errInfixTwo(name, x, y, expected, result);
    return 1;
  }
}

int boolTestOne(char* name, bool x, bool expected, bool result) {
  if (expected == result) {
    bool_succPrefixOne(name, x, expected, result);
    return 0;
  } else {
    bool_errPrefixOne(name, x, expected, result);
    return 1;
  }
}

int boolTestTwo(char* name, bool a, bool b, bool expected, bool result) {
  if (expected == result) {
    bool_succInfixTwo(name, a, b, expected, result);
    return 0;
  } else {
    bool_errInfixTwo(name, a, b, expected, result);
    return 1;
  }
}

void runComparisonTests() {
  printf("\nComparison Tests \n");
  comparisonTest("==", 1, 1, 1, eq(1, 1));
  comparisonTest("==", 1, 0, 0, eq(1, 0));
  comparisonTest("==", 0, 1, 0, eq(0, 1));
  comparisonTest("==", 0, 0, 1, eq(0, 0));

  comparisonTest("!=", 1, 1, 0, neq(1, 1));
  comparisonTest("!=", 1, 0, 1, neq(1, 0));
  comparisonTest("!=", 0, 1, 1, neq(0, 1));
  comparisonTest("!=", 0, 0, 0, neq(0, 0));

  comparisonTest("<", 1, 1, 0, lt(1, 1));
  comparisonTest("<", 1, 0, 0, lt(1, 0));
  comparisonTest("<", 0, 1, 1, lt(0, 1));
  comparisonTest("<", 0, 0, 0, lt(0, 0));

  comparisonTest("<=", 1, 1, 1, lte(1, 1));
  comparisonTest("<=", 1, 0, 0, lte(1, 0));
  comparisonTest("<=", 0, 1, 1, lte(0, 1));
  comparisonTest("<=", 0, 0, 1, lte(0, 0));

  comparisonTest(">", 1, 1, 0, gt(1, 1));
  comparisonTest(">", 1, 0, 1, gt(1, 0));
  comparisonTest(">", 0, 1, 0, gt(0, 1));
  comparisonTest(">", 0, 0, 0, gt(0, 0));

  comparisonTest(">=", 1, 1, 1, gte(1, 1));
  comparisonTest(">=", 1, 0, 1, gte(1, 0));
  comparisonTest(">=", 0, 1, 0, gte(0, 1));
  comparisonTest(">=", 0, 0, 1, gte(0, 0));

  boolTestTwo("&&", true, true, true, and(true, true));
  boolTestTwo("&&", true, false, false, and(true, false));
  boolTestTwo("&&", false, true, false, and(false, true));
  boolTestTwo("&&", false, false, false, and(false, false));

  boolTestTwo("||", true, true, true, or(true, true));
  boolTestTwo("||", true, false, true, or(true, false));
  boolTestTwo("||", false, true, true, or(false, true));
  boolTestTwo("||", false, false, false, or(false, false));

  boolTestOne("!", true, false, not(true));
  boolTestOne("!", false, true, not(false));
}