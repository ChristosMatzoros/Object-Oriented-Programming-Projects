//The node class of the list used for the Action Manager
package ce325.hw3;

public class InfoNode {
	private InfoNode left;
	private InfoNode right;
	private int panelRow;	//the row of the panel that the change was made
	private int panelColumn;	//the column of the text field that the change was made
	private int textFieldRow;	//the row of the panel that the change was made
	private int textFieldColumn;	//the row of the text field that the change was made
	private String value;	//represents the value of the text field when the change was made

	public InfoNode(){}

	public InfoNode(int panelRow, int panelColumn, int textFieldRow, int textFieldColumn, String value){
		left = null;
		right = null;
		this.panelRow = panelRow;
		this.panelColumn = panelColumn;
		this.textFieldRow = textFieldRow;
		this.textFieldColumn = textFieldColumn;
		this.value = value;
	}

	public InfoNode getLeft(){
		return left;
	}

	public InfoNode getRight(){
		return right;
	}

	public int getPanelRow(){
		return panelRow;
	}

	public int getPanelColumn(){
		return panelColumn;
	}

	public int getTextFieldRow(){
		return textFieldRow;
	}

	public int getTextFieldColumn(){
		return textFieldColumn;
	}

	public String getValue(){
		return value;
	}

	public void setLeft(InfoNode newLeft){
		left = newLeft;
	}

	public void setRight(InfoNode newRight){
		right = newRight;
	}

	public void setPanelRow(int newPanelRow){
		panelRow = newPanelRow;
	}

	public void setPanelColumn(int newPanelColumn){
		panelColumn =  newPanelColumn;
	}

	public void setTextFieldRow(int newTextFieldRow){
		textFieldRow = newTextFieldRow;
	}

	public void setTextFieldColumn(int newTextFieldColumn){
		textFieldColumn =  newTextFieldColumn;
	}

	public void setValue(String newVal){
		value = newVal;
	}
}
