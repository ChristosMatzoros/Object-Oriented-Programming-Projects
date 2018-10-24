package ce325.hw1;

import java.io.*;

public class ArithmeticCalculator {
	static public void main(String []args){
		//expression input
		java.util.Scanner sc = new java.util.Scanner(System.in);
		System.out.print("Enter math expression: ");
		String line = sc.nextLine();
		System.out.println("Math expression is: "+line);

		ArithmeticExpression expression = new ArithmeticExpression();

		if(expression.checkBalancedParentheses(line) && expression.checkIrrelevantCharacters(line)){
			//split the given expression into sub-expressions that finally compose the tree
			expression.SplitExpression(line,null);

			//create file for DotString and execute graphviz
			try {
				PrintWriter pfile = new PrintWriter("ArithmeticExpression.dot");
				pfile.println(expression.toDotString(expression.getTree().getRoot()));
				pfile.close();
				System.out.println("PRINT DOT FILE OK!");

				Process p = Runtime.getRuntime().exec("dot -Tpng ArithmeticExpression.dot " +
														"-o ArithmeticExpression.png");
				p.waitFor();
				System.out.println("PRINT PNG FILE OK!");
			}catch(Exception ex) {
				System.err.println("Unable to write dotString!!!");
				ex.printStackTrace();
				System.exit(1);
			}

			//print expression equivalent to the original expression based on the preconstructed tree using toString() function
			System.out.println("toString: "+expression.toString(expression.getTree().getRoot()));

			//calculate the result of the given expression
			System.out.println("Calculation Result: "+expression.Calculate(expression.getTree().getRoot()));
		}
		else{
			System.out.println("Wrong Expression!");
		}
	}
}
