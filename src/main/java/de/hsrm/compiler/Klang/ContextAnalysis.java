package de.hsrm.compiler.Klang;

import java.util.Map;
import java.util.HashMap;

import de.hsrm.compiler.Klang.helper.FunctionInformation;
import de.hsrm.compiler.Klang.helper.Helper;
import de.hsrm.compiler.Klang.nodes.*;
import de.hsrm.compiler.Klang.nodes.expressions.*;
import de.hsrm.compiler.Klang.nodes.loops.DoWhileLoop;
import de.hsrm.compiler.Klang.nodes.loops.ForLoop;
import de.hsrm.compiler.Klang.nodes.loops.WhileLoop;
import de.hsrm.compiler.Klang.nodes.statements.*;
import de.hsrm.compiler.Klang.types.Type;

public class ContextAnalysis extends KlangBaseVisitor<Node> {
  Map<String, VariableDeclaration> vars = new HashMap<>();
  Map<String, FunctionInformation> funcs;
  Type currentDeclaredReturnType;

  public ContextAnalysis(Map<String, FunctionInformation> funcs) {
    this.funcs = funcs;
  }

  @Override
  public Node visitProgram(KlangParser.ProgramContext ctx) {
    FunctionDefinition[] funcs = new FunctionDefinition[ctx.functionDef().size()];
    for (int i = 0; i < ctx.functionDef().size(); i++) {
      funcs[i] = (FunctionDefinition) this.visit(ctx.functionDef(i));
    }
    Expression expression = (Expression) this.visit(ctx.expression());
    Program result = new Program(funcs, expression);
    result.type = expression.type;
    result.line = ctx.start.getLine();
    result.col = ctx.start.getCharPositionInLine();
    return result;
  }

  @Override
  public Node visitStatement(KlangParser.StatementContext ctx) {
    // The first child is the proper context we need to visit
    // The second child is either null or just a SCOL!
    return this.visit(ctx.getChild(0));
  }

  @Override
  public Node visitBraced_block(KlangParser.Braced_blockContext ctx) {
    int actualStatementCount = 0;
    int declaredStatementCount = ctx.statement().size();
    boolean hasReturn = false;
    Statement[] statements = new Statement[declaredStatementCount];

    for (int i = 0; i < declaredStatementCount; i++) {
      Node currentStatement = this.visit(ctx.statement(i));
      statements[i] = (Statement) currentStatement;
      actualStatementCount += 1;

      // We use the existance of a type to indicate that this statement returns
      // something for which the VariableDeclaration is an exception
      if (currentStatement.type != null && !(currentStatement instanceof VariableDeclaration)) {
        // check whether the type matches
        try {
          this.currentDeclaredReturnType.combine(currentStatement.type);
        } catch (Exception e) {
          throw new RuntimeException(
              Helper.getErrorPrefix(currentStatement.line, currentStatement.col) + e.getMessage());
        }

        // since we have a return guaranteed, every statement
        // after this one is unreachable code
        hasReturn = true;
        break;
      }
    }

    // If there was unreachable code in this block,
    // create a shorter statements array and copy the statements to there
    if (actualStatementCount < declaredStatementCount) {
      Statement[] newStatements = new Statement[actualStatementCount];
      for (int i = 0; i < actualStatementCount; i++) {
        newStatements[i] = statements[i];
      }
      statements = newStatements;
    }

    // if this block contains at least one statement that guarantees a return value,
    // we indicate that this block guarantees a return value by setting result.type
    Block result = new Block(statements);
    if (hasReturn) {
      result.type = this.currentDeclaredReturnType;
    }

    result.line = ctx.start.getLine();
    result.col = ctx.start.getCharPositionInLine();
    return result;
  }

  @Override
  public Node visitPrint(KlangParser.PrintContext ctx) {
    ctx.start.getLine();
    ctx.start.getCharPositionInLine();
    Node expression = this.visit(ctx.expression());
    PrintStatement result = new PrintStatement((Expression) expression);
    result.type = null;
    result.line = ctx.start.getLine();
    result.col = ctx.start.getCharPositionInLine();
    return result;
  }

