#include <stdio.h>
#include <stdlib.h>
#include "comparison/comparison.h"
#include "functionCall/functionCall.h"
#include "loop/loop.h"
#include "math/math.h"
#include "recursive/recursive.h"
#include "struct/struct.h"

int successes, failures = 0;

void incSuccess() {
  successes += 1;
}

void incFailure() {
  failures += 1;
}

int main(){
  // Tests for various math related functions
  runMathTests();

  // Tests for passing arguments to functions
  runFunctionCallTests();

  // Tests for recursive funtions
  runRecursiveTests();

  // Tests for comparison expressions
  runComparisonTests();

  // Tests for while loop
  runLoopTests();

  // Tests for structs
  runStructTests();

  printf("\n%d tests in total\n", successes + failures);

  if (failures > 0) {
    printf("\033[0;31m%d tests were successful\033[0;0m\n", successes);
    printf("\033[0;31m%d tests failed\033[0;0m\n", failures);
    return EXIT_FAILURE;
  } else {
    printf("\033[0;32m%d tests were successful\033[0;0m\n", successes);
    printf("\033[0;32m%d tests failed\033[0;0m\n", failures);
    return EXIT_SUCCESS;
  }
}
  