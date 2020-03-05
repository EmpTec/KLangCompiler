#include <stdbool.h>

void incSuccess();
void incFailure();

void succ(char* name, long expected, long result);
void err(char* name, long expected, long result);

void succ_f(char* name, double expected, double result);
void err_f(char* name, double expected, double result);

void succPrefixOne(char* name, long x,  long expected, long result);
void errPrefixOne(char* name, long x,  long expected, long result);

void float_succPrefixOne(char* name, double x,  double expected, double result);
void float_errPrefixOne(char* name, double x,  double expected, double result);

void succPrefixTwo(char* name, long x,  long y, long expected, long result);
void errPrefixTwo(char* name, long x,  long y, long expected, long result);

void float_succPrefixTwo(char* name, double x,  double y, double expected, double result);
void float_errPrefixTwo(char* name, double x,  double y, double expected, double result);

void succInfixTwo(char* name, long x, long y, long expected, long result);
void errInfixTwo(char* name, long x, long y, long expected, long result);

void bool_succPrefixOne(char* name, bool x, bool expected, bool result);
void bool_errPrefixOne(char* name, bool x, bool expected, bool result);

void bool_succInfixTwo(char* name, bool x, bool y, bool expected, bool result);
void bool_errInfixTwo(char* name, bool x, bool y, bool expected, bool result);