  @Override
  public Node visitIf_statement(KlangParser.If_statementContext ctx) {
    Node condition = this.visit(ctx.cond);
    Node thenBlock = this.visit(ctx.then);
    Type type = null;

    IfStatement result;
    if (ctx.alt != null) {
      Node elseBlock = this.visit(ctx.alt);
      result = new IfStatement((Expression) condition, (Block) thenBlock, (Block) elseBlock);
      type = elseBlock.type;
    } else if (ctx.elif != null) {
      Node elif = this.visit(ctx.elif);
      result = new IfStatement((Expression) condition, (Block) thenBlock, (IfStatement) elif);
      type = elif.type;
    } else {
      result = new IfStatement((Expression) condition, (Block) thenBlock);
    }

    if (thenBlock.type != null && type != null) {
      // Since a block verifies that it can combine with the return type of the
      // function it is defined in, we do not have check whether the then and
      // alt block return types match
      result.type = thenBlock.type;
    }

    result.line = ctx.start.getLine();
    result.col = ctx.start.getCharPositionInLine();
    return result;
  }

  @Override
  public Node visitWhileLoop(KlangParser.WhileLoopContext ctx) {
    Node condition = this.visit(ctx.cond);
    Node block = this.visit(ctx.braced_block());
    Node result = new WhileLoop((Expression) condition, (Block) block);
    result.line = ctx.start.getLine();
    result.col = ctx.start.getCharPositionInLine();
    return result;
  }

  @Override
  public Node visitDoWhileLoop(KlangParser.DoWhileLoopContext ctx) {
    Node condition = this.visit(ctx.cond);
    Node block = this.visit(ctx.braced_block());
    Node result = new DoWhileLoop((Expression) condition, (Block) block);
    result.line = ctx.start.getLine();
    result.col = ctx.start.getCharPositionInLine();
    return result;
  }

  @Override
  public Node visitForLoop(KlangParser.ForLoopContext ctx) {
    Node init = this.visit(ctx.init);
    Node condition = this.visit(ctx.cond);
    Node step = this.visit(ctx.step);
    Node block = this.visit(ctx.braced_block());
    Node result = new ForLoop((Statement) init, (Expression) condition, (VariableAssignment) step, (Block) block);
    result.line = ctx.start.getLine();
    result.col = ctx.start.getCharPositionInLine();
    return result;
  }

  @Override
  public Node visitVariable_declaration(KlangParser.Variable_declarationContext ctx) {
    String name = ctx.IDENT().getText();
    int line = ctx.start.getLine();
    int col = ctx.start.getCharPositionInLine();
    Type declaredType = Type.getByName(ctx.type_annotation().type().getText());

    if (this.vars.get(name) != null) {
      String error = "Redeclaration of variable with name \"" + name + "\".";
      throw new RuntimeException(Helper.getErrorPrefix(line, col) + error);
    }

    // Create the appropriate instance
    VariableDeclaration result;
    if (ctx.expression() != null) {
      Node expression = this.visit(ctx.expression());
      try {
        declaredType = declaredType.combine(expression.type);
      } catch (Exception e) {
        throw new RuntimeException(Helper.getErrorPrefix(line, col) + e.getMessage());
      }
      result = new VariableDeclaration(name, (Expression) expression);
      result.type = declaredType; // add the type only if there is an expression
    } else {
      result = new VariableDeclaration(name);
    }

    // Add it to the global map of variable declarations
    this.vars.put(name, result);

    result.line = line;
    result.col = col;
    return result;
  }

  @Override
  public Node visitVariable_assignment(KlangParser.Variable_assignmentContext ctx) {
    String name = ctx.IDENT().getText();
    int line = ctx.start.getLine();
    int col = ctx.start.getCharPositionInLine();

    VariableDeclaration var = this.vars.get(name);
    if (var == null) {
      String error = "Variable with name \"" + name + "\" not defined.";
      throw new RuntimeException(Helper.getErrorPrefix(line, col) + error);
    }

    // Evaluate the expression
    Expression expression = (Expression) this.visit(ctx.expression());

    // Make sure expression can be assigned to the variable
    try {
      expression.type.combine(var.type);
    } catch (Exception e) {
      throw new RuntimeException(Helper.getErrorPrefix(line, col) + e.getMessage());
    }

    // Create a new node and add the type of the expression to it
    Node result = new VariableAssignment(name, expression);
    result.line = line;
    result.col = col;
    return result;
  }

  @Override
  public Node visitReturn_statement(KlangParser.Return_statementContext ctx) {
    Expression expression = (Expression) this.visit(ctx.expression());
    ReturnStatement result = new ReturnStatement(expression);
    result.type = expression.type;
    result.line = ctx.start.getLine();
    result.col = ctx.start.getCharPositionInLine();
    return result;
  }

  @Override
  public Node visitOrExpression(KlangParser.OrExpressionContext ctx) {
    Node lhs = this.visit(ctx.lhs);
    Node rhs = this.visit(ctx.rhs);
    OrExpression result = new OrExpression((Expression) lhs, (Expression) rhs);
    result.type = Type.getBooleanType();
    result.line = ctx.start.getLine();
    result.col = ctx.start.getCharPositionInLine();
    return result;
  }

