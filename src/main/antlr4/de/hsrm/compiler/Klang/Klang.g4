grammar Klang;

parse
  : program <EOF>
  ;

program
  : (functionDef | structDef)* expression SCOL
  ;

structDef
  : STRUCT structName=IDENT OBRK structField+ CBRK
  ;

structField
  : IDENT type_annotation SCOL
  ;

functionDef
  : FUNC funcName=IDENT params=parameter_list returnType=type_annotation braced_block
  ;

parameter_list
  : OPAR (parameter (COMMA parameter)*)? CPAR
  ;

parameter
  : IDENT type_annotation
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
  : LET IDENT type_annotation (EQUAL expression)?
  ;

variable_assignment
  : IDENT EQUAL expression
  ;

return_statement
  : RETURN expression SCOL
  ;

expression
  : atom #atomExpression
  | IDENT (DOT IDENT)+ #structFieldAccessExpression
  | OPAR expression CPAR #parenthesisExpression
  | lhs=expression MUL rhs=expression #multiplicationExpression
  | lhs=expression DIV rhs=expression #divisionExpression
  | lhs=expression MOD rhs=expression #moduloExpression
  | lhs=expression ADD rhs=expression #additionExpression
  | lhs=expression SUB rhs=expression #substractionExpression
  | lhs=expression EQEQ rhs=expression #equalityExpression
  | lhs=expression NEQ rhs=expression #NotEqualityExpression
  | lhs=expression LT rhs=expression #lessThanExpression
  | lhs=expression GT rhs=expression #greaterThanExpression
  | lhs=expression LTE rhs=expression #lessThanOrEqualToExpression
  | lhs=expression GTE rhs=expression #GreaterThanOrEqualToExpression
  | lhs=expression OR rhs=expression #OrExpression
  | lhs=expression AND rhs=expression #AndExpression
  | SUB expression #negateExpression
  | NOT expression #NotExpression
  | functionCall #functionCallExpression
  | CREATE IDENT OPAR arguments CPAR # constructorCallExpression
  ;

atom
  : INTEGER_LITERAL #intAtom
  | BOOLEAN_LITERAL #boolAtom
  | FLOAT_LITERAL #floatAtom
  | NULL # nullAtom
  | IDENT #variable
  ;

type_annotation
  : COL type
  ;

type
  : INTEGER
  | BOOLEAN
  | FLOAT
  | IDENT
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
STRUCT: 'struct';
RETURN: 'return';
LET: 'let';
WHILE: 'while';
DO: 'do';
FOR: 'for';
CREATE: 'create';
NULL: 'naught';

PERIOD: '.';
COL: ':';
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
DOT: '.';

MUL: '*';
ADD: '+';
SUB: '-';
MOD: '%';
DIV: '/';

BOOLEAN: 'bool';
INTEGER: 'int';
FLOAT: 'float';

INTEGER_LITERAL
  : [0-9]+
  ;

FLOAT_LITERAL
  : INTEGER_LITERAL PERIOD INTEGER_LITERAL
  ;

BOOLEAN_LITERAL
  : 'true'
  | 'false'
  ;

IDENT
  : [a-zA-Z][a-zA-Z0-9]*
  ;

BLOCK_COMMENT
	: '/*' .*? '*/' -> skip
	;

LINE_COMMENT
	: '//' ~[\r\n]* -> skip
	;

WS
 : [ \t\r\n] -> skip
 ;
