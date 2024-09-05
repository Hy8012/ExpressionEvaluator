import java.util.EmptyStackException;

// TODO: Auto-generated Javadoc
/**
 * The Class ExpressionEvaluator.
 */
// TODO: Auto-generated Javadoc
public class ExpressionEvaluator {
	// These are the required error strings for that MUST be returned on the appropriate error 
	// for the JUnit tests to pass
	
	/** The Constant PAREN_ERROR. */
	private static final String PAREN_ERROR = "Paren Error: ";
	
	/** The Constant OP_ERROR. */
	private static final String OP_ERROR = "Op Error: ";
	
	/** The Constant DATA_ERROR. */
	private static final String DATA_ERROR = "Data Error: ";
	
	/** The Constant DIV0_ERROR. */
	private static final String DIV0_ERROR = "Div0 Error: ";

	/** The data stack. */
	private GenericStack<Double> dataStack;
	
	/** The oper stack. */
	private GenericStack<String>  operStack;

	/** The right paren operator group. */
	//Repetitive Regex Types
	private static String RIGHT_PAREN_OPERATOR_GROUP = "(-|\\+|\\/|\\(|\\*)";
	
	/** The operator group. */
	private static String OPERATOR_GROUP = "(-|\\+|\\/|\\*)";

	/**
	 * Convert to tokens. Takes a string and splits it into tokens that
	 * are either operators or data. This is where you should convert 
	 * implicit multiplication to explict multiplication. It is also a candidate
	 * for recognizing negative numbers, and then including that negative sign
	 * as part of the appropriate data token.
	 *
	 * @param str the str
	 * @return the string[]
	 */
	private String[] convertToTokens(String str) {
		str = str.replaceAll("\\d\\s+\\d", "_");
		str = str.replaceAll("(-|\\+|\\*|\\/|\\(|\\))", " $1 ");
		System.out.println(str);
		str = implicitMultiplicationToExplicit(str);
		str = implicitNegativeToExplicit(str);
		System.out.println(str);
		//System.out.println(str);
		String[] tokens = str.trim().split("\\s+");
		return tokens;
	}
	
	
	
	/**
	 * Converts implicit Multiplication (i.e. 'x(y - z)') to explicit 'x * (y - z)'
	 *
	 * @param in the input string from convertToTokens(String str)
	 * @return the string after replaceAll's have been added.
	 */
	String implicitMultiplicationToExplicit(String in) {
		in = in.replaceAll("(\\d)\\s\\(", "$1 * (");
		in = in.replaceAll("\\)\\s*(\\(|\\d)", ") * $1");
		return in;

	}
	
	/**
	 * Converts implicit Multiplication (i.e. '-x(y - z)') to explicit '-x * (y - z)'
	 *
	 * @param in the input string from convertToTokens(String str)
	 * @return the string after replaceAll's have been added.
	 */
	String implicitNegativeToExplicit(String in) {
		in = in.replaceAll("(-|\\+|\\*|\\(|\\/)\\s*\\-\\s*(\\d)", "$1 -$2");
		in = in.replaceAll("^\\s*\\-\\s*(\\d)", "-$1");
		

		//Should work for -(x) implicit, however, it is broken somehow. 
		//in = in.replaceAll("-\\s+\\(", "(-1) * (");
		System.out.println(in.matches("-\\s+\\("));
		return in;
	}

	/**
	 * Calculates the precedence of the operator to determine if TOS should be executed. Follows PMDAS order
	 * Parenthesis, Multiplication, Division, Addition, Subtraction
	 *
	 * @param operator the operator
	 * @return the int value of the operator
	 */
	
	//PMDAS
	int operatorPrecedence(String operator) {
		switch(operator) {
		case ")": return 3;
		case "*": return 2;
		case "/": return 2;
		case "+": return 1;
		case "-": return 1;
		default: return 0;
		}
	}

