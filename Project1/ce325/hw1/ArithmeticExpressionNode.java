package ce325.hw1;

public class ArithmeticExpressionNode{
	private String data;	//data of each ArithmeticExpressionNode object(it can either be an operator or a number)
	private ArithmeticExpressionNode left, right;

	//class constructor
	public ArithmeticExpressionNode(String str){
		data  = str;
		left  = right = null;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getData() {
		return data;
	}

	public void setLeft(ArithmeticExpressionNode left) {
		this.left = left;
	}

	public ArithmeticExpressionNode getLeft() {
		return left;
	}

	public void setRight(ArithmeticExpressionNode right ) {
		this.right = right;
	}

	public ArithmeticExpressionNode getRight() {
		return right;
	}
}
