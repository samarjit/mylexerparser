package test;

import ps.exp.AdditionExpressionNode;
import ps.exp.ConstantExpressionNode;
import ps.exp.ExponentiationExpressionNode;
import ps.exp.ExpressionNode;
import ps.exp.FunctionExpressionNode;
import ps.exp.MultiplicationExpressionNode;

public class ManualExpressionTest {
	/**
	 * testing expression 3*2^4 + sqrt(1+3); created programmatically
	 * @param args
	 */
	public static void main(String[] args) {
		AdditionExpressionNode innerSum = new AdditionExpressionNode();
		innerSum.add(new ConstantExpressionNode(1), true);
		innerSum.add(new ConstantExpressionNode(3), true);

		ExpressionNode sqrt = new FunctionExpressionNode(FunctionExpressionNode.SQRT, innerSum);

		ExpressionNode expo = new ExponentiationExpressionNode(new ConstantExpressionNode(2), new ConstantExpressionNode(4));

		MultiplicationExpressionNode prod = new MultiplicationExpressionNode();
		prod.add(new ConstantExpressionNode(3), true);
		prod.add(expo, true);

		AdditionExpressionNode expression = new AdditionExpressionNode();
		expression.add(prod, true);
		expression.add(sqrt, true);

		System.out.println("The result is " + expression.getValue());
	}
}
