#include <stdio.h>
#include <stdlib.h>
#include "test.h"

int main(){
  int failed = 0;

  // Tests for various math related functions
  failed += runMathTests();

  // Tests for passing arguments to functions
  failed += runFunctionCallTests();

  // Tests for recursive funtions
  failed += runRecursiveTests();

  // Tests for comparison expressions
  failed += runComparisonTests();

  // Tests for while loop
  failed += runLoopTests();

  printf("\n=== Failed Tests: %d\n", failed);

  if (failed > 0) {
    return EXIT_FAILURE;
  } else {
    return EXIT_SUCCESS;
  }
}
  