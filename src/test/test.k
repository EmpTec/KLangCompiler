function add(x: int, y: int): int {
  return (x + y);
}

function sub (x: int, y: int): int {
  return (x - y);
}

function mul(x: int, y: int): int {
  return (x * y);
}

function div(x: int, y: int): int {
  return (x / y);
}

function modulo(x: int, y: int): int {
  return (x % y);
}

function neg(x: int): int {
  return -x;
}

function id(x: int): int {
  return x;
}

function arg1(a: int, b: int, c: int, d: int, e: int, f: int, g: int, h: int, i: int ,j: int): int {
  return a;
}

function get1(): int {
  return arg1(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
}

function arg2(a: int, b: int, c: int, d: int, e: int, f: int, g: int, h: int, i: int ,j: int): int {
  return b;
}

function get2(): int {
  return arg2(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
}

function arg3(a: int, b: int, c: int, d: int, e: int, f: int, g: int, h: int, i: int ,j: int): int {
  return c;
}

function get3(): int {
  return arg3(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
}

function arg4(a: int, b: int, c: int, d: int, e: int, f: int, g: int, h: int, i: int ,j: int): int {
  return d;
}

function get4(): int {
  return arg4(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
}

function arg5(a: int, b: int, c: int, d: int, e: int, f: int, g: int, h: int, i: int ,j: int): int {
  return e;
}

function get5(): int {
  return arg5(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
}

function arg6(a: int, b: int, c: int, d: int, e: int, f: int, g: int, h: int, i: int ,j: int): int {
  return f;
}

function get6(): int {
  return arg6(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
}

function arg7(a: int, b: int, c: int, d: int, e: int, f: int, g: int, h: int, i: int ,j: int): int {
  return g;
}

function get7(): int {
  return arg7(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
}

function arg8(a: int, b: int, c: int, d: int, e: int, f: int, g: int, h: int, i: int ,j: int): int {
  return h;
}

function get8(): int {
  return arg8(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
}

function arg9(a: int, b: int, c: int, d: int, e: int, f: int, g: int, h: int, i: int ,j: int): int {
  return i;
}

function get9(): int {
  return arg9(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
}

function arg10(a: int, b: int, c: int, d: int, e: int, f: int, g: int, h: int, i: int ,j: int): int {
  return j;
}

function get10(): int {
  return arg10(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
}

// TAIL CALL
function arg1Tail(a: int, b: int, c: int,d: int,e: int,f: int,g: int, h: int,i: int,j: int, count: int): int {
  if (count <= 0) {
    return a;
  }

  return arg1Tail(a, b, c, d, e, f, g, h, i, j, count - 1);
}

function get1Tail(count: int): int {
  return arg1Tail(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 10);
}

function arg2Tail(a: int, b: int, c: int,d: int,e: int,f: int,g: int, h: int,i: int,j: int, count: int): int {
  if (count <= 0) {
    return b;
  }

  return arg2Tail(a, b, c, d, e, f, g, h, i, j, count - 1);
}

function get2Tail(count: int): int {
  return arg2Tail(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 10);
}

function arg3Tail(a: int, b: int, c: int,d: int,e: int,f: int,g: int, h: int,i: int,j: int, count: int): int {
  if (count <= 0) {
    return c;
  }

  return arg3Tail(a, b, c, d, e, f, g, h, i, j, count - 1);
}

function get3Tail(count: int): int {
  return arg3Tail(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 10);
}

function arg4Tail(a: int, b: int, c: int,d: int,e: int,f: int,g: int, h: int,i: int,j: int, count: int): int {
  if (count <= 0) {
    return d;
  }

  return arg4Tail(a, b, c, d, e, f, g, h, i, j, count - 1);
}

function get4Tail(count: int): int {
  return arg4Tail(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 10);
}

function arg5Tail(a: int, b: int, c: int,d: int,e: int,f: int,g: int, h: int,i: int,j: int, count: int): int {
  if (count <= 0) {
    return e;
  }

  return arg5Tail(a, b, c, d, e, f, g, h, i, j, count - 1);
}

function get5Tail(count: int): int {
  return arg5Tail(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 10);
}

function arg6Tail(a: int, b: int, c: int,d: int,e: int,f: int,g: int, h: int,i: int,j: int, count: int): int {
  if (count <= 0) {
    return f;
  }

  return arg6Tail(a, b, c, d, e, f, g, h, i, j, count - 1);
}

function get6Tail(count: int): int {
  return arg6Tail(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 10);
}

function arg7Tail(a: int, b: int, c: int,d: int,e: int,f: int,g: int, h: int,i: int,j: int, count: int): int {
  if (count <= 0) {
    return g;
  }

  return arg7Tail(a, b, c, d, e, f, g, h, i, j, count - 1);
}

function get7Tail(count: int): int {
  return arg7Tail(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 10);
}

function arg8Tail(a: int, b: int, c: int,d: int,e: int,f: int,g: int, h: int,i: int,j: int, count: int): int {
  if (count <= 0) {
    return h;
  }

  return arg8Tail(a, b, c, d, e, f, g, h, i, j, count - 1);
}

function get8Tail(count: int): int {
  return arg8Tail(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 10);
}

function arg9Tail(a: int, b: int, c: int,d: int,e: int,f: int,g: int, h: int,i: int,j: int, count: int): int {
  if (count <= 0) {
    return i;
  }

  return arg9Tail(a, b, c, d, e, f, g, h, i, j, count - 1);
}

function get9Tail(count: int): int {
  return arg9Tail(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 10);
}

function arg10Tail(a: int, b: int, c: int, d: int, e: int, f: int, g: int, h: int, i: int, j: int, count: int): int {
  if (count <= 0) {
    return j;
  }

  return arg10Tail(a, b, c, d, e, f, g, h, i, j, count - 1);
}

function get10Tail(count: int): int {
  return arg10Tail(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 10);
}

// FLOATS

function farg1(a: float, b: float, c: float, d: float, e: float, f: float, g: float, h: float, i: float ,j: float): float {
  return a;
}

function fget1(): float {
  return farg1(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0);
}

function farg2(a: float, b: float, c: float, d: float, e: float, f: float, g: float, h: float, i: float ,j: float): float {
  return b;
}

function fget2(): float {
  return farg2(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0);
}

function farg3(a: float, b: float, c: float, d: float, e: float, f: float, g: float, h: float, i: float ,j: float): float {
  return c;
}

function fget3(): float {
  return farg3(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0);
}

function farg4(a: float, b: float, c: float, d: float, e: float, f: float, g: float, h: float, i: float ,j: float): float {
  return d;
}

function fget4(): float {
  return farg4(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0);
}

function farg5(a: float, b: float, c: float, d: float, e: float, f: float, g: float, h: float, i: float ,j: float): float {
  return e;
}

function fget5(): float {
  return farg5(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0);
}

function farg6(a: float, b: float, c: float, d: float, e: float, f: float, g: float, h: float, i: float ,j: float): float {
  return f;
}

function fget6(): float {
  return farg6(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0);
}

function farg7(a: float, b: float, c: float, d: float, e: float, f: float, g: float, h: float, i: float ,j: float): float {
  return g;
}

function fget7(): float {
  return farg7(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0);
}

function farg8(a: float, b: float, c: float, d: float, e: float, f: float, g: float, h: float, i: float ,j: float): float {
  return h;
}

function fget8(): float {
  return farg8(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0);
}

function farg9(a: float, b: float, c: float, d: float, e: float, f: float, g: float, h: float, i: float ,j: float): float {
  return i;
}

function fget9(): float {
  return farg9(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0);
}

function farg10(a: float, b: float, c: float, d: float, e: float, f: float, g: float, h: float, i: float ,j: float): float {
  return j;
}

function fget10(): float {
  return farg10(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0);
}

function fargMix1(a: float, b: int, c: float, d: int, e: float, f: int, g: float, h: int, i: float ,j: int): float {
  return a;
}

function fgetMix1(): float {
  return fargMix1(1.0, 2, 3.0, 4, 5.0, 6, 7.0, 8, 9.0, 10);
}

function fargMix2(a: float, b: int, c: float, d: int, e: float, f: int, g: float, h: int, i: float ,j: int): int {
  return b;
}

function fgetMix2(): int {
  return fargMix2(1.0, 2, 3.0, 4, 5.0, 6, 7.0, 8, 9.0, 10);
}

function fargMix3(a: float, b: int, c: float, d: int, e: float, f: int, g: float, h: int, i: float ,j: int): float {
  return c;
}

function fgetMix3(): float {
  return fargMix3(1.0, 2, 3.0, 4, 5.0, 6, 7.0, 8, 9.0, 10);
}

function fargMix4(a: float, b: int, c: float, d: int, e: float, f: int, g: float, h: int, i: float ,j: int): int {
  return d;
}

function fgetMix4(): int {
  return fargMix4(1.0, 2, 3.0, 4, 5.0, 6, 7.0, 8, 9.0, 10);
}

function fargMix5(a: float, b: int, c: float, d: int, e: float, f: int, g: float, h: int, i: float ,j: int): float {
  return e;
}

function fgetMix5(): float {
  return fargMix5(1.0, 2, 3.0, 4, 5.0, 6, 7.0, 8, 9.0, 10);
}

function fargMix6(a: float, b: int, c: float, d: int, e: float, f: int, g: float, h: int, i: float ,j: int): int {
  return f;
}

function fgetMix6(): int {
  return fargMix6(1.0, 2, 3.0, 4, 5.0, 6, 7.0, 8, 9.0, 10);
}

function fargMix7(a: float, b: int, c: float, d: int, e: float, f: int, g: float, h: int, i: float ,j: int): float {
  return g;
}

function fgetMix7(): float {
  return fargMix7(1.0, 2, 3.0, 4, 5.0, 6, 7.0, 8, 9.0, 10);
}

function fargMix8(a: float, b: int, c: float, d: int, e: float, f: int, g: float, h: int, i: float ,j: int): int {
  return h;
}

function fgetMix8(): int {
  return fargMix8(1.0, 2, 3.0, 4, 5.0, 6, 7.0, 8, 9.0, 10);
}

function fargMix9(a: float, b: int, c: float, d: int, e: float, f: int, g: float, h: int, i: float ,j: int): float {
  return i;
}

function fgetMix9(): float {
  return fargMix9(1.0, 2, 3.0, 4, 5.0, 6, 7.0, 8, 9.0, 10);
}

function fargMix10(a: float, b: int, c: float, d: int, e: float, f: int, g: float, h: int, i: float ,j: int): int {
  return j;
}

function fgetMix10(): int {
  return fargMix10(1.0, 2, 3.0, 4, 5.0, 6, 7.0, 8, 9.0, 10);
}

function fadd(x: float, y: float): float {
  return x + y;
}

function fsub(x: float, y: float): float {
  return x - y;
}

function fmul(x: float, y: float): float {
  return x * y;
}

function fneg(x: float): float {
  return -x;
}

function fid(x: float): float {
  return x;
}

function fselfMinus(x: float): float {
   x = (x - 1);
  return x;
}

// END FLOATS

function fac(x: int): int {
  if (x) {
    return (x * fac((x - 1)));
  }
  return 1;
}

function eq(x: int, y: int): bool {
  return (x == y);
}

function neq(x: int, y: int): bool {
  return (x != y);
}

function lt(x: int, y: int): bool {
  return (x < y);
}

function lte(x: int, y: int): bool {
  return (x <= y);
}

function gt(x: int, y: int): bool {
  return (x > y);
}

function gte(x: int, y: int): bool {
  return (x >= y);
}

function selfMinus(x: int): int {
  x = (x - 1);
  return x;
}

function myWhile(end: int): int {
  let cond: int = 0;
  while ((cond < end)) {
    cond = (cond + 1);
  } 
  return cond;
}

function myDoWhile(end: int): int {
  let cond: int = 0;
  do {
    cond = (cond + 1);
  } while((cond < end));
  return cond;
}

function myFor(end: int): int {
  let x: int = 0;
  for (let i: int = 0; (i < end); i = (i + 1)) {
    x = (x + 1);
  }
  return x;
}

function and(a: bool, b: bool): bool {
  return (a && b);
}

function or(a: bool, b: bool): bool {
  return (a || b);
}

function not(a: bool): bool {
  return !a;
}

function t1(): int {
  return ((5 * 3) + 1);
}

function t2(): int {
  return (1 + (5 * 3));
}

function t3(): int {
  return 5 * 3 + 1;
}

function t4(): int {
  return 1 + 5 * 3;
}

function t5(): int {
  return (5 * 3 + 1);
}

function t6(): int {
  return (1 + 5 * 3);
}

function t7(): int {
  return 1 + (5 * 3);
}

function t8(): int {
  return (1 + 5) * 3;
}

function mixadd(x: float, y: int): float {
  return x + y;
}

function mixsub(x: float, y: int): float {
  return x - y;
}

function mixmul(x: float, y: int): float {
  return x * y;
}

function mixdiv(x: float, y: int): float {
  return x / y;
}

struct testStruct {
    a: int;
    b: bool;
    c: float;
}

struct testStructRec {
    a: int;
    b: testStructRec;
}

function getTestStruct(a: int, b: bool, c: float): testStruct {
  return create testStruct(a, b, c);
}

function getTestStructRec(a: int, b: testStructRec): testStructRec {
  return create testStructRec(a, b);
}

function getStructFieldA(t: testStruct): int {
  return t.a;
}

function getStructFieldB(t: testStruct): bool {
  return t.b;
}

function getStructFieldC(t: testStruct): float {
  return t.c;
}

function setStructFieldA(t: testStruct, a: int): testStruct {
  t.a = a;
  return t;
}

function setStructFieldB(t: testStruct, b: bool): testStruct {
  t.b = b;
  return t;
}

function setStructFieldC(t: testStruct, c: float): testStruct {
  t.c = c;
  return t;
}

function getStructFieldRecA(t: testStructRec): int {
  return t.a;
}

function getStructFieldRecB(t: testStructRec): testStructRec {
  return t.b;
}

function setStructFieldRecA(t: testStructRec, a: int): testStructRec {
  t.a = a;
  return t;
}

function setStructFieldRecB(t: testStructRec, b: testStructRec): testStructRec {
  t.b = b;
  return t;
}

add(1, 1); 