  @Override
  public Node visitAndExpression(KlangParser.AndExpressionContext ctx) {
    Node lhs = this.visit(ctx.lhs);
    Node rhs = this.visit(ctx.rhs);
    AndExpression result = new AndExpression((Expression) lhs, (Expression) rhs);
    result.type = Type.getBooleanType();
    result.line = ctx.start.getLine();
    result.col = ctx.start.getCharPositionInLine();
    return result;
  }

  @Override
  public Node visitAdditionExpression(KlangParser.AdditionExpressionContext ctx) {
    Node lhs = this.visit(ctx.lhs);
    Node rhs = this.visit(ctx.rhs);
    int line = ctx.start.getLine();
    int col = ctx.start.getCharPositionInLine();
    AdditionExpression result = new AdditionExpression((Expression) lhs, (Expression) rhs);
    try {
      result.type = lhs.type.combine(rhs.type);
    } catch (Exception e) {
      throw new RuntimeException(Helper.getErrorPrefix(line, col) + e.getMessage());
    }
    result.line = ctx.start.getLine();
    result.col = ctx.start.getCharPositionInLine();
    return result;
  }

  @Override
  public Node visitParenthesisExpression(KlangParser.ParenthesisExpressionContext ctx) {
    return this.visit(ctx.expression());
  }

  @Override
  public Node visitEqualityExpression(KlangParser.EqualityExpressionContext ctx) {
    Node lhs = this.visit(ctx.lhs);
    Node rhs = this.visit(ctx.rhs);
    int line = ctx.start.getLine();
    int col = ctx.start.getCharPositionInLine();

    try {
      lhs.type.combine(rhs.type);
    } catch (Exception e) {
      throw new RuntimeException(Helper.getErrorPrefix(line, col) + e.getMessage());
    }

    EqualityExpression result = new EqualityExpression((Expression) lhs, (Expression) rhs);
    result.type = Type.getBooleanType();
    result.line = line;
    result.col = col;
    return result;
  }

  @Override
  public Node visitNotEqualityExpression(KlangParser.NotEqualityExpressionContext ctx) {
    Node lhs = this.visit(ctx.lhs);
    Node rhs = this.visit(ctx.rhs);
    int line = ctx.start.getLine();
    int col = ctx.start.getCharPositionInLine();

    if (lhs.type != Type.getIntegerType() || rhs.type != Type.getIntegerType()) {
      String error = "Both operants of this expression have to be a number.";
      throw new RuntimeException(Helper.getErrorPrefix(line, col) + error);
    }

    NotEqualityExpression result = new NotEqualityExpression((Expression) lhs, (Expression) rhs);
    result.type = Type.getBooleanType();
    result.line = line;
    result.col = col;
    return result;
  }

  @Override
  public Node visitLessThanExpression(KlangParser.LessThanExpressionContext ctx) {
    Node lhs = this.visit(ctx.lhs);
    Node rhs = this.visit(ctx.rhs);
    int line = ctx.start.getLine();
    int col = ctx.start.getCharPositionInLine();

    if (lhs.type != Type.getIntegerType() || rhs.type != Type.getIntegerType()) {
      String error = "Both operants of this expression have to be a number.";
      throw new RuntimeException(Helper.getErrorPrefix(line, col) + error);
    }

    LTExpression result = new LTExpression((Expression) lhs, (Expression) rhs);
    result.type = Type.getBooleanType();
    result.line = line;
    result.col = col;
    return result;
  }

  @Override
  public Node visitGreaterThanExpression(KlangParser.GreaterThanExpressionContext ctx) {
    Node lhs = this.visit(ctx.lhs);
    Node rhs = this.visit(ctx.rhs);
    int line = ctx.start.getLine();
    int col = ctx.start.getCharPositionInLine();

    if (lhs.type != Type.getIntegerType() || rhs.type != Type.getIntegerType()) {
      String error = "Both operants of this expression have to be a number.";
      throw new RuntimeException(Helper.getErrorPrefix(line, col) + error);
    }

    GTExpression result = new GTExpression((Expression) lhs, (Expression) rhs);
    result.type = Type.getBooleanType();
    result.line = line;
    result.col = col;
    return result;
  }

