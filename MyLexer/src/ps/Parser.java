package ps;

import java.util.LinkedList;
import java.util.List;

import ps.exp.AdditionExpressionNode;
import ps.exp.ConstantExpressionNode;
import ps.exp.ExponentiationExpressionNode;
import ps.exp.ExpressionNode;
import ps.exp.FunctionExpressionNode;
import ps.exp.MultiplicationExpressionNode;
import ps.exp.VariableExpressionNode;

public class Parser {
	LinkedList<Token> tokens;
	Token lookahead;

	public ExpressionNode parse(String expression) {
		Tokenizer tokenizer = Tokenizer.getExpressionTokenizer();
		tokenizer.tokenize(expression);
		LinkedList<Token> tokens = tokenizer.getTokens();
		return this.parse(tokens);
	}
	 
	public ExpressionNode parse(List<Token> tokens) {
		this.tokens = new LinkedList<Token>(tokens);
		lookahead = this.tokens.getFirst();
		return expression();
	}

	private void nextToken() {
		tokens.pop();
		// at the end of input we return an epsilon token
		if (tokens.isEmpty())
			lookahead = new Token(Token.EPSILON, "");
		else
			lookahead = tokens.getFirst();
	}

	private ExpressionNode expression() {
		// expression -> signed_term sum_op
		ExpressionNode expr = signedTerm();
		return sumOp(expr);
	}

	private ExpressionNode sumOp(ExpressionNode expr) {
		if (lookahead.token == Token.PLUSMINUS) {
			// sum_op -> PLUSMINUS term sum_op
			AdditionExpressionNode sum;
			 if (expr.getType() == ExpressionNode.ADDITION_NODE)
			      sum = (AdditionExpressionNode)expr;
			    else
			      sum = new AdditionExpressionNode(expr, true);
			 
			boolean positive = lookahead.sequence.equals("+");
		    
			nextToken();
		    
		    ExpressionNode t = term();
		    sum.add(t, positive);

		    return sumOp(sum);
		} else {
			// sum_op -> EPSILON
			return expr;
		}
	}

	private ExpressionNode signedTerm() {
		if (lookahead.token == Token.PLUSMINUS) {
			// signed_term -> PLUSMINUS term
			boolean positive = lookahead.sequence.equals("+");
			nextToken();
			ExpressionNode t = term();
		    if (positive)
		      return t;
		    else
		      return new AdditionExpressionNode(t, false);
		} else {
			// signed_term -> term
			return term();
		}
	}

	private ExpressionNode term() {
		// term -> factor term_op
		ExpressionNode f = factor();
		return termOp(f);
	}

	private ExpressionNode termOp(ExpressionNode expression) {
		if (lookahead.token == Token.MULTDIV) {
			// term_op -> MULTDIV factor term_op
			MultiplicationExpressionNode prod;
			if (expression.getType() == ExpressionNode.MULTIPLICATION_NODE)
			      prod = (MultiplicationExpressionNode)expression;
			    else
			      prod = new MultiplicationExpressionNode(expression, true);
			boolean positive = lookahead.sequence.equals("*");
			nextToken();
			ExpressionNode f = factor();
		    prod.add(f, positive);
		    return termOp(prod);
		} else {
			// term_op -> EPSILON
			return expression;
		}
	}

	private ExpressionNode factor() {
		if (lookahead.token == Token.FUNCTION) {
			// factor -> FUNCTION argument
			int function =
				      FunctionExpressionNode.stringToFunction(lookahead.sequence);
			nextToken();
			ExpressionNode expr = factor();
			return new FunctionExpressionNode(function, expr);
		} else {
			// factor -> argument factor_op
			ExpressionNode a = argument();
			return factorOp(a);
		}
	}

	private ExpressionNode factorOp(ExpressionNode expression) {
		if (lookahead.token == Token.RAISED) {
			// factor_op -> RAISED expression
			nextToken();
			ExpressionNode exponent = factor();
			return new ExponentiationExpressionNode(expression, exponent);
		} else {
			// factor_op -> EPSILON
			return expression;
		}
	}

	private ExpressionNode argument() {
		if (lookahead.token == Token.OPEN_BRACKET) {
			// argument -> OPEN_BRACKET sum CLOSE_BRACKET
			nextToken();
			ExpressionNode expr = expression();

			if (lookahead.token != Token.CLOSE_BRACKET)
				throw new ParserException("Closing brackets expected and " + lookahead.sequence + " found instead");

			nextToken();
			return expr;
		} else {
			// argument -> value
			return value();
		}
	}

	private ExpressionNode value() {
		if (lookahead.token == Token.NUMBER) {
			// argument -> NUMBER
			ExpressionNode expr = new ConstantExpressionNode(lookahead.sequence);
			nextToken();
			return expr;
		} else if (lookahead.token == Token.VARIABLE) {
			// argument -> VARIABLE
			ExpressionNode expr = new VariableExpressionNode(lookahead.sequence);
			nextToken();
			return expr;
		} else if (lookahead.token == Token.EPSILON) {
			throw new ParserException("Unexpected end of input");
		} else {
			throw new ParserException("Unexpected symbol " + lookahead.sequence + " found");
		}
	}
}
