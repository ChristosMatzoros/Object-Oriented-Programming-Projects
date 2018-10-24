package ce325.hw1;

import java.util.regex.*;
import java.util.*;

public class ArithmeticExpression {

	private boolean isRoot;		//flag to check if the node created is the root, in which case we have to create a tree object
	private StringBuffer str;	//stringbuffer used in toDotString method
	private StringBuffer tempString;	//stringbuffer used in toString method
	private ArithmeticExpressionTree tree; 	//tree variable

	public ArithmeticExpression(){
		isRoot = true;
		str = new StringBuffer();
		tempString = new StringBuffer();
		tree = new ArithmeticExpressionTree(null);
	}

	//returns the ArithmeticExpressionTree tree object
	public ArithmeticExpressionTree getTree(){
		return tree;
	}

	//checks if all the parentheses that are opened are then closed and if there are no parentheses closed before they are opened
	public boolean checkBalancedParentheses(String expression){
		int i;
		int parenthesesCounter = 0;

		for( i=0; i<expression.length(); i++){
			if(expression.charAt(i) == '('){
				parenthesesCounter++;
			}
			else if(expression.charAt(i) == ')'){
				parenthesesCounter--;
			}

			if(parenthesesCounter < 0){
				System.out.println("Close parentheses symbol without matching open parentheses symbol before");
				return false;
			}
		}
		if(parenthesesCounter == 0){
			return true;
		}
		else{
			System.out.println("Uneven number of open and closed parentheses");
			return false;
		}
	}

	//checks if the expression consists of permitted characters
	public boolean checkIrrelevantCharacters(String expression){
		Pattern r1 = Pattern.compile("\\D\\.");	//used to find instances of a dot without a digit before it
		Pattern r2 = Pattern.compile("\\.\\D");	//used to find instances of a dot without a digit after it

		Matcher m1 = r1.matcher(expression);
		Matcher m2 = r2.matcher(expression);

		if(m1.find()){
			return false;
		}
		else if(m2.find()){
			return false;
		}
		else if((expression.charAt(0)=='.') || (expression.charAt(expression.length()-1)=='.')){
			return false;
		}
		else if (expression.matches("^[\\d\\.\\s\\(\\)\\-+x*/^]+$")) {	//used to check if the expression consists of permitted characters
			return true;
		}
		else{
			return false;
		}
	}

	//adds an ArrayList object based on the level and the priority of the operator
	public void addToArrayList(ArrayList<ArithmeticOperators> operatorList, ArithmeticOperators operator){
		int i;

		for( i=0; i<operatorList.size(); i++){
			if(operator.getLevel() < operatorList.get(i).getLevel()){
				operatorList.add(i, operator);
				return;
			}
			else if( (operator.getLevel() == operatorList.get(i).getLevel()) && (operator.getPriority() < operatorList.get(i).getPriority()) ){
				operatorList.add(i, operator);
				return;
			}
		}
		operatorList.add(operator);
		return;
	}

	//prints the ArrayList objects
	public void printArrayList(ArrayList<ArithmeticOperators> operatorList){
		int i;

		for( i=0; i<operatorList.size(); i++){
			System.out.println("Position "+i+": "+operatorList.get(i).toString());
		}
		System.out.println("--------------------------------------------");
		return;
	}

	//this method is used to create an ArrayList of the operators of a given expression
	public void CreateList(ArrayList<ArithmeticOperators> operatorList, String expression){
		Character element = ' ';
		int parenthesesCounter = 0;	//indicates the level of the operator

		for(int i=0; i<expression.length(); i++){
			element = expression.charAt(i);
			if(element == '('){
				parenthesesCounter++;
			}
			else if(element == ')'){
				parenthesesCounter--;
			}
			else if(element == '+'){
				ArithmeticOperators operator = new ArithmeticOperators(element, i, parenthesesCounter, 1);
				addToArrayList(operatorList,operator);
			}
			else if(element == '-'){
				ArithmeticOperators operator = new ArithmeticOperators(element, i, parenthesesCounter, 2);
				addToArrayList(operatorList,operator);
			}
			else if((element == '*') || (element == 'x')){
				ArithmeticOperators operator = new ArithmeticOperators(element, i, parenthesesCounter, 3);
				addToArrayList(operatorList,operator);
			}
			else if(element == '/'){
				ArithmeticOperators operator = new ArithmeticOperators(element, i, parenthesesCounter, 4);
				addToArrayList(operatorList,operator);
			}
			else if(element == '^'){
				ArithmeticOperators operator = new ArithmeticOperators(element, i, parenthesesCounter, 5);
				addToArrayList(operatorList,operator);
			}
		}
	}