	/**
	 * Evaluates the top of stack
	 *
	 * @return true, if successful
	 */
	String evalTOS() {
		String operation = operStack.pop();
		Double data1 = dataStack.pop();
		if(dataStack.empty()) return OP_ERROR;
		Double data2 = dataStack.pop();
		switch(operation) {
			case "*": dataStack.push(data2 * data1); return "";
			case "/": 
				if(data1 == 0) {
					return DIV0_ERROR;
				} else {
					dataStack.push(data2 / data1); return "";
				}
			case "+": dataStack.push(data2 + data1); return "";
			case "-": dataStack.push(data2 - data1); return "";
			default: return OP_ERROR;
		}
	}
	
	
	/**
	 * Checks the input String[] for double sequential operations.	 *
	 * @param tokens the tokens array
	 * @return true, if there are double sequential operations
	 */
	private boolean doubleOperationError(String[] tokens) {
		for(int i = 0; i < tokens.length - 2; i++) {
			if(tokens[i].equals(" ")) {
				continue;
			}
			if(tokens[i].matches("\\(") && tokens[i + 1].matches("\\-?\\d*\\.?\\d+") && tokens[i + 2].matches(OPERATOR_GROUP) && !tokens[i + 3].matches("\\-?\\d*\\.?\\d+")) {
				return true;
			} else {
				return false;
			}
		}
		return false;
		
		
	}
	
	/**
	 * Error checking for invalid inputs. GUI is able to prevent invalid characters, however, must be implemented for JUnit
	 *
	 * @param tokens the token array after calling converToTokens()
	 * @return the string. Empty if no error detected, otherwise returns appropriate error. 
	 */
	private String errorChecking(String[] tokens) {
		int count = 0;
		for(int i = 0; i < tokens.length; i++) {
			if("".equals(tokens[i])) {
				continue;
			} if(tokens[i].matches("\\-*\\d*\\.\\d+") || tokens[i].matches("\\-*\\d+")) {
				continue;
			} else if (tokens[i].matches("(-|\\+|\\/|\\(|\\)|\\*)")) {
				if(tokens[i].matches("\\(")) {
					count++;
				} else if(tokens[i].matches("\\)")) {
					count--;
					if(i > 0 && tokens[i - 1].matches("\\(")) {return PAREN_ERROR;}
				} else if(tokens[i].matches(OPERATOR_GROUP)) {
					if(i > 0 && tokens[i - 1].matches(OPERATOR_GROUP) || i == 0 || i == tokens.length-1) {
						return OP_ERROR;}}	} else { return DATA_ERROR;}}
		if(count != 0) {return PAREN_ERROR;} else { return "";}}
	

	/**
	 * Evaluate expression. This is it, the big Kahuna....
	 * It is going to be called by the GUI (or the JUnit tester),
	 * and:
	 * a) convert the string to tokens
	 * b) if conversion successful, perform static error checking
	 *    - Paren Errors
	 *    - Op Errors 
	 *    - Data Errors
	 * c) if static error checking is successful:
	 *    - evaluate the expression, catching any runtime errors.
	 *      For the purpose of this project, the only runtime errors are 
	 *      divide-by-0 errors.
	 *
	 * @param str the str
	 * @return the string
	 */
	protected String evaluateExpression(String str) {
		String tokenString; dataStack =  new GenericStack<Double>(); operStack =  new GenericStack<String>(); String[] tokens = convertToTokens(str); String error = errorChecking(tokens); String errorMsg;
		if(!error.equals("")) return error;
		if(doubleOperationError(tokens)) return OP_ERROR;
		for(int i = 0; i < tokens.length; i++) {
			if(tokens[i].matches(RIGHT_PAREN_OPERATOR_GROUP)) {
				if(!operStack.empty() && operStack.peek().matches(OPERATOR_GROUP)) {
					if(tokens[i].matches(OPERATOR_GROUP)) {
						while(!operStack.empty() && operatorPrecedence(operStack.peek()) >= operatorPrecedence(tokens[i])) {
							errorMsg = evalTOS();
							if(!errorMsg.isEmpty()) return errorMsg;}}}
				operStack.push(tokens[i]);
			} else if(tokens[i].matches("\\)")) {
				while(!(operStack.peek().matches("\\("))) {
					errorMsg = evalTOS();
					if(!errorMsg.isEmpty()) return error;}
				operStack.pop();
			} else  {
				try {
					dataStack.push(Double.parseDouble(tokens[i]));
				} catch (NumberFormatException e) {
					//e.printStackTrace();
					return DATA_ERROR;}}}
		while(!operStack.empty()) {
			errorMsg = evalTOS();
			if(!errorMsg.isEmpty()) {return errorMsg;}}
		return (str + " = " + Double.toString(dataStack.pop()));
	}
}