  @Override
  public Node visitLessThanOrEqualToExpression(KlangParser.LessThanOrEqualToExpressionContext ctx) {
    Node lhs = this.visit(ctx.lhs);
    Node rhs = this.visit(ctx.rhs);
    int line = ctx.start.getLine();
    int col = ctx.start.getCharPositionInLine();

    if (lhs.type != Type.getIntegerType() || rhs.type != Type.getIntegerType()) {
      String error = "Both operants of this expression have to be a number.";
      throw new RuntimeException(Helper.getErrorPrefix(line, col) + error);
    }

    LTEExpression result = new LTEExpression((Expression) lhs, (Expression) rhs);
    result.type = Type.getBooleanType();
    result.line = line;
    result.col = col;
    return result;
  }

  @Override
  public Node visitGreaterThanOrEqualToExpression(KlangParser.GreaterThanOrEqualToExpressionContext ctx) {
    Node lhs = this.visit(ctx.lhs);
    Node rhs = this.visit(ctx.rhs);
    int line = ctx.start.getLine();
    int col = ctx.start.getCharPositionInLine();

    if (lhs.type != Type.getIntegerType() || rhs.type != Type.getIntegerType()) {
      String error = "Both operants of this expression have to be a number.";
      throw new RuntimeException(Helper.getErrorPrefix(line, col) + error);
    }

    GTEExpression result = new GTEExpression((Expression) lhs, (Expression) rhs);
    result.type = Type.getBooleanType();
    result.line = line;
    result.col = col;
    return result;
  }

  @Override
  public Node visitSubstractionExpression(KlangParser.SubstractionExpressionContext ctx) {
    Node lhs = this.visit(ctx.lhs);
    Node rhs = this.visit(ctx.rhs);
    int line = ctx.start.getLine();
    int col = ctx.start.getCharPositionInLine();
    SubstractionExpression result = new SubstractionExpression((Expression) lhs, (Expression) rhs);
    try {
      result.type = lhs.type.combine(rhs.type);
    } catch (Exception e) {
      throw new RuntimeException(Helper.getErrorPrefix(line, col) + e.getMessage());
    }
    result.line = line;
    result.col = col;
    return result;
  }

  @Override
  public Node visitMultiplicationExpression(KlangParser.MultiplicationExpressionContext ctx) {
    Node lhs = this.visit(ctx.lhs);
    Node rhs = this.visit(ctx.rhs);
    int line = ctx.start.getLine();
    int col = ctx.start.getCharPositionInLine();
    MultiplicationExpression result = new MultiplicationExpression((Expression) lhs, (Expression) rhs);
    try {
      result.type = lhs.type.combine(rhs.type);
    } catch (Exception e) {
      throw new RuntimeException(Helper.getErrorPrefix(line, col) + e.getMessage());
    }
    result.line = line;
    result.col = col;
    return result;
  }

  @Override
  public Node visitDivisionExpression(KlangParser.DivisionExpressionContext ctx) {
    Node lhs = this.visit(ctx.lhs);
    Node rhs = this.visit(ctx.rhs);
    int line = ctx.start.getLine();
    int col = ctx.start.getCharPositionInLine();
    DivisionExpression result = new DivisionExpression((Expression) lhs, (Expression) rhs);
    try {
      result.type = lhs.type.combine(rhs.type);
    } catch (Exception e) {
      throw new RuntimeException(Helper.getErrorPrefix(line, col) + e.getMessage());
    }
    result.line = line;
    result.col = col;
    return result;
  }

  @Override
  public Node visitModuloExpression(KlangParser.ModuloExpressionContext ctx) {
    Node lhs = this.visit(ctx.lhs);
    Node rhs = this.visit(ctx.rhs);
    int line = ctx.start.getLine();
    int col = ctx.start.getCharPositionInLine();
    ModuloExpression result = new ModuloExpression((Expression) lhs, (Expression) rhs);
    try {
      result.type = lhs.type.combine(rhs.type);
    } catch (Exception e) {
      throw new RuntimeException(Helper.getErrorPrefix(line, col) + e.getMessage());
    }
    result.line = line;
    result.col = col;
    return result;
  }

  @Override
  public Node visitNegateExpression(KlangParser.NegateExpressionContext ctx) {
    Node expression = this.visit(ctx.expression());
    NegateExpression result = new NegateExpression((Expression) expression);
    result.type = expression.type;
    result.line = ctx.start.getLine();
    result.col = ctx.start.getCharPositionInLine();
    return result;
  }

  @Override
  public Node visitNotExpression(KlangParser.NotExpressionContext ctx) {
    Node expression = this.visit(ctx.expression());
    NotExpression result = new NotExpression((Expression) expression);
    result.type = expression.type;
    result.line = ctx.start.getLine();
    result.col = ctx.start.getCharPositionInLine();
    return result;
  }

