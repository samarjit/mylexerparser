package ps.exp;

import ps.visitor.ExpressionNodeVisitor;

public class ConstantExpressionNode implements ExpressionNode {
	  private double value;

	  public ConstantExpressionNode(double value) {
	    this.value = value;
	  }

	  public ConstantExpressionNode(String value) {
	    this.value = Double.valueOf(value);
	  }

	  public double getValue() {
	    return value;
	  }

	  public int getType() {
	    return ExpressionNode.CONSTANT_NODE;
	  }
	  
	  public void accept(ExpressionNodeVisitor visitor) {
		    visitor.visit(this);
	  }
}