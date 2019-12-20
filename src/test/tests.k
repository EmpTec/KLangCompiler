function add(x, y) {
  return (x + y);
}

function sub (x, y) {
  return (x - y);
}

function mul(x, y) {
  return (x * y);
}

function modulo(x, y) {
  return (x % y);
}

function neg(x) {
  return -x;
}

function id(x) {
  return x;
}

function arg1(a, b, c, d, e, f, g, h, i ,j) {
  return a;
}

function get1() {
  return arg1(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
}

function arg2(a, b, c, d, e, f, g, h, i ,j) {
  return b;
}

function get2() {
  return arg2(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
}

function arg3(a, b, c, d, e, f, g, h, i ,j) {
  return c;
}

function get3() {
  return arg3(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
}

function arg4(a, b, c, d, e, f, g, h, i ,j) {
  return d;
}

function get4() {
  return arg4(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
}

function arg5(a, b, c, d, e, f, g, h, i ,j) {
  return e;
}

function get5() {
  return arg5(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
}

function arg6(a, b, c, d, e, f, g, h, i ,j) {
  return f;
}

function get6() {
  return arg6(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
}

function arg7(a, b, c, d, e, f, g, h, i ,j) {
  return g;
}

function get7() {
  return arg7(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
}

function arg8(a, b, c, d, e, f, g, h, i ,j) {
  return h;
}

function get8() {
  return arg8(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
}

function arg9(a, b, c, d, e, f, g, h, i ,j) {
  return i;
}

function get9() {
  return arg9(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
}

function arg10(a, b, c, d, e, f, g, h, i ,j) {
  return j;
}

function get10() {
  return arg10(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
}

function fac(x) {
  if (x) {
    return (x * fac((x - 1)));
  }
  return 1;
}

function eq(x, y) {
  return (x == y);
}

function neq(x, y) {
  return (x != y);
}

function lt(x, y) {
  return (x < y);
}

function lte(x, y) {
  return (x <= y);
}

function gt(x, y) {
  return (x > y);
}

function gte(x, y) {
  return (x >= y);
}

add(1, 1);
