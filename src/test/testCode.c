#include <stdio.h>
#include <stdlib.h>
#include "tests.h"
#include "testCode.h"

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

void printSuccess(char* name, int x, int y, int expected, int result) {
  printf("SUCCESS:\t%s(%d, %d)\tGOT: %d\tExpected: %d\n", name, x, y, result, expected);
}

void printSuccessOneArg(char* name, int x, int expected, int result) {
  printf("SUCCESS:\t%s(%d)\tGOT: %d\tExpected: %d\n", name, x, result, expected);
}

void printError(char* name, int x, int y, int expected, int result) {
  printf("ERROR:\t\t%s(%d, %d)\tGOT: %d\tExpected: %d\n", name, x, y, result, expected);
}

void printErrorOneArg(char* name, int x, int expected, int result) {
  printf("ERROR:\t\t%s(%d)\tGOT: %d\tExpected: %d\n", name, x, result, expected);
}

int testExpected(char* name, int x, int y, int expected, int result) {
  if (expected == result) {
    printSuccess(name, x, y, expected, result);
    return 0;
  } else {
    printError(name, x, y, expected, result);
    return 1;
  }
}

int test(char* name, int (*correctFunction)(int, int), int (*testFunction)(int, int), int x, int y) {
  int expected = correctFunction(x, y);
  int result = testFunction(x, y);
  return testExpected(name, x, y, expected, result);
}

int testOneArg(char* name, int (*correctFunction)(int), int (*testFunction)(int), int x) {
  int expected = correctFunction(x);
  int result = testFunction(x);
  if (expected == result) {
    printSuccessOneArg(name, x, expected, result);
    return 0;
  } else {
    printErrorOneArg(name, x, expected, result);
    return 1;
  }
}

int main(){
  int failed = 0;
  printf("\nAddition Tests \n");
  failed += test("add", cAdd, add, 0, 0);
  failed += test("add", cAdd, add, 1, 1);
  failed += test("add", cAdd, add, 2, 0);
  failed += test("add", cAdd, add, 1, 5);
  failed += test("add", cAdd, add, -1, -1);

  printf("\nSubtraction Tests \n");
  failed += test("sub", cSub, sub, 0, 0);
  failed += test("sub", cSub, sub, 1, 1);
  failed += test("sub", cSub, sub, 2, 0);
  failed += test("sub", cSub, sub, 1, 5);
  failed += test("sub", cSub, sub, -1, -1);

  printf("\nMultiplication Tests \n");
  failed += test("mul", cMul, mul, 0, 0);
  failed += test("mul", cMul, mul, 1, 1);
  failed += test("mul", cMul, mul, 2, 0);
  failed += test("mul", cMul, mul, 1, 5);
  failed += test("mul", cMul, mul, -1, -1);

  printf("\nModulo Tests \n");
  failed +=  test("modulo", cModulo, modulo, 1, 1);
  failed +=  test("modulo", cModulo, modulo, 1, 5);
  failed +=  test("modulo", cModulo, modulo, -1, -1);
  failed +=  test("modulo", cModulo, modulo, 1337, 42);

  printf("\nNegative Tests\n");
  failed += testOneArg("neg", cNeg, neg, 0);
  failed += testOneArg("neg", cNeg, neg, 1);
  failed += testOneArg("neg", cNeg, neg, -1);

  printf("\nIdentity Tests\n");
  failed +=  testOneArg("id", cId, id, 0);
  failed +=  testOneArg("id", cId, id, -1);
  failed +=  testOneArg("id", cId, id, 15);

  // Tests for passing arguments to functions
  failed += runFunctionCallTests();

  // Tests for recursive funtions
  failed += runRecursiveTests();

  printf("\n=== Failed Tests: %d\n", failed);

  if (failed > 0) {
    return EXIT_FAILURE;
  } else {
    return EXIT_SUCCESS;
  }
}
  