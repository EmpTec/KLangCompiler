#include <stdio.h>
#include "print.h"

void succInfixTwo(char* name, int x, int y, int expected, int result) {
  printf("\033[0;32mSUCCESS:\t%d %s %d\tGOT: %d\tExpected: %d\033[0;0m\n", x, name, y, result, expected);
}

void errInfixTwo(char* name, int x, int y, int expected, int result) {
  printf("\033[0;31mERROR:\t\t%d %s %d\tGOT: %d\tExpected: %d\033[0;0m\n", x, name, y, result, expected);
}

void succ(char* name, int expected, int result) {
  printf("\033[0;32mSUCCESS:\t%s:\tGOT: %d\tExpected: %d\033[0;0m\n", name, result, expected);
}

void err(char* name, int expected, int result) {
  printf("\033[0;31mERROR:\t\t%s:\tGOT: %d\tExpected: %d\033[0;0m\n", name, result, expected);
}

void succPrefixOne(char* name, int x,  int expected, int result) {
  printf("\033[0;32mSUCCESS:\t%s(%d)\tGOT: %d\tExpected: %d\033[0;0m\n", name, x, result, expected);
}

void errPrefixOne(char* name, int x, int expected, int result) {
  printf("\033[0;31mERROR:\t\t%s(%d)\tGOT: %d\tExpected: %d\033[0;0m\n", name, x, result, expected);
}

void succPrefixTwo(char* name, int x, int y, int expected, int result) {
  printf("\033[0;32mSUCCESS:\t%s(%d, %d)\tGOT: %d\tExpected: %d\033[0;0m\n", name, x, y, result, expected);
}

void errPrefixTwo(char* name, int x, int y,  int expected, int result) {
  printf("\033[0;31mERROR:\t\t%s(%d, %d)\tGOT: %d\tExpected: %d\033[0;0m\n", name, x, y, result, expected);
}