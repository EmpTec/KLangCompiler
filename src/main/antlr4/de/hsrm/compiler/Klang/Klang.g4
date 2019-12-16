grammar Klang;

parse
  : program <EOF>
  ;

program
  : functionDef* expression SCOL
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
  | variable_assignment
  | return_statement
  ;

print
  : PRINT expression SCOL
  ;

if_statement
  : IF OPAR cond = expression CPAR then = braced_block (ELSE (alt = braced_block | elif = if_statement) )?
  ;  

variable_assignment
  : IDENT EQUAL expression SCOL
  ;

return_statement
  : RETURN expression SCOL
  ;

expression
  : atom #atomExpression
  | OPAR lhs=expression ADD rhs=expression CPAR #additionExpression
  | OPAR lhs=expression SUB rhs=expression CPAR #substractionExpression
  | OPAR lhs=expression MUL rhs=expression CPAR #multiplicationExpression
  | OPAR lhs=expression DIV rhs=expression CPAR #divisionExpression
  | OPAR lhs=expression MOD rhs=expression CPAR #moduloExpression
  | SUB expression #negateExpression
  | functionCall #functionCallExpression
  ;

atom
  : INTEGER_LITERAL #intAtom
  | IDENT # variable
  ;

functionCall
  : IDENT OPAR arguments CPAR
  ;

arguments
  : (expression (COMMA expression)*)?
  ;

PRINT: 'print';
IF: 'if';
ELSE: 'else';
FUNC: 'function';
RETURN: 'return';

SCOL: ';';
OBRK: '{';
CBRK: '}';
OPAR: '(';
CPAR: ')';
COMMA: ',';
EQUAL: '=';

MUL: '*';
ADD: '+';
SUB: '-';
MOD: '%';
DIV: '/';

INTEGER_LITERAL
  : [0-9]+
  ;

IDENT
  : [a-zA-Z][a-zA-Z0-9]*
  ;

WS
 : [ \t\r\n] -> skip
 ;
