grammar Klang;

parse
  : block <EOF>
  ;

block
  : statement*
  ;

statement
  : print
  ;

print
  : PRINT expression SCOL
  ;

expression
  : atom MULT atom #multiplicationExpression
  | atom op=(ADD | SUB) atom #additiveExpression
  | atom MOD atom #moduloExpression
  | SUB atom #unaryNegateExpression
  ;

atom
  : INTEGER_LITERAL #intAtom
  ;

PRINT: 'print';
SCOL: ';';

MULT: '*';
ADD: '+';
SUB: '-';
MOD: '%';

INTEGER_LITERAL
  : [0-9]+
  ;

WS
 : [ \t\r\n] -> skip
 ;
