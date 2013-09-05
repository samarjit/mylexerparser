package ps.exp;

import ps.visitor.ExpressionNodeVisitor;

public class VariableExpressionNode implements ExpressionNode {
	  private String name;
	  private double value;
	  private boolean valueSet;

	  public VariableExpressionNode(String name) {
	    this.name = name;
	    valueSet = false;
	  }

	  public int getType() {
	    return ExpressionNode.VARIABLE_NODE;
	  }

	  public String getName(){
		  return this.name;
	  }
	  
	  public void setValue(double value) {
	    this.value = value;
	    this.valueSet = true;
	  }

	  public double getValue() {
	    if (valueSet)
	      return value;
	    else
	      throw new EvaluationException("Variable '" 
	        + name + "' was not initialized.");
	  }
	  
	  public void accept(ExpressionNodeVisitor visitor) {
		    visitor.visit(this);
	  }
}
