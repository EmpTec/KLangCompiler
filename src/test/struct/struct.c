#include <stdio.h>
#include <stdlib.h>
#include "struct.h"
#include "../print/print.h"
#include "../test.h"

// C equivalent implementations of the funcitons written in k
testStruct* cGetTestStruct(long a, bool b, long c) {
  testStruct* result = (testStruct*) malloc(sizeof(testStruct));
  result->a = a;
  result->b = b;
  result->c = c;
  return result;
}

testStructRec* cGetTestStructRec(long a, testStructRec* b) {
  testStructRec* result = (testStructRec*) malloc(sizeof(testStructRec));
  result->a = a;
  result->b = b;
  return result;
}

int struct_testExpected_l(char *name, long expected, long result)
{
  if (expected == result)
  {
    succ(name, expected, result);
    return 0;
  }
  else
  {
    err(name, expected, result);
    return 1;
  }
}

int struct_testExpected_s(char *name, void* expected, void* result)
{
  if (expected == result)
  {
    succ_s(name, expected, result);
    return 0;
  }
  else
  {
    err_s(name, expected, result);
    return 1;
  }
}

int struct_testExpected_f(char *name, double expected, double result)
{
  if (expected == result)
  {
    succ_f(name, expected, result);
    return 0;
  }
  else
  {
    err_f(name, expected, result);
    return 1;
  }
}

int struct_testExpected_b(char *name, bool expected, bool result)
{
  if (expected == result)
  {
    succ_b(name, expected, result);
    return 0;
  }
  else
  {
    err_b(name, expected, result);
    return 1;
  }
}

int testStructCreation() {
  printf("\nStruct creation tests\n");
  testStruct* result = getTestStruct(1, false, 23.3);

  struct_testExpected_l("init field a", 1, result->a);
  struct_testExpected_b("init field b", false, result->b);
  struct_testExpected_f("init field c", 23.3, result->c);

  free(result);

  printf("\nRecursive struct creation tests\n");
  testStructRec* innerStruct = getTestStructRec(20, NULL);
  testStructRec* resultRec = getTestStructRec(10, innerStruct);

  struct_testExpected_l("init rec field a", 10, resultRec->a);
  struct_testExpected_s("init rec field b", innerStruct, resultRec->b);
  struct_testExpected_l("init inner field a", 20, resultRec->b->a);
  struct_testExpected_s("init inner field b", NULL, resultRec->b->b);

  free(resultRec);
  free(innerStruct);
}

int testStructGet() {
  printf("\nStruct getter tests\n");
  testStruct* result = getTestStruct(1, false, 23.3);

  struct_testExpected_l("get field a", 1, getStructFieldA(result));
  struct_testExpected_b("get field b", false, getStructFieldB(result));
  struct_testExpected_f("get field c", 23.3, getStructFieldC(result));

  free(result);

  printf("\nStruct getter tests\n");
  testStructRec* innerStruct = getTestStructRec(1, NULL);
  testStructRec* resultRec = getTestStructRec(20, innerStruct);

  struct_testExpected_l("get rec field a", 20, getStructFieldRecA(resultRec));
  struct_testExpected_s("get rec field b", innerStruct, getStructFieldRecB(resultRec));
  struct_testExpected_l("get inner field a", 1, getStructFieldRecA(getStructFieldRecB(resultRec)));
  struct_testExpected_s("get inner field b", NULL, getStructFieldRecB(getStructFieldRecB(resultRec)));

  free(resultRec);
  free(innerStruct);
}

void runStructTests() {
    testStructCreation();
    testStructGet();
}