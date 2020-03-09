#include <stdio.h>
#include "print.h"

char* printBool(bool a) {
  if (a == true) {
    return "true";
  }
  return "false";
}

void succInfixTwo(char* name, long x, long y, long expected, long result) {
  incSuccess();
  printf("\033[0;32mSUCCESS:\t%ld %s %ld\tGOT: %ld\tExpected: %ld\033[0;0m\n", x, name, y, result, expected);
}

void errInfixTwo(char* name, long x, long y, long expected, long result) {
  incFailure();
  printf("\033[0;31mERROR:\t\t%ld %s %ld\tGOT: %ld\tExpected: %ld\033[0;0m\n", x, name, y, result, expected);
}

void succ(char* name, long expected, long result) {
  incSuccess();
  printf("\033[0;32mSUCCESS:\t%s:\tGOT: %ld\tExpected: %ld\033[0;0m\n", name, result, expected);
}

void err(char* name, long expected, long result) {
  incFailure();
  printf("\033[0;31mERROR:\t\t%s:\tGOT: %ld\tExpected: %ld\033[0;0m\n", name, result, expected);
}

void succ_f(char* name, double expected, double result) {
  incSuccess();
  printf("\033[0;32mSUCCESS:\t%s:\tGOT: %f\tExpected: %f\033[0;0m\n", name, result, expected);
}

void err_f(char* name, double expected, double result) {
  incFailure();
  printf("\033[0;31mERROR:\t\t%s:\tGOT: %f\tExpected: %f\033[0;0m\n", name, result, expected);
}

void succ_s(char* name, void* expected, void* result) {
  incSuccess();
  printf("\033[0;32mSUCCESS:\t%s:\tGOT: %p\tExpected: %p\033[0;0m\n", name, result, expected);
}

void err_s(char* name, void* expected, void* result) {
  incFailure();
  printf("\033[0;31mERROR:\t\t%s:\tGOT: %p\tExpected: %p\033[0;0m\n", name, result, expected);
}

void succ_b(char* name, bool expected, bool result) {
  incSuccess();
  printf("\033[0;32mSUCCESS:\t%s:\tGOT: %s\tExpected: %s\033[0;0m\n", name, printBool(result), printBool(expected));
}

void err_b(char* name, bool expected, bool result) {
  incFailure();
  printf("\033[0;31mERROR:\t\t%s:\tGOT: %s\tExpected: %s\033[0;0m\n", name, printBool(result), printBool(expected));
}

void succPrefixOne(char* name, long x,  long expected, long result) {
  incSuccess();
  printf("\033[0;32mSUCCESS:\t%s(%ld)\tGOT: %ld\tExpected: %ld\033[0;0m\n", name, x, result, expected);
}

void errPrefixOne(char* name, long x, long expected, long result) {
  incFailure();
  printf("\033[0;31mERROR:\t\t%s(%ld)\tGOT: %ld\tExpected: %ld\033[0;0m\n", name, x, result, expected);
}

void float_succPrefixOne(char* name, double x,  double expected, double result) {
  incSuccess();
  printf("\033[0;32mSUCCESS:\t%s(%f)\tGOT: %f\tExpected: %f\033[0;0m\n", name, x, result, expected);
}

void float_errPrefixOne(char* name, double x, double expected, double result) {
  incFailure();
  printf("\033[0;31mERROR:\t\t%s(%f)\tGOT: %f\tExpected: %f\033[0;0m\n", name, x, result, expected);
}

void succPrefixTwo(char* name, long x, long y, long expected, long result) {
  incSuccess();
  printf("\033[0;32mSUCCESS:\t%s(%ld, %ld)\tGOT: %ld\tExpected: %ld\033[0;0m\n", name, x, y, result, expected);
}

void errPrefixTwo(char* name, long x, long y,  long expected, long result) {
  incFailure();
  printf("\033[0;31mERROR:\t\t%s(%ld, %ld)\tGOT: %ld\tExpected: %ld\033[0;0m\n", name, x, y, result, expected);
}

void float_succPrefixTwo(char* name, double x, double y, double expected, double result) {
  incSuccess();
  printf("\033[0;32mSUCCESS:\t%s(%f, %f)\tGOT: %f\tExpected: %f\033[0;0m\n", name, x, y, result, expected);
}

void float_errPrefixTwo(char* name, double x, double y,  double expected, double result) {
  incFailure();
  printf("\033[0;31mERROR:\t\t%s(%f, %f)\tGOT: %f\tExpected: %f\033[0;0m\n", name, x, y, result, expected);
}

void bool_succPrefixOne(char* name, bool x, bool expected, bool result) {
  incSuccess();
  printf("\033[0;32mSUCCESS:\t%s%s\tGOT: %s\tExpected: %s\033[0;0m\n", name, printBool(x), printBool(result), printBool(expected));
}

void bool_errPrefixOne(char* name, bool x, bool expected, bool result) {
  incFailure();
  printf("\033[0;31mERROR:\t\t%s%s\tGOT: %s\tExpected: %s\033[0;0m\n", name, printBool(x), printBool(result), printBool(expected));
}

void bool_succInfixTwo(char* name, bool a, bool b, bool expected, bool result) {
  incSuccess();
  printf("\033[0;32mSUCCESS:\t%s %s %s\tGOT: %s\tExpected: %s\033[0;0m\n", printBool(a), name, printBool(b), printBool(result), printBool(expected));
}

void bool_errInfixTwo(char* name, bool a, bool b,  bool expected, bool result) {
  incFailure();
  printf("\033[0;31mERROR:\t\t%s %s %s\tGOT: %s\tExpected: %s\033[0;0m\n", printBool(a), name, printBool(b), printBool(result), printBool(expected));
}