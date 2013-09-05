package test;

import ps.Parser;
import ps.ParserException;
import ps.exp.EvaluationException;
import ps.exp.ExpressionNode;
import ps.visitor.SetVariable;

public class VisitorTest {
	public static void main(String[] args) {
		Parser parser = new Parser();
		try {
			ExpressionNode expression = parser.parse("sin(pi/2)");
			expression.accept(new SetVariable("pi", Math.PI));
			System.out.println("The value of the expression is " + expression.getValue());
		} catch (ParserException e) {
			System.out.println(e.getMessage());
		} catch (EvaluationException e) {
			System.out.println(e.getMessage());
		}
	}
}