  @Override
  public Node visitVariable(KlangParser.VariableContext ctx) {
    String name = ctx.IDENT().getText();
    int line = ctx.start.getLine();
    int col = ctx.start.getCharPositionInLine();

    VariableDeclaration var = this.vars.get(name);
    if (var == null) {
      String error = "Variable with name \"" + name + "\" not defined.";
      throw new RuntimeException(Helper.getErrorPrefix(line, col) + error);
    }

    Variable result = new Variable(ctx.IDENT().getText());
    result.type = var.type;
    result.line = line;
    result.col = col;
    return result;
  }

  @Override
  public Node visitAtomExpression(KlangParser.AtomExpressionContext ctx) {
    return this.visit(ctx.atom());
  }

  @Override
  public Node visitIntAtom(KlangParser.IntAtomContext ctx) {
    Node n = new IntegerExpression(Integer.parseInt(ctx.getText()));
    n.type = Type.getIntegerType();
    n.line = ctx.start.getLine();
    n.col = ctx.start.getCharPositionInLine();
    return n;
  }

  @Override
  public Node visitBoolAtom(KlangParser.BoolAtomContext ctx) {
    Node n = new BooleanExpression(ctx.getText().equals("true") ? true : false);
    n.type = Type.getBooleanType();
    n.line = ctx.start.getLine();
    n.col = ctx.start.getCharPositionInLine();
    return n;
  }

  @Override
  public Node visitFunctionDef(KlangParser.FunctionDefContext ctx) {
    String name = ctx.funcName.getText();
    int line = ctx.start.getLine();
    int col = ctx.start.getCharPositionInLine();
    Type returnType = Type.getByName(ctx.returnType.type().getText());
    this.currentDeclaredReturnType = returnType;

    // Create a new set for the variables of the current function
    // this will be filled in the variable declaration visitor aswell
    this.vars = new HashMap<>();

    // Process the paremter list by visiting every paremter in it
    int paramCount = ctx.params.parameter().size();
    Parameter[] params = new Parameter[paramCount];
    for (int i = 0; i < paramCount; i++) {
      // Add the parameter to the list of parameters
      Parameter param = (Parameter) this.visit(ctx.params.parameter(i));
      params[i] = param;

      // add the param as a variable
      VariableDeclaration var = new VariableDeclaration(param.name);
      var.type = param.type;
      this.vars.put(param.name, var);
    }

    // Visit the block, make sure that a return value is guaranteed
    Node block = this.visit(ctx.braced_block());
    if (block.type == null) {
      String error = "Function " + name + " has to return something of type " + returnType.getName() + ".";
      throw new RuntimeException(Helper.getErrorPrefix(line, col) + error);
    }

    FunctionDefinition result = new FunctionDefinition(name, params, (Block) block);
    result.type = returnType;

    result.line = ctx.start.getLine();
    result.col = ctx.start.getCharPositionInLine();
    return result;
  }

  @Override
  public Node visitParameter(KlangParser.ParameterContext ctx) {
    String name = ctx.IDENT().getText();
    Type type = Type.getByName(ctx.type_annotation().type().getText());
    Parameter result = new Parameter(name);
    result.type = type;
    result.line = ctx.start.getLine();
    result.col = ctx.start.getCharPositionInLine();
    return result;
  }

  @Override
  public Node visitFunctionCallExpression(KlangParser.FunctionCallExpressionContext ctx) {
    String name = ctx.functionCall().IDENT().getText();
    int line = ctx.start.getLine();
    int col = ctx.start.getCharPositionInLine();

    FunctionInformation func = this.funcs.get(name);
    if (func == null) {
      String error = "Function with name \"" + name + "\" not defined.";
      throw new RuntimeException(Helper.getErrorPrefix(line, col) + error);
    }

    // Make sure the number of arguments matches the number of parameters
    int argCount = ctx.functionCall().arguments().expression().size();
    int paramCount = func.parameters.size();
    if (argCount != paramCount) {
      String error = "Function \"" + name + "\" expects " + paramCount + " parameters, but got " + argCount + ".";
      throw new RuntimeException(Helper.getErrorPrefix(line, col) + error);
    }

    // Evaluate every argument
    Expression[] args = new Expression[argCount];
    for (int i = 0; i < argCount; i++) {
      Expression expression = (Expression) this.visit(ctx.functionCall().arguments().expression(i));
      try {
        expression.type.combine(func.signature[i]); // Make sure the types are matching
      } catch (Exception e) {
        throw new RuntimeException(Helper.getErrorPrefix(line, col) + "argument " + i + " " + e.getMessage());
      }
      args[i] = expression;
    }

    FunctionCall result = new FunctionCall(name, args);
    result.type = func.returnType;
    result.line = line;
    result.col = col;
    return result;
  }
}