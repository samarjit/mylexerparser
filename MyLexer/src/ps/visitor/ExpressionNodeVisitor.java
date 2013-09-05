package ps.visitor;

import ps.exp.AdditionExpressionNode;
import ps.exp.ConstantExpressionNode;
import ps.exp.ExponentiationExpressionNode;
import ps.exp.FunctionExpressionNode;
import ps.exp.MultiplicationExpressionNode;
import ps.exp.VariableExpressionNode;

public interface ExpressionNodeVisitor
{
  public void visit(VariableExpressionNode node);
  public void visit(ConstantExpressionNode node);
  public void visit(AdditionExpressionNode node);
  public void visit(MultiplicationExpressionNode node);
  public void visit(ExponentiationExpressionNode node);
  public void visit(FunctionExpressionNode node);
}