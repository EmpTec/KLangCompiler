grammar Klang;

parse
  : program <EOF>
  ;

program
  : functionDef* expression
  ;

functionDef
  : FUNC funcName=IDENT OPAR parameters CPAR braced_block
  ;

parameters
  : (IDENT (COMMA IDENT)*)?
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
  | functionCall #functionCallExpression
  ;

functionCall
  : IDENT OPAR arguments CPAR SCOL
  ;

arguments
  : (expression (COMMA expression)*)?
  ;

atom
  : INTEGER_LITERAL #intAtom
  | IDENT # variable
  ;

PRINT: 'print';
IF: 'if';
ELSE: 'else';
FUNC: 'function';

SCOL: ';';
OBRK: '{';
CBRK: '}';
OPAR: '(';
CPAR: ')';
COMMA: ',';

MULT: '*';
ADD: '+';
SUB: '-';
MOD: '%';

INTEGER_LITERAL
  : [0-9]+
  ;

IDENT
  : [a-zA-Z][a-zA-Z0-9]*
  ;

WS
 : [ \t\r\n] -> skip
 ;
