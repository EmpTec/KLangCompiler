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


// Only the first child of a rule alternative will be visited!
// i.e. SCOL won't be visited, but thats unneccesary anyway
statement
  : print
  | if_statement
  | variable_declaration SCOL
  | variable_assignment SCOL
  | return_statement
  | whileLoop
  | doWhileLoop
  | forLoop
  ;

print
  : PRINT expression SCOL
  ;

if_statement
  : IF OPAR cond = expression CPAR then = braced_block (ELSE (alt = braced_block | elif = if_statement) )?
  ; 

variableDeclarationOrAssignment
  : variable_assignment
  | variable_declaration
  ;

variable_declaration
  : LET IDENT (EQUAL expression)?
  ;

variable_assignment
  : IDENT EQUAL expression
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
  | OPAR lhs=expression OR rhs=expression CPAR #OrExpression
  | OPAR lhs=expression AND rhs=expression CPAR #AndExpression
  | SUB expression #negateExpression
  | NOT expression #NotExpression
  | functionCall #functionCallExpression
  ;

atom
  : INTEGER_LITERAL #intAtom
  | BOOLEAN_LITERAL #boolAtom
  | IDENT #variable
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

forLoop
  : FOR OPAR init = variableDeclarationOrAssignment SCOL
             cond = expression SCOL 
             step = variable_assignment CPAR braced_block
  ;

PRINT: 'print';
IF: 'if';
ELSE: 'else';
FUNC: 'function';
RETURN: 'return';
LET: 'let';
WHILE: 'while';
DO: 'do';
FOR: 'for';

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
OR: '||';
AND: '&&';
NOT: '!';

MUL: '*';
ADD: '+';
SUB: '-';
MOD: '%';
DIV: '/';

INTEGER_LITERAL
  : [0-9]+
  ;

BOOLEAN_LITERAL
  : 'true'
  | 'false'
  ;

IDENT
  : [a-zA-Z][a-zA-Z0-9]*
  ;

WS
 : [ \t\r\n] -> skip
 ;
