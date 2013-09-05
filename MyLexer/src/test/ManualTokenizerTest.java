package test;

import ps.ParserException;
import ps.Token;
import ps.Tokenizer;

public class ManualTokenizerTest {
	public static void main(String[] args) {
		  Tokenizer tokenizer = new Tokenizer();
		  tokenizer.add("sin|cos|exp|ln|sqrt", 1); // function
		  tokenizer.add("\\(", 2); // open bracket
		  tokenizer.add("\\)", 3); // close bracket
		  tokenizer.add("\\+|-", 4); // plus or minus
		  tokenizer.add("\\*|/", 5); // mult or divide
		  tokenizer.add("[0-9]+",6); // integer number
		  tokenizer.add("[a-zA-Z][a-zA-Z0-9_]*", 7); // variable
		  
		  try {
			  tokenizer.tokenize(" sin(x) * (1 + var_12) ");

			  for (Token tok : tokenizer.getTokens()) {
			    System.out.println("" + tok.token + " " + tok.sequence);
			  }
			}
			catch (ParserException e) {
			  System.out.println(e.getMessage());
			}
	}
}
