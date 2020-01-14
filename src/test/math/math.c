#include <stdio.h>
#include "math.h"
#include "../print/print.h"

int cAdd(int x, int y) {
  return x + y;
}
int cSub(int x, int y) {
  return x - y;
}
int cMul(int x, int y) {
  return x * y;
}
int cModulo(int x, int y) {
  return x % y;
}
int cNeg(int x) {
  return -x;
}
int cId(int x) {
  return x;
}
int cSelfMinus(int x) {
  x = x - 1;
  return x;
}


int math_testExpected(char* name, int x, int y, int expected, int result) {
  if (expected == result) {
    succPrefixTwo(name, x, y, expected, result);
    return 0;
  } else {
    errPrefixTwo(name, x, y, expected, result);
    return 1;
  }
}

int math_test(char* name, int (*correctFunction)(int, int), int (*testFunction)(int, int), int x, int y) {
  int expected = correctFunction(x, y);
  int result = testFunction(x, y);
  return math_testExpected(name, x, y, expected, result);
}

int math_testOneArg(char* name, int (*correctFunction)(int), int (*testFunction)(int), int x) {
  int expected = correctFunction(x);
  int result = testFunction(x);
  if (expected == result) {
    succPrefixOne(name, x, expected, result);
    return 0;
  } else {
    errPrefixOne(name, x, expected, result);
    return 1;
  }
}

int runMathTests() {
  int failed = 0;
    printf("\nAddition Tests \n");
  failed += math_test("add", cAdd, add, 0, 0);
  failed += math_test("add", cAdd, add, 1, 1);
  failed += math_test("add", cAdd, add, 2, 0);
  failed += math_test("add", cAdd, add, 1, 5);
  failed += math_test("add", cAdd, add, -1, -1);

  printf("\nSubtraction Tests \n");
  failed += math_test("sub", cSub, sub, 0, 0);
  failed += math_test("sub", cSub, sub, 1, 1);
  failed += math_test("sub", cSub, sub, 2, 0);
  failed += math_test("sub", cSub, sub, 1, 5);
  failed += math_test("sub", cSub, sub, -1, -1);

  printf("\nMultiplication Tests \n");
  failed += math_test("mul", cMul, mul, 0, 0);
  failed += math_test("mul", cMul, mul, 1, 1);
  failed += math_test("mul", cMul, mul, 2, 0);
  failed += math_test("mul", cMul, mul, 1, 5);
  failed += math_test("mul", cMul, mul, -1, -1);

  printf("\nModulo Tests \n");
  failed +=  math_test("modulo", cModulo, modulo, 1, 1);
  failed +=  math_test("modulo", cModulo, modulo, 1, 5);
  failed +=  math_test("modulo", cModulo, modulo, -1, -1);
  failed +=  math_test("modulo", cModulo, modulo, 1337, 42);

  printf("\nNegative Tests\n");
  failed += math_testOneArg("neg", cNeg, neg, 0);
  failed += math_testOneArg("neg", cNeg, neg, 1);
  failed += math_testOneArg("neg", cNeg, neg, -1);

  printf("\nIdentity Tests\n");
  failed +=  math_testOneArg("id", cId, id, 0);
  failed +=  math_testOneArg("id", cId, id, -1);
  failed +=  math_testOneArg("id", cId, id, 15);

  printf("\nMisc Tests\n");
  failed += math_testOneArg("selfMinus", cSelfMinus, selfMinus, 5);
  failed += math_testOneArg("selfMinus", cSelfMinus, selfMinus, 0);
  failed += math_testOneArg("selfMinus", cSelfMinus, selfMinus, 100);
  failed += math_testOneArg("selfMinus", cSelfMinus, selfMinus, -50);

  return failed;
}