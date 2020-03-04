#include <stdio.h>
#include "math.h"
#include "../print/print.h"

int cAdd(int x, int y)
{
  return x + y;
}
int cSub(int x, int y)
{
  return x - y;
}
int cMul(int x, int y)
{
  return x * y;
}
int cModulo(int x, int y)
{
  return x % y;
}
int cNeg(int x)
{
  return -x;
}
int cId(int x)
{
  return x;
}
int cSelfMinus(int x)
{
  x = x - 1;
  return x;
}

double fcAdd(double x, double y)
{
  return x + y;
}
double fcSub(double x, double y)
{
  return x - y;
}
double fcMul(double x, double y)
{
  return x * y;
}
double fcNeg(double x)
{
  return -x;
}
double fcId(double x)
{
  return x;
}
double fcSelfMinus(double x)
{
  x = x - 1;
  return x;
}

int math_testExpected(char *name, int x, int y, int expected, int result)
{
  if (expected == result)
  {
    succPrefixTwo(name, x, y, expected, result);
    return 0;
  }
  else
  {
    errPrefixTwo(name, x, y, expected, result);
    return 1;
  }
}

int math_test(char *name, int (*correctFunction)(int, int), int (*testFunction)(int, int), int x, int y)
{
  int expected = correctFunction(x, y);
  int result = testFunction(x, y);
  return math_testExpected(name, x, y, expected, result);
}

float math_test_f(char *name, float (*correctFunction)(float, float), float (*testFunction)(float, float), float x, float y) {
  float expected = correctFunction(x, y);
  float result = testFunction(x, y);
  return math_testExpected(name, x, y, expected, result);
}

int math_testOneArg(char *name, int (*correctFunction)(int), int (*testFunction)(int), int x)
{
  int expected = correctFunction(x);
  int result = testFunction(x);
  if (expected == result)
  {
    succPrefixOne(name, x, expected, result);
    return 0;
  }
  else
  {
    errPrefixOne(name, x, expected, result);
    return 1;
  }
}

float math_testOneArg_f(char *name, float (*correctFunction)(float), float (*testFunction)(float), float x)
{
  float expected = correctFunction(x);
  float result = testFunction(x);
  if (expected == result)
  {
    succPrefixOne(name, x, expected, result);
    return 0;
  }
  else
  {
    errPrefixOne(name, x, expected, result);
    return 1;
  }
}

void math_simpleTest(char *name, int expected, int result) {
  if (expected == result) {
    succ(name, expected, result);
  } else {
    err(name, expected, result);
  }
}

int runMathTests()
{
  printf("\nAddition Tests \n");
  math_test("add", cAdd, add, 0, 0);
  math_test("add", cAdd, add, 1, 1);
  math_test("add", cAdd, add, 2, 0);
  math_test("add", cAdd, add, 1, 5);
  math_test("add", cAdd, add, -1, -1);

  printf("\nSubtraction Tests \n");
  math_test("sub", cSub, sub, 0, 0);
  math_test("sub", cSub, sub, 1, 1);
  math_test("sub", cSub, sub, 2, 0);
  math_test("sub", cSub, sub, 1, 5);
  math_test("sub", cSub, sub, -1, -1);

  printf("\nMultiplication Tests \n");
  math_test("mul", cMul, mul, 0, 0);
  math_test("mul", cMul, mul, 1, 1);
  math_test("mul", cMul, mul, 2, 0);
  math_test("mul", cMul, mul, 1, 5);
  math_test("mul", cMul, mul, -1, -1);

  printf("\nModulo Tests \n");
  math_test("modulo", cModulo, modulo, 1, 1);
  math_test("modulo", cModulo, modulo, 1, 5);
  math_test("modulo", cModulo, modulo, -1, -1);
  math_test("modulo", cModulo, modulo, 1337, 42);

  printf("\nNegative Tests\n");
  math_testOneArg("neg", cNeg, neg, 0);
  math_testOneArg("neg", cNeg, neg, 1);
  math_testOneArg("neg", cNeg, neg, -1);

  printf("\nIdentity Tests\n");
  math_testOneArg("id", cId, id, 0);
  math_testOneArg("id", cId, id, -1);
  math_testOneArg("id", cId, id, 15);

  printf("\nFloat Addition Tests \n");
  math_test_f("fadd", fcAdd, fadd, 0.0, 0.0);
  math_test_f("fadd", fcAdd, fadd, 1.0, 1.0);
  math_test_f("fadd", fcAdd, fadd, 2.0, 0.0);
  math_test_f("fadd", fcAdd, fadd, 1.0, 5.0);
  math_test_f("fadd", fcAdd, fadd, -1.0, -1.0);

  printf("\nFloat Subtraction Tests \n");
  math_test_f("fsub", fcSub, fsub, 0.0, 0.0);
  math_test_f("fsub", fcSub, fsub, 1.0, 1.0);
  math_test_f("fsub", fcSub, fsub, 2.0, 0.0);
  math_test_f("fsub", fcSub, fsub, 1.0, 5.0);
  math_test_f("fsub", fcSub, fsub, -1.0, -1.0);

  printf("\nFloat Multiplication Tests \n");
  math_test_f("fmul", fcMul, fmul, 0.0, 0.0);
  math_test_f("fmul", fcMul, fmul, 1.0, 1.0);
  math_test_f("fmul", fcMul, fmul, 2.0, 0.0);
  math_test_f("fmul", fcMul, fmul, 1.0, 5.0);
  math_test_f("fmul", fcMul, fmul, -1.0, -1.0);

  printf("\nFloat Negative Tests\n");
  math_testOneArg_f("fneg", fcNeg, fneg, 0.0);
  math_testOneArg_f("fneg", fcNeg, fneg, 1.0);
  math_testOneArg_f("fneg", fcNeg, fneg, -1.0);

  printf("\nFloat Identity Tests\n");
  math_testOneArg_f("fid", fcId, fid, 0.0);
  math_testOneArg_f("fid", fcId, fid, -1.0);
  math_testOneArg_f("fid", fcId, fid, 15.0);

  printf("\nMisc Tests\n");
  math_testOneArg("selfMinus", cSelfMinus, selfMinus, 5);
  math_testOneArg("selfMinus", cSelfMinus, selfMinus, 0);
  math_testOneArg("selfMinus", cSelfMinus, selfMinus, 100);
  math_testOneArg("selfMinus", cSelfMinus, selfMinus, -50);
  math_simpleTest("precedence t1", 16, t1());
  math_simpleTest("precedence t2", 16, t2());
  math_simpleTest("precedence t3", 16, t3());
  math_simpleTest("precedence t4", 16, t4());
  math_simpleTest("precedence t5", 16, t5());
  math_simpleTest("precedence t6", 16, t6());
  math_simpleTest("precedence t7", 16, t7());
  math_simpleTest("precedence t8", 18, t8());
}