grammar Klang;

parse
  : multiplicativeExpr <EOF>
  ;

multiplicativeExpr
  : unaryExpression (MULT unaryExpression)*  
  ;

unaryExpression
  : INTEGER_LITERAL
  ;

INTEGER_LITERAL
  : [0-9]+
  ;

MULT
  : '*'
  ;

WS
 : [ \t\r\n] -> skip
 ;
