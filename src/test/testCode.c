#include <stdio.h>
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

void testExpected(char* name, int x, int y, int expected, int result) {
  if (expected == result) {
    printSuccess(name, x, y, expected, result);
  } else {
    printError(name, x, y, expected, result);
  }
}

void test(char* name, int (*correctFunction)(int, int), int (*testFunction)(int, int), int x, int y) {
  int expected = correctFunction(x, y);
  int result = testFunction(x, y);
  testExpected(name, x, y, expected, result);
}

void testOneArg(char* name, int (*correctFunction)(int), int (*testFunction)(int), int x) {
  int expected = correctFunction(x);
  int result = testFunction(x);
  if (expected == result) {
    printSuccessOneArg(name, x, expected, result);
  } else {
    printErrorOneArg(name, x, expected, result);
  }
}

int main(){
  printf("\nAddition Tests \n");
  test("add", cAdd, add, 0, 0);
  test("add", cAdd, add, 1, 1);
  test("add", cAdd, add, 2, 0);
  test("add", cAdd, add, 1, 5);
  test("add", cAdd, add, -1, -1);

  printf("\nSubtraction Tests \n");
  test("sub", cSub, sub, 0, 0);
  test("sub", cSub, sub, 1, 1);
  test("sub", cSub, sub, 2, 0);
  test("sub", cSub, sub, 1, 5);
  test("sub", cSub, sub, -1, -1);

  printf("\nMultiplication Tests \n");
  test("mul", cMul, mul, 0, 0);
  test("mul", cMul, mul, 1, 1);
  test("mul", cMul, mul, 2, 0);
  test("mul", cMul, mul, 1, 5);
  test("mul", cMul, mul, -1, -1);

  printf("\nModulo Tests \n");
  test("modulo", cModulo, modulo, 1, 1);
  test("modulo", cModulo, modulo, 1, 5);
  test("modulo", cModulo, modulo, -1, -1);
  test("modulo", cModulo, modulo, 1337, 42);

  printf("\nNegative Tests\n");
  testOneArg("neg", cNeg, neg, 0);
  testOneArg("neg", cNeg, neg, 1);
  testOneArg("neg", cNeg, neg, -1);

  printf("\nIdentity Tests\n");
  testOneArg("id", cId, id, 0);
  testOneArg("id", cId, id, -1);
  testOneArg("id", cId, id, 15);

  printf("\nFunction Argument Tests\n");
  runFunctionCallTests();


  return 0;
}
  