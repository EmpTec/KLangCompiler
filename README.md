# Klang - The Kaiser language
This is the project for Klang - the Kaiser language.

# Authors
This code was in equal parts developed by `Dennis Kaiser` and `Marvin Kaiser` at the RheinMain University of Applied Sciences for the Compilers course. 

# Usage
example call to print help `java -cp target/klang-1.0-jar-with-dependencies.jar de.hsrm.compiler.Klang.Klang -h`
example call: `java -cp <jar> <cp> -out <input> <output>`
Arguments:
- -out <file>   File to write to
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
- `make testJava` runs JUnit tests
- `make cleanTests` cleans files generated from tests

# Boilerplate Example
A simple program in the KLang Language consits of some struct definitions and some function definition and a single expression that is used as the start for the compilation
```
struct node {
  value: int;
  tail: node;
}

function makeList(anz: int): node {
  if (anz == 0) {
    return naught;
  }
  return create node(anz - 1, makeList(anz - 1));
}

function get(ll: node, index: int): int {
  if (index == 0 || ll == naught) {
    return ll.value;
  } else {
    return get(ll.tail, index - 1);
  }
}

function sum(list: node, length: int): int {
  if (length == 0) {
    return list.value;
  }
  return list.value + sum(list.tail, length -1);
}

function init(pos: int): int {
  let n: node = makeList(5);
  return sum(n, pos);
}

init(0);
```

# Functionality
The KLang compiler supports generation of AMD64 assembly code, as well as prettifying and evaluating the KLang code.

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
5 + 4
8 % 2
8 == 0
```

## Functions
Functions can be defined and called. A function call can be used like any other expression. Recursion is supported aswell as linking agaings c object files since we are following the calling convention

### Examples
```
function fun(x: int, y: int, z: bool): int {
  return x;
}

fun(1, 2, 3);
```

## Structs
Structs can be defined, created and destroyed. Structs can reference other structs as well as themselves. You can reference structs that are defined later in the code. Our structs are compatible to c structs. When defining a struct, a constructor function is implicitly defined so that you can create instances of your struct. To denote a non existing reference to a struct, use the reserved word "naught";

### Examples
```
struct myStruct {
  a: int;
  b: bool;
  c: float;
  d: myStruct;
}

function add(x: myStruct, y: myStruct): float {
  return x.c + y.c;
}

function isOk(x: myStruct, y: myStruct): bool {
  return x.b && y.b;
}

function getReferenced(x: myStruct): myStruct {
  return x.d;
}

function start(): int {
  let x: myStruct = create myStruct(1, false, 42.0, naught);
  let y: myStruct = create myStruct(12, true, 13.37, x);
  let z: int = add(x, y);
  let a: bool = isOk(x, y);
  let y2: myStruct = getReferenced(x);
  let isSame: bool = y == y2;
  destroy y;
  destroy x;

  return 0;
}

start();
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
function example(x: int, y: int, z: int): int {
  let a: int;
  let b: int = 0;
  if (x == y) {
    a = y;
  } else if (x == z) {
    a = z;
  } else {
    return b;
  } 
  return a;
}

function whileExample(end: int): int {
  let x: int = 0;
  while (x < end) {
    x = x + 1;
  }
  return x;
}

function doWhileExample(end: int): int {
  let x: int = 0;
  do {
    x = x + 1;
  } while(x < end);
  return x;
}

function forExample(end: int): int {
  let x: int = 0;
  for (let i: int = 0; i < end; i = i + 1) {
    x = x + 1;
  }
  return x;
}

```

## Tail Call Optimized
Recursive tail calls are optimized at compile time.

## Statically typed
KLang statically verifies the integrity of your code. These checks include:
- Type checking
- Ensuring that variables and functions in use are declared
- Ensuring that the arguments of a function call match the function definition
- Ensuring that a function returns something
- Ensuring that a function only returns data of the correct type

### Primitive Data Types
- Integer "int"
- Boolean "bool"
- Floats  "float"

### Examples
You can declare types for parameters, return values and variables
```
function foo(start: int): boolean {
  let threshold: int = 10;
  return threshold < start;
}
```

Type annotations are required as per our parsing rules, so this will result in an error while parsing
```
function bar() {
  return 0;
}
```

This will throw an error since a boolean is returned, but int is declared as the return type
```
function baz(): int {
  return false;
}
```

This will throw an error since the function "bam" expects one argument but the call to this function provided none
```
function bam(a: int): int {
  return a;
}
bam();
```

This will throw an error since the first parameter of function "boo" has to be of type bool
```
function boo(a: bool): bool {
  return a;
}
boo(100);
```
