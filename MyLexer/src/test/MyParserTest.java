package test;

import java.util.List;

import ps.Parser;
import ps.ParserException;
import ps.Token;
import ps.Tokenizer;
import ps.exp.EvaluationException;
import ps.exp.ExpressionNode;
import ps.exp.FunctionExpressionNode;

public class MyParserTest {
	public static void main(String[] args) {
		Parser parser = new Parser();
		try {
			
			Tokenizer tokenizer = new Tokenizer();
			  tokenizer.add("("+FunctionExpressionNode.getAllFunctions()+")", Token.FUNCTION); // function
			  tokenizer.add("\\(", Token.OPEN_BRACKET); // open bracket
			  tokenizer.add("\\)", Token.CLOSE_BRACKET); // close bracket
			  tokenizer.add("\\+|-", Token.PLUSMINUS); // plus or minus
			  tokenizer.add("\\*|/", Token.MULTDIV); // mult or divide
			  tokenizer.add("[0-9]+",Token.NUMBER); // integer number
			  tokenizer.add("[a-zA-Z][a-zA-Z0-9_]*", Token.VARIABLE); // variable
			  tokenizer.add("\\^", Token.RAISED); // variable
			  
			  tokenizer.tokenize("3*2^4 + sqrt(1+3)");
//			  tokenizer.tokenize("3*2^4 + sqrt(1+3)");
			  for (Token tok : tokenizer.getTokens()) {
				    System.out.println("" + tok.token + " " + tok.sequence);
				  }
			  
			  System.out.println("Tokienize again");	

			  Tokenizer tokenizer1 = Tokenizer.getExpressionTokenizer();
			  tokenizer1.tokenize("3*2^4 + sqrt(1+3)");
			  for (Token tok : tokenizer1.getTokens()) {
				    System.out.println("" + tok.token + " " + tok.sequence);
				  }	
				
			ExpressionNode expression = parser.parse("3*2^4+sqrt(1+3)" );
			System.out.println("The value of the expression is " + expression.getValue());
		} catch (ParserException e) {
			System.out.println(e.getMessage());
		} catch (EvaluationException e) {
			System.out.println(e.getMessage());
		}
	}
}