	//recursive method that splits the given expression into sub-expressions that finally compose the tree
	public void SplitExpression(String expression, ArithmeticExpressionNode current){
		String newString;
		ArrayList<ArithmeticOperators> operatorList = new ArrayList<ArithmeticOperators>();

		CreateList(operatorList,expression);
		//while parsing the given expression it creates a list with all the operators sorted in ascending order
		// firstly by their level(in how many parentheses they are inside) and secondly by their priority.
		//therefore the first element of the list is the element that we are going to use in this iteration

		if(operatorList.isEmpty()){
			if(isRoot){
				tree.getRoot().setData(expression.trim());
				isRoot = false;
				return;
			}
			current.setData(expression.trim());
			return;
		}

		if(isRoot){
			tree.getRoot().setData(operatorList.get(0).getSymbol().toString());
			current = tree.getRoot();
			isRoot = false;
		}
		else{
			current.setData(operatorList.get(0).getSymbol().toString());
		}

		newString = expression;

		// if the first element of the list is of level higher than one then we have to remove the parentheses around it
		while(operatorList.get(0).getLevel() != 0){
			//remove first and last parentheses
			newString = newString.trim().replaceAll("^\\(", "").replaceAll("\\)$", "").trim();
			operatorList.clear();
			CreateList(operatorList,newString);
		}

		ArithmeticExpressionNode left = new ArithmeticExpressionNode(null);
		ArithmeticExpressionNode right = new ArithmeticExpressionNode(null);
		tree.add(current, left, "left");
		tree.add(current, right, "right");

		SplitExpression(newString.substring(0, operatorList.get(0).getIndex()), left); //first substring
		SplitExpression(newString.substring(operatorList.get(0).getIndex() + 1, newString.length()), right); //second substring
	}

	//this method is used to create an expression equivalent to the original expression based on the preconstructed tree
	public String toString(ArithmeticExpressionNode currRoot) {
		if (currRoot == null) {
			return null;
		}
		if(currRoot.getLeft() != null && currRoot.getRight() != null)
			tempString.append("(");

		toString(currRoot.getLeft());
		tempString.append(currRoot.getData());
		toString(currRoot.getRight());

		if(currRoot.getLeft() != null && currRoot.getRight() != null)
			tempString.append(")");

		return tempString.toString();
	}

	//this method is used to create an input string to visualize the tree
	public String toDotString(ArithmeticExpressionNode currRoot){
		if (currRoot == null) {
			return null;
		}
		str.append("\t" + currRoot.hashCode() + " [label=\""+currRoot.getData()+"\", shape=circle, color=black]\n");
		if(currRoot.getLeft() != null){
			str.append("\t" + currRoot.hashCode() + " -> " + currRoot.getLeft().hashCode() + "\n");
		}

		toDotString(currRoot.getLeft());

		if(currRoot.getRight() != null){
			str.append("\t" + currRoot.hashCode() + " -> " + currRoot.getRight().hashCode() + "\n");
		}

		toDotString(currRoot.getRight());

		return "digraph ArithmeticExpressionTree {\n\tfontcolor=\"red\";\n\tfontsize=30;\n\tlabelloc=\"t\";\n\tlabel=\"Arithmetic Expression\"\n" + str.toString() + "}";
	}

	//this method is used to calculate the result of the given expression
	public Double Calculate(ArithmeticExpressionNode currRoot) {
		Double a=0.0, b=0.0;

		if(currRoot.getLeft() != null && currRoot.getRight() != null){
			a = Calculate(currRoot.getLeft());
			b = Calculate(currRoot.getRight());

			if(currRoot.getData().equals("+")){
				return a+b;
			}
			else if(currRoot.getData().equals("-")){
				return a-b;
			}
			else if( (currRoot.getData().equals("*")) || (currRoot.getData().equals("x")) ){
				return a*b;
			}
			else if(currRoot.getData().equals("/")){
				return a/b;
			}
			else if(currRoot.getData().equals("^")){
				return Math.pow(a,b);
			}
		}
		return Double.valueOf(currRoot.getData());
	}
}
