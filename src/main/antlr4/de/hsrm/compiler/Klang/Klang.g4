grammar Klang;

parse
  : block <EOF>
  ;

block
  : statement+
  ;

braced_block
  : OBRK statement+ CBRK
  ;

statement
  : print
  | if_statement
  ;

print
  : PRINT expression SCOL
  ;

if_statement
  : IF OPAR cond = expression CPAR then = braced_block (ELSE (alt = braced_block | elif = if_statement) )?
  ;  

expression
  : atom MULT atom #multiplicationExpression
  | atom op=(ADD | SUB) atom #additiveExpression
  | atom MOD atom #moduloExpression
  | SUB atom #unaryNegateExpression
  | atom #atomExpression
  ;

atom
  : INTEGER_LITERAL #intAtom
  ;

PRINT: 'print';
IF: 'if';
ELSE: 'else';

SCOL: ';';
OBRK: '{';
CBRK: '}';
OPAR: '(';
CPAR: ')';

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
