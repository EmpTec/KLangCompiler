#include <stdbool.h>

void incSuccess();
void incFailure();

void succ(char* name, int expected, int result);
void err(char* name, int expected, int result);

void succPrefixOne(char* name, int x,  int expected, int result);
void errPrefixOne(char* name, int x,  int expected, int result);

void succPrefixTwo(char* name, int x,  int y, int expected, int result);
void errPrefixTwo(char* name, int x,  int y, int expected, int result);

void succInfixTwo(char* name, int x, int y, int expected, int result);
void errInfixTwo(char* name, int x, int y, int expected, int result);

void bool_succPrefixOne(char* name, bool x, bool expected, bool result);
void bool_errPrefixOne(char* name, bool x, bool expected, bool result);

void bool_succInfixTwo(char* name, bool x, bool y, bool expected, bool result);
void bool_errInfixTwo(char* name, bool x, bool y, bool expected, bool result);
