# Klang - The Kaiser language
This is the project for Klang - the Kaiser language.

# Authors
This code was in equal parts developed by `Dennis Kaiser` and `Marvin Kaiser` at the RheinMain University of Applied Sciences for the Compilers course. 

# Usage
Pass source code via stdin

example call to print help `java -cp target/klang-1.0-jar-with-dependencies.jar de.hsrm.compiler.Klang.Klang -h`

Arguments:
- -h               Print this help
- --evaluate:      Evaluates the given source code
- --pretty:        Pretty print the given source code
- --no-compile:    Do not compile the source code
- --no-main:       Do not generate main function, will be generated as 'start'. Useful for testing

The makefile can be used to perform various functions more easily:
- `make build` simply builds the compiler
- `make clean` cleans up all generated output
- `make` generates code.s from code.k in root folder
- `make pretty` prettifies code.k and writes to pretty.k
- `make eval` evaluates code.k
- `make test` runs tests from src/test/
- `make cleanTests` cleans files generated from tests

# Functionality
The KLang compiler supports generation of AMD64 assembly code, as well as prettifying and evaluating the KLang code. 

## Data Types
- Integer

## Simple Expressions
The following simple expressions are supported. Expressions need to be put in paranthesis. When using comparison operators, the expressions evaluate to 0 for false and 1 for true.
- Addition (+)
- Subtraction (-)
- Multiplication (*)
- Division (/)
- Modulo (%)
- Equality (==)
- Less Than (<)
- Less Than Or Equal (<=)
- Greater Than (>)
- Greater Than Or Equal (>=)
- Number Negation (-)

### Examples:
```
(5 + 4)
(8 % 2)
(8 == 0)
```

## Functions
Functions can be defined and called. A function call can be used like any other expression. Recusion is supported

### Examples
```
function fun(x, y, z) {
  return x;
}

fun(1, 2, 3);
```
## Statements
Several statements are supported:
- if
- variable declaration
- variable assignment
- return
- while
- do while
- for

### Examples
```
function example(x, y, z) {
  let a;
  let b = 0;
  if ((x == y)) {
    a = y;
  } else if ((x == z)) {
    a = z;
  } else {
    return b;
  } 
  return a;
}

function whileExample(end) {
  let x = 0;
  while ((x < end)) {
    x = (x + 1);
  }
  return x;
}

function doWhileExample(end) {
  let x = 0;
  do {
    x = (x + 1);
  } while((x < end));
}

function forExample(end) {
  let x = 0;
  for (let i = 0; (i < end); i = (i + 1)) {
    x = (x + 1);
  }
  return x;
}

```

