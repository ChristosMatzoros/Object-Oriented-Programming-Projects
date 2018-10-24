package ce325.hw3;

import java.util.List;
import java.util.ArrayList;
import javax.swing.event.ChangeEvent;

//Manages a Queue of InfoNodes to perform undo operations
public class ActionManager{
	private InfoNode currentNode;	//the current index node
	private InfoNode parentNode;	//the parent node far left node.

	//Creates a new ActionManager object which is initially empty.
	public ActionManager(){
		currentNode = null;
		parentNode = new InfoNode();
		currentNode = parentNode;
	}

	//Adds a InfoNode to manage.
	public void addValue(int panelRow, int panelColumn, int textFieldRow, int textFieldColumn, String value){
		InfoNode node = new InfoNode(panelRow, panelColumn, textFieldRow, textFieldColumn, value);
		currentNode.setRight(node);
		node.setLeft(currentNode);
		currentNode = node;
	}

	// Undoes the InfoNode at the current index
	public InfoNode undo(){
		InfoNode returnedNode = new InfoNode();
		//Check for valid operation
		if (currentNode == parentNode){
			return null;
		}
		//
		returnedNode = currentNode;
		//delete the last node of the list
		currentNode = currentNode.getLeft();
		currentNode.setRight(null);

		//return the last node of the list
		return returnedNode;
	}
}
