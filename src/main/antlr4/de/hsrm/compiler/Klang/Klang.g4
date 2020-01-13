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
  | variable_declaration
  | variable_assignment
  | return_statement
  | whileLoop
  | doWhileLoop
  ;

print
  : PRINT expression SCOL
  ;

if_statement
  : IF OPAR cond = expression CPAR then = braced_block (ELSE (alt = braced_block | elif = if_statement) )?
  ; 

variable_declaration
  : LET IDENT (EQUAL expression)? SCOL
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
  | OPAR lhs=expression EQEQ rhs=expression CPAR #equalityExpression
  | OPAR lhs=expression NEQ rhs=expression CPAR #NotEqualityExpression
  | OPAR lhs=expression LT rhs=expression CPAR #lessThanExpression
  | OPAR lhs=expression GT rhs=expression CPAR #greaterThanExpression
  | OPAR lhs=expression LTE rhs=expression CPAR #lessThanOrEqualToExpression
  | OPAR lhs=expression GTE rhs=expression CPAR #GreaterThanOrEqualToExpression
  
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

whileLoop
  : WHILE OPAR cond = expression CPAR braced_block
  ;

doWhileLoop
  : DO braced_block WHILE OPAR cond = expression CPAR SCOL
  ;

PRINT: 'print';
IF: 'if';
ELSE: 'else';
FUNC: 'function';
RETURN: 'return';
LET: 'let';
WHILE: 'while';
DO: 'do';

SCOL: ';';
OBRK: '{';
CBRK: '}';
OPAR: '(';
CPAR: ')';
COMMA: ',';
EQUAL: '=';
EQEQ: '==';
NEQ: '!=';
LT: '<';
GT: '>';
LTE: '<=';
GTE: '>=';

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
