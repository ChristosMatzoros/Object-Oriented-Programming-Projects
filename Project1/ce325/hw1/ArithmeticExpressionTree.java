package ce325.hw1;

public class ArithmeticExpressionTree{
	private ArithmeticExpressionNode root;

	//class constructor
	public ArithmeticExpressionTree(String data){
		root = new ArithmeticExpressionNode(data);
	}

	//returns the data(string) of the root
	public String getRootValue(){
		return(root.getData());
	}

	//returns the root node
	public ArithmeticExpressionNode getRoot(){
		return(root);
	}

	//adds a left or right child to the given parent node
	public void add(ArithmeticExpressionNode parent, ArithmeticExpressionNode child, String side){
		if(side.equals("left")){
			 parent.setLeft(child);
		 }
		else if(side.equals("right")){
			parent.setRight(child);
		}
	}
}
