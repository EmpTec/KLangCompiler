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
  : IF expression braced_block (ELSE braced_block)?
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
