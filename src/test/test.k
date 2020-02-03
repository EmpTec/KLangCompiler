function add(x: int, y: int): int {
  return (x + y);
}

function sub (x: int, y: int): int {
  return (x - y);
}

function mul(x: int, y: int): int {
  return (x * y);
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



add(1, 1); 
