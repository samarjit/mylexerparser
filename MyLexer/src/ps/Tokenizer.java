package ps;

import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ps.exp.FunctionExpressionNode;

public class Tokenizer {

	private LinkedList<TokenInfo> tokenInfos = null;
	private LinkedList<Token> tokens;
	
	private class TokenInfo {
		public final Pattern regex;
		public final int token;

		public TokenInfo(Pattern regex, int token) {
			super();
			this.regex = regex;
			this.token = token;
		}
	}

	public Tokenizer() {
		tokenInfos = new LinkedList<TokenInfo>();
		tokens = new LinkedList<Token>();
	}

	public void add(String regex, int token) {
		tokenInfos.add(new TokenInfo(Pattern.compile("^" + regex), token));
	}
	
	public void tokenize(String str) throws ParserException {
		  String s = new String(str).trim();
		  tokens.clear();
		  while (!s.equals("")) {
			    boolean match = false;
			    for (TokenInfo info : tokenInfos) {
			        Matcher m = info.regex.matcher(s.trim());
			        if (m.find()) {
			          match = true;

			          String tok = m.group().trim();
			          tokens.add(new Token(info.token, tok));

			          s = m.replaceFirst("");
			          break;
			        }
			      }
			       
			    if (!match) throw new  ParserException("Exception Unexpected character in input: "+s);
		  }
	}
	
	public LinkedList<Token> getTokens() {
		  return tokens;
	}

	
	private static Tokenizer expressionTokenizer = null;
	
	public static Tokenizer getExpressionTokenizer() {
		if (expressionTokenizer == null)
			expressionTokenizer = createExpressionTokenizer();
		return expressionTokenizer;
	}

	private static Tokenizer createExpressionTokenizer() {
		Tokenizer tokenizer = new Tokenizer();

		tokenizer.add("[+-]", Token.PLUSMINUS);
		tokenizer.add("[*/]", Token.MULTDIV);
		tokenizer.add("\\^", Token.RAISED);

		String funcs = FunctionExpressionNode.getAllFunctions();
		tokenizer.add("(" + funcs + ")(?!\\w)", Token.FUNCTION);

		tokenizer.add("\\(", Token.OPEN_BRACKET);
		tokenizer.add("\\)", Token.CLOSE_BRACKET);
		tokenizer.add("(?:\\d+\\.?|\\.\\d)\\d*(?:[Ee][-+]?\\d+)?", Token.NUMBER);
		tokenizer.add("[a-zA-Z]\\w*", Token.VARIABLE);

		return tokenizer;
	}
	
	
	

}