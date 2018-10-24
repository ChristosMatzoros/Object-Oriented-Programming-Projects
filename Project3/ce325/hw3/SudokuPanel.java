package ce325.hw3;

import javax.swing.*;

//Represents a Panel that contains 9 textfields in a 3x3 array as well as some more information about them
public class SudokuPanel{
	private JPanel panel;	//the JPanel itself
	protected JTextField [][] textField;	//the array of text fields
	protected int [][] status;		//used to indicate the background color of each textField (0 for white background, 1 for gray background)
	private static final int height = 3;	//dimensions of the array of textFields
	private static final int width = 3;

	public SudokuPanel(){
		panel = new JPanel();
		textField = new JTextField[height][width];
		status = new int[height][width];

		for(int i=0; i<height; i++){
			for(int j=0; j<width; j++){
				textField[i][j] = new JTextField();
				status[i][j] = 0;
			}
		}
	}
	public JPanel getPanel(){
		return panel;
	}

}
