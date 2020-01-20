#include <stdbool.h>
#include <stdio.h>
#include "print.h"

void succInfixTwo(char* name, int x, int y, int expected, int result) {
  incSuccess();
  printf("\033[0;32mSUCCESS:\t%d %s %d\tGOT: %d\tExpected: %d\033[0;0m\n", x, name, y, result, expected);
}

void errInfixTwo(char* name, int x, int y, int expected, int result) {
  incFailure();
  printf("\033[0;31mERROR:\t\t%d %s %d\tGOT: %d\tExpected: %d\033[0;0m\n", x, name, y, result, expected);
}

void succ(char* name, int expected, int result) {
  incSuccess();
  printf("\033[0;32mSUCCESS:\t%s:\tGOT: %d\tExpected: %d\033[0;0m\n", name, result, expected);
}

void err(char* name, int expected, int result) {
  incFailure();
  printf("\033[0;31mERROR:\t\t%s:\tGOT: %d\tExpected: %d\033[0;0m\n", name, result, expected);
}

void succPrefixOne(char* name, int x,  int expected, int result) {
  incSuccess();
  printf("\033[0;32mSUCCESS:\t%s(%d)\tGOT: %d\tExpected: %d\033[0;0m\n", name, x, result, expected);
}

void errPrefixOne(char* name, int x, int expected, int result) {
  incFailure();
  printf("\033[0;31mERROR:\t\t%s(%d)\tGOT: %d\tExpected: %d\033[0;0m\n", name, x, result, expected);
}

void succPrefixTwo(char* name, int x, int y, int expected, int result) {
  incSuccess();
  printf("\033[0;32mSUCCESS:\t%s(%d, %d)\tGOT: %d\tExpected: %d\033[0;0m\n", name, x, y, result, expected);
}

void errPrefixTwo(char* name, int x, int y,  int expected, int result) {
  incFailure();
  printf("\033[0;31mERROR:\t\t%s(%d, %d)\tGOT: %d\tExpected: %d\033[0;0m\n", name, x, y, result, expected);
}

void bool_succPrefixTwo(char* name, bool a, bool b, bool expected, bool result) {
  incSuccess();
  printf("\033[0;32mSUCCESS:\t%s(%s, %s)\tGOT: %s\tExpected: %s\033[0;0m\n", name, printBool(a), printBool(b), printBool(result), printBool(expected));
}

void bool_errPrefixTwo(char* name, bool a, bool b,  bool expected, bool result) {
  incFailure();
  printf("\033[0;32mSUCCESS:\t%s(%s, %s)\tGOT: %s\tExpected: %s\033[0;0m\n", name, printBool(a), printBool(b), printBool(result), printBool(expected));

}

char* printBool(bool a) {
  if (a == true) {
    return "true";
  }
  return "false";
}