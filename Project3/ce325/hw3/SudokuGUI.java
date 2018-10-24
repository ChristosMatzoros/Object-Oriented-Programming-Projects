/*Sudoku Puzzle
 * The user can use either the on screen buttons or ALT + the equivalent keyboard jButtonSolution
 * For example instead of pressing 1 the user can press ALT + 1
 * For non aritmetic buttons the key bindings are as follows:
 * Erase Button --> ALT + Delete
 * Undo Button --> ALT + Backspace
 * Solution Button --> ALT + S
*/
package ce325.hw3;

import java.net.*;
import java.io.*;

import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import javax.swing.border.*;

public class SudokuGUI extends JFrame {

	public SudokuGUI() {
		initComponents();
	}

	private void initComponents() {
		sudokuPanel = new SudokuPanel[height][width];	//a 3x3 array of sudokuPanel items that contain 9 textfields
														//in a 3x3 array as well as some more information about them
		buttonsPanel = new JPanel();					//JPanel that contain  the buttons and the checkbox

		//initialization of the button variables and the checkbox
		jButton1 = new JButton();
		jButton2 = new JButton();
		jButton3 = new JButton();
		jButton4 = new JButton();
		jButton5 = new JButton();
		jButton6 = new JButton();
		jButton7 = new JButton();
		jButton8 = new JButton();
		jButton9 = new JButton();
		jButtonEraser = new JButton();
		jButtonUndo = new JButton();
		checkbox1 = new Checkbox();
		jButtonSolution = new JButton();

		setTitle("Sudoku Puzzle");

		//menu creation
		menubar = new JMenuBar();
		setJMenuBar(menubar);
		newGame = new JMenu("New Game");
		menubar.add(newGame);

		easy = new JMenuItem("Easy");
		easy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				easyActionPerformed(evt);
			}
		});
		newGame.add(easy);

		intermediate = new JMenuItem("Intermediate");
		intermediate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				intermediateActionPerformed(evt);
			}
		});
		newGame.add(intermediate);

		expert = new JMenuItem("Expert");
		expert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				expertActionPerformed(evt);
			}
		});
		newGame.add(expert);

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setResizable(false);
		getContentPane().setBackground(new Color(238, 238, 238));



		//initialization of the sudokuPanel array
		for(int i=0; i<3; i++){
			for(int j=0; j<3; j++){
				sudokuPanel[i][j] = new SudokuPanel();
			}
		}

		//TextField Layout Creation

		GroupLayout panelLayout;
		JTextField textTestField = new JTextField(0);
			textTestField.addFocusListener(new FocusListener(){
			public void focusGained(FocusEvent arg0){
				arg0.getComponent().setBackground(new Color(255, 255, 200));
			}
			public void focusLost(FocusEvent arg0){
				arg0.getComponent().setBackground(new Color(255,255, 255));
			}
		});
		add(textTestField);

		for(int i=0; i<3; i++){
			for(int j=0; j<3; j++){
				sudokuPanel[i][j].getPanel().setBackground(new Color(255, 255, 255));
				sudokuPanel[i][j].getPanel().setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
				sudokuPanel[i][j].getPanel().setPreferredSize(new Dimension(160, 80));
				sudokuPanel[i][j].getPanel().setRequestFocusEnabled(false);

				for(int k=0; k<3; k++){
					for(int l=0; l<3; l++){
						sudokuPanel[i][j].textField[k][l].setEditable(false);
						sudokuPanel[i][j].textField[k][l].setBackground(new Color(255, 255, 255));
						sudokuPanel[i][j].textField[k][l].setBorder(BorderFactory.createLineBorder(new Color(180, 210, 255)));
						sudokuPanel[i][j].textField[k][l].addFocusListener(new FocusListener(){
							public void focusGained(FocusEvent evt){
								focusGainedActionPerformed(evt);
							}
							public void focusLost(FocusEvent evt){
								focusLostActionPerformed(evt);
							}
						});
					}
				}

				panelLayout = new GroupLayout(sudokuPanel[i][j].getPanel());
				sudokuPanel[i][j].getPanel().setLayout(panelLayout);
				panelLayout.setHorizontalGroup(
					panelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addGroup(panelLayout.createSequentialGroup()
						.addComponent(sudokuPanel[i][j].textField[0][0], GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
						.addGap(1, 1, 1)
						.addComponent(sudokuPanel[i][j].textField[0][1], GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
						.addGap(1, 1, 1)
						.addComponent(sudokuPanel[i][j].textField[0][2], GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE))
					.addGroup(panelLayout.createSequentialGroup()
						.addComponent(sudokuPanel[i][j].textField[1][0], GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
						.addGap(1, 1, 1)
						.addComponent(sudokuPanel[i][j].textField[1][1], GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
						.addGap(1, 1, 1)
						.addComponent(sudokuPanel[i][j].textField[1][2], GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE))
					.addGroup(panelLayout.createSequentialGroup()
						.addComponent(sudokuPanel[i][j].textField[2][0], GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
						.addGap(1, 1, 1)
						.addComponent(sudokuPanel[i][j].textField[2][1], GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
						.addGap(1, 1, 1)
						.addComponent(sudokuPanel[i][j].textField[2][2], GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE))
				);
				panelLayout.setVerticalGroup(
					panelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addGroup(panelLayout.createSequentialGroup()
						.addGroup(panelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
							.addComponent(sudokuPanel[i][j].textField[0][0], GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
							.addComponent(sudokuPanel[i][j].textField[0][1], GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
							.addComponent(sudokuPanel[i][j].textField[0][2], GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
						.addGap(1, 1, 1)
						.addGroup(panelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
							.addComponent(sudokuPanel[i][j].textField[1][0], GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
							.addComponent(sudokuPanel[i][j].textField[1][1], GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
							.addComponent(sudokuPanel[i][j].textField[1][2], GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
						.addGap(1, 1, 1)
						.addGroup(panelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
							.addComponent(sudokuPanel[i][j].textField[2][0], GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
							.addComponent(sudokuPanel[i][j].textField[2][1], GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
							.addComponent(sudokuPanel[i][j].textField[2][2], GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
						.addGap(1, 1, 1))
				);

			}
		}

		//Adding text, key bindings, and action listeners to the buttons and the checkbox

		jButton1.setText("1");
		jButton1.setMnemonic(KeyEvent.VK_1);
		jButton1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jButton1ActionPerformed(evt);
			}
		});

		jButton2.setText("2");
		jButton2.setMnemonic(KeyEvent.VK_2);
		jButton2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jButton2ActionPerformed(evt);
			}
		});

		jButton3.setText("3");
		jButton3.setMnemonic(KeyEvent.VK_3);
		jButton3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jButton3ActionPerformed(evt);
			}
		});

		jButton4.setText("4");
		jButton4.setMnemonic(KeyEvent.VK_4);
		jButton4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jButton4ActionPerformed(evt);
			}
		});

		jButton5.setText("5");
		jButton5.setMnemonic(KeyEvent.VK_5);
		jButton5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jButton5ActionPerformed(evt);
			}
		});

		jButton6.setText("6");
		jButton6.setMnemonic(KeyEvent.VK_6);
		jButton6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jButton6ActionPerformed(evt);
			}
		});

		jButton7.setText("7");
		jButton7.setMnemonic(KeyEvent.VK_7);
		jButton7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jButton7ActionPerformed(evt);
			}
		});

		jButton8.setText("8");
		jButton8.setMnemonic(KeyEvent.VK_8);
		jButton8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jButton8ActionPerformed(evt);
			}
		});

		jButton9.setText("9");
		jButton9.setMnemonic(KeyEvent.VK_9);
		jButton9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jButton9ActionPerformed(evt);
			}
		});


		jButtonEraser.setIcon(new javax.swing.ImageIcon(new javax.swing.ImageIcon("eraser.png").getImage().getScaledInstance(20, 20, java.awt.Image.SCALE_DEFAULT)));
		jButtonEraser.setMnemonic(KeyEvent.VK_DELETE);
		jButtonEraser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jButtonEraserActionPerformed(evt);
			}
		});

		jButtonUndo.setIcon(new javax.swing.ImageIcon(new javax.swing.ImageIcon("undo.png").getImage().getScaledInstance(20, 20, java.awt.Image.SCALE_DEFAULT)));
		jButtonUndo.setMnemonic(KeyEvent.VK_BACK_SPACE);
		jButtonUndo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jButtonUndoActionPerformed(evt);
			}
		});

		checkbox1.setFont(new Font("Dialog", 1, 14)); // NOI18N
		checkbox1.setLabel("Verify against solution");
		checkbox1.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				checkboxStateChanged(e);
			}
		});

		jButtonSolution.setIcon(new javax.swing.ImageIcon(new javax.swing.ImageIcon("rubik.png").getImage().getScaledInstance(20, 20, java.awt.Image.SCALE_DEFAULT)));
		jButtonSolution.setMnemonic(KeyEvent.VK_S);
		jButtonSolution.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jButtonSolutionActionPerformed(evt);
			}
		});

		//Button Layout Creation

		GroupLayout buttonsPanelLayout = new GroupLayout(buttonsPanel);
		buttonsPanel.setLayout(buttonsPanelLayout);
		buttonsPanel.setBackground(new Color(238, 238, 238));
		buttonsPanelLayout.setHorizontalGroup(
		buttonsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
			.addGroup(buttonsPanelLayout.createSequentialGroup()
			.addGroup(buttonsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
				.addGroup(buttonsPanelLayout.createSequentialGroup()
					.addComponent(jButton1, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
					.addGap(1, 1, 1)
					.addComponent(jButton2, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
					.addGap(1, 1, 1)
					.addComponent(jButton3, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE))
				.addGroup(buttonsPanelLayout.createSequentialGroup()
					.addComponent(jButton9, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
					.addGap(1, 1, 1)
					.addComponent(jButtonEraser, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
					.addGap(1, 1, 1)
					.addComponent(jButtonUndo, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)))
			.addGap(0, 0, 0)
			.addGroup(buttonsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
				.addGroup(buttonsPanelLayout.createSequentialGroup()
					.addComponent(checkbox1, GroupLayout.PREFERRED_SIZE, 226, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(jButtonSolution, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE))
				.addGroup(buttonsPanelLayout.createSequentialGroup()
					.addComponent(jButton4, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
					.addGap(1, 1, 1)
					.addComponent(jButton5, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
					.addGap(1, 1, 1)
					.addComponent(jButton6, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
					.addGap(1, 1, 1)
					.addComponent(jButton7, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
					.addGap(1, 1, 1)
					.addComponent(jButton8, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)))
			.addGap(0, 0, 0))
		);
		buttonsPanelLayout.setVerticalGroup(
			buttonsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
			.addGroup(buttonsPanelLayout.createSequentialGroup()
				.addGroup(buttonsPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(jButton1, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
					.addComponent(jButton2, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
					.addComponent(jButton3, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
					.addComponent(jButton4, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
					.addComponent(jButton5, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
					.addComponent(jButton6, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
					.addComponent(jButton7, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
					.addComponent(jButton8, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
				.addGap(1, 1, 1)
				.addGroup(buttonsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addComponent(jButton9, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
					.addComponent(jButtonEraser, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
					.addComponent(jButtonUndo, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
					.addComponent(checkbox1, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
					.addComponent(jButtonSolution, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)))
		);

		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
			layout.createParallelGroup(GroupLayout.Alignment.LEADING)
			.addComponent(buttonsPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
			.addGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
					.addGroup(GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
						.addComponent(sudokuPanel[2][0].getPanel(), GroupLayout.PREFERRED_SIZE, 169, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(sudokuPanel[2][1].getPanel(), GroupLayout.PREFERRED_SIZE, 169, Short.MAX_VALUE))
					.addGroup(GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
						.addComponent(sudokuPanel[1][0].getPanel(), GroupLayout.PREFERRED_SIZE, 169, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(sudokuPanel[1][1].getPanel(), GroupLayout.PREFERRED_SIZE, 169, Short.MAX_VALUE))
					.addGroup(GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
						.addComponent(sudokuPanel[0][0].getPanel(), GroupLayout.PREFERRED_SIZE, 169, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(sudokuPanel[0][1].getPanel(), GroupLayout.PREFERRED_SIZE, 169, GroupLayout.PREFERRED_SIZE)))
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
					.addComponent(sudokuPanel[1][2].getPanel(), GroupLayout.PREFERRED_SIZE, 169, GroupLayout.PREFERRED_SIZE)
					.addComponent(sudokuPanel[0][2].getPanel(), GroupLayout.PREFERRED_SIZE, 169, GroupLayout.PREFERRED_SIZE)
					.addComponent(sudokuPanel[2][2].getPanel(), GroupLayout.PREFERRED_SIZE, 169, Short.MAX_VALUE))
				.addGap(0, 0, Short.MAX_VALUE))
		);
		layout.setVerticalGroup(
			layout.createParallelGroup(GroupLayout.Alignment.LEADING)
			.addGroup(layout.createSequentialGroup()
				.addGap(72, 72, 72)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
					.addGroup(layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
							.addGroup(GroupLayout.Alignment.TRAILING, layout.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addComponent(sudokuPanel[0][0].getPanel(), GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE)
								.addComponent(sudokuPanel[0][1].getPanel(), GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE))
							.addComponent(sudokuPanel[0][2].getPanel(), GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
							.addComponent(sudokuPanel[1][0].getPanel(), GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE)
							.addComponent(sudokuPanel[1][2].getPanel(), GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE)))
					.addComponent(sudokuPanel[1][1].getPanel(), GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addComponent(sudokuPanel[2][2].getPanel(), GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE)
					.addComponent(sudokuPanel[2][1].getPanel(), GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE)
					.addComponent(sudokuPanel[2][0].getPanel(), GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE))
				.addGap(30, 30, 30)
				.addComponent(buttonsPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
				.addGap(0, 0, 0))
		);
		pack();

		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension dim = tk.getScreenSize();
		int xPos = (dim.width / 2) - (getWidth() / 2);
		int yPos = (dim.height / 2) - (getHeight() / 2);
		setLocation(xPos, yPos);
	}


	//Check if the position of the number is valid without collisions for the Sudoku solver algorithm
	public boolean validPosition(int rowNumber, int numColumn, int number) {
		// Check for the same 'number' in the same row
		for (int col = 0; col < 9; col++) {
			if (solutionArray[rowNumber][col] == number) {
				return false;
			}
		}

		// Check for the same 'number' in the same column
		for (int row = 0; row < 9; row++) {
			if (solutionArray[row][numColumn] == number) {
				return false;
			}
		}

		// Check for 'number' in the 3x3 box
		int boxRow = rowNumber - (rowNumber % 3);
		int boxColumn = numColumn - (numColumn % 3);

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (solutionArray[boxRow + i][boxColumn + j] == number) {
					return false;
				}
			}
		}

		return true;
	}

	//Sudoku backtrack algorithm for finding the solution
	public boolean backtrackSolveSudoku() {
		int row = 0, column = 0;
		int sizeOfRow = 9;
		int sizeOfColumn = 9;
		boolean isEmpty = false;

		for (int i = 0; (i < sizeOfRow) && (!isEmpty); i++) {
			for (int j = 0; (j < sizeOfColumn) && (!isEmpty); j++) {
				if (solutionArray[i][j] == 0) {
					isEmpty = true;
					row = i;
					column = j;
				}
			}
		}

		if (!isEmpty) {
			return true;
		}

		for (int number = 1; number < 10; number++) {
			if (validPosition(row, column, number)) {
				solutionArray[row][column] = number;
				if (backtrackSolveSudoku()) {
					return true;
				}
				solutionArray[row][column] = 0;
			}
		}

		return false;
	}

	//solves the sudoku game
	public void sudokuSolver(){
		for(int i=0; i<3; i++){
			for(int j=0; j<3; j++){
				for(int k=0; k<3; k++){
					for(int l=0; l<3; l++){
						if(sudokuPanel[i][j].textField[k][l].getText().equals("")){
							solutionArray[(3*i)+k][(3*j)+l] = 0;
						}
						else{
							solutionArray[(3*i)+k][(3*j)+l] = Integer.parseInt(sudokuPanel[i][j].textField[k][l].getText());
						}
					}
				}
			}
		}

		if (!backtrackSolveSudoku()) {
			System.out.println("This Sudoku game can't be solved.");
			return;
		}
	}

	//listener for the easy game mode
	private void easyActionPerformed(ActionEvent evt) {

		try{
			//read the input from the website and create the game instance
			URL url = new URL("http://gthanos.inf.uth.gr/~gthanos/sudoku/exec.php?difficulty=easy");
			URLConnection connection = url.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(
			connection.getInputStream()));
			String inputLine;
			Character c;
			manager = new ActionManager();
			lastTextFieldClicked = null;
			for(int i=0; i<3; i++){
				for(int k=0; k<3; k++){
					// read each line
					inputLine = in.readLine();
					for(int j=0; j<3; j++){
						for(int l=0; l<3; l++){
							c = inputLine.charAt(l+(3*j));
							if(c != '.'){
								sudokuPanel[i][j].textField[k][l].setText(c.toString());
								sudokuPanel[i][j].textField[k][l].setHorizontalAlignment(JTextField.CENTER);
								sudokuPanel[i][j].textField[k][l].setBackground(new Color(229, 229, 229));
								sudokuPanel[i][j].status[k][l] = 1; //GRAY
							}
							else{
								sudokuPanel[i][j].textField[k][l].setText("");
								sudokuPanel[i][j].textField[k][l].setBackground(new Color(255, 255, 255));
								sudokuPanel[i][j].status[k][l] = 0; //WHITE
							}
						}
					}
				}
			}

			jButton1.setEnabled(true);
			jButton2.setEnabled(true);
			jButton3.setEnabled(true);
			jButton4.setEnabled(true);
			jButton5.setEnabled(true);
			jButton6.setEnabled(true);
			jButton7.setEnabled(true);
			jButton8.setEnabled(true);
			jButton9.setEnabled(true);
			jButtonEraser.setEnabled(true);
			jButtonUndo.setEnabled(true);
			checkbox1.setEnabled(true);
			jButtonSolution.setEnabled(true);

			sudokuSolver();
			in.close();
		}
		catch(MalformedURLException ex1){
			ex1.printStackTrace();
		}
		catch(IOException ex2){
			ex2.printStackTrace();
		}
	}

	//listener for the intermediate game mode
	private void intermediateActionPerformed(ActionEvent evt) {

		try{
			//read the input from the website and create the game instance
			URL url = new URL("http://gthanos.inf.uth.gr/~gthanos/sudoku/exec.php?difficulty=intermediate");
			URLConnection connection = url.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(
			connection.getInputStream()));
			String inputLine;
			Character c;
			manager = new ActionManager();
			lastTextFieldClicked = null;

			for(int i=0; i<3; i++){
				for(int k=0; k<3; k++){
					if((inputLine = in.readLine()) != null){//////////// read each line   ///////////////
						for(int j=0; j<3; j++){
							for(int l=0; l<3; l++){
								c = inputLine.charAt(l+(3*j));
								if(c != '.'){
									sudokuPanel[i][j].textField[k][l].setText(c.toString());
									sudokuPanel[i][j].textField[k][l].setHorizontalAlignment(JTextField.CENTER);
									sudokuPanel[i][j].textField[k][l].setBackground(new Color(229, 229, 229));
									sudokuPanel[i][j].status[k][l] = 1; //GRAY
								}
								else{
									sudokuPanel[i][j].textField[k][l].setText("");
									sudokuPanel[i][j].textField[k][l].setBackground(new Color(255, 255, 255));
									sudokuPanel[i][j].status[k][l] = 0; //WHITE
								}
							}
						}
					}
				}
			}

			jButton1.setEnabled(true);
			jButton2.setEnabled(true);
			jButton3.setEnabled(true);
			jButton4.setEnabled(true);
			jButton5.setEnabled(true);
			jButton6.setEnabled(true);
			jButton7.setEnabled(true);
			jButton8.setEnabled(true);
			jButton9.setEnabled(true);
			jButtonEraser.setEnabled(true);
			jButtonUndo.setEnabled(true);
			checkbox1.setEnabled(true);
			jButtonSolution.setEnabled(true);

			sudokuSolver();

			in.close();
		}
		catch(MalformedURLException ex1){
			ex1.printStackTrace();
		}
		catch(IOException ex2){
			ex2.printStackTrace();
		}
	}

	//listener for the expert game mode
	private void expertActionPerformed(ActionEvent evt) {

		try{
			//read the input from the website and create the game instance
			URL url = new URL("http://gthanos.inf.uth.gr/~gthanos/sudoku/exec.php?difficulty=expert");
			URLConnection connection = url.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(
			connection.getInputStream()));
			String inputLine;
			Character c;
			manager = new ActionManager();
			lastTextFieldClicked = null;
			for(int i=0; i<3; i++){
				for(int k=0; k<3; k++){
					if((inputLine = in.readLine()) != null){//////////// read each line   ///////////////
						for(int j=0; j<3; j++){
							for(int l=0; l<3; l++){
								c = inputLine.charAt(l+(3*j));
								if(c != '.'){
									sudokuPanel[i][j].textField[k][l].setText(c.toString());
									sudokuPanel[i][j].textField[k][l].setHorizontalAlignment(JTextField.CENTER);
									sudokuPanel[i][j].textField[k][l].setBackground(new Color(229, 229, 229));
									sudokuPanel[i][j].status[k][l] = 1; //GRAY
								}
								else{
									sudokuPanel[i][j].textField[k][l].setText("");
									sudokuPanel[i][j].textField[k][l].setBackground(new Color(255, 255, 255));
									sudokuPanel[i][j].status[k][l] = 0; //WHITE
								}
							}
						}
					}
				}
			}

			jButton1.setEnabled(true);
			jButton2.setEnabled(true);
			jButton3.setEnabled(true);
			jButton4.setEnabled(true);
			jButton5.setEnabled(true);
			jButton6.setEnabled(true);
			jButton7.setEnabled(true);
			jButton8.setEnabled(true);
			jButton9.setEnabled(true);
			jButtonEraser.setEnabled(true);
			jButtonUndo.setEnabled(true);
			checkbox1.setEnabled(true);
			jButtonSolution.setEnabled(true);

			sudokuSolver();
			in.close();
		}
		catch(MalformedURLException ex1){
			ex1.printStackTrace();
		}
		catch(IOException ex2){
			ex2.printStackTrace();
		}
	}

	//focus listener for the text fields
	private void focusGainedActionPerformed(FocusEvent evt) {
		JTextField testfield = (JTextField)evt.getSource();

		if(testfield.getText().equals("")){
			for(int i=0; i<3; i++){
				for(int j=0; j<3; j++){
					for(int k=0; k<3; k++){
						for(int l=0; l<3; l++){
							if(sudokuPanel[i][j].status[k][l] == 1){
								sudokuPanel[i][j].textField[k][l].setBackground(new Color(229,229,229));
							}
							else if(sudokuPanel[i][j].status[k][l] == 0){
								sudokuPanel[i][j].textField[k][l].setBackground(new Color(255,255,255));
							}
						}
					}
				}
			}
			if(verifyFlag == 1){
				checkForMistakes();
			}
			lastTextFieldClicked = testfield;
			testfield.setBackground(new Color(255, 255, 200));
			return;
		}
		for(int i=0; i<3; i++){
			for(int j=0; j<3; j++){
				for(int k=0; k<3; k++){
					for(int l=0; l<3; l++){
						if((testfield == sudokuPanel[i][j].textField[k][l]) && (sudokuPanel[i][j].status[k][l] == 1)){
							return;
						}
					}
				}
			}
		}

		for(int i=0; i<3; i++){
			for(int j=0; j<3; j++){
				for(int k=0; k<3; k++){
					for(int l=0; l<3; l++){
						if(sudokuPanel[i][j].status[k][l] == 1){
							sudokuPanel[i][j].textField[k][l].setBackground(new Color(229,229,229));
							if(sudokuPanel[i][j].textField[k][l].getText().equals(testfield.getText())){
								sudokuPanel[i][j].textField[k][l].setBackground(new Color(255, 255, 200));
							}
						}
						else if(sudokuPanel[i][j].status[k][l] == 0){
							sudokuPanel[i][j].textField[k][l].setBackground(new Color(255,255,255));
							if(sudokuPanel[i][j].textField[k][l].getText().equals(testfield.getText())){
								sudokuPanel[i][j].textField[k][l].setBackground(new Color(255, 255, 200));
								if(testfield == sudokuPanel[i][j].textField[k][l]){
									if(sudokuPanel[i][j].status[k][l] == 1){
										lastTextFieldClicked = null;
									}
									else if(sudokuPanel[i][j].status[k][l] == 0){
										lastTextFieldClicked = testfield;
									}
								}
							}
						}
					}
				}
			}
		}
		if(verifyFlag == 1){
			checkForMistakes();
		}
	}
	private void focusLostActionPerformed(FocusEvent evt) {

		for(int i=0; i<3; i++){
			for(int j=0; j<3; j++){
				for(int k=0; k<3; k++){
					for(int l=0; l<3; l++){
						if(sudokuPanel[i][j].status[k][l] == 0){
							sudokuPanel[i][j].textField[k][l].setBackground(new Color(255,255,255));
						}
						else if(sudokuPanel[i][j].status[k][l] == 1){
							sudokuPanel[i][j].textField[k][l].setBackground(new Color(229,229,229));
						}
					}
				}
			}
		}
		if(verifyFlag == 1){
			checkForMistakes();
		}
	}

	//action listener for the button 1
	private void jButton1ActionPerformed(ActionEvent evt) {
		int returnValue;

		if(lastTextFieldClicked != null && lastTextFieldClicked.getText().equals("")){
			if(verifyFlag == 1){
				checkForMistakes();
			}
			returnValue = checkForInvalidOperation("1");

			if(returnValue == 0){
				int[] cords = new int[4];
				cords = findCordinates();
				manager.addValue(cords[0], cords[1], cords[2], cords[3], lastTextFieldClicked.getText());
				lastTextFieldClicked.setText("1");
				lastTextFieldClicked.setHorizontalAlignment(JTextField.CENTER);
				lastTextFieldClicked = null;
				if(verifyFlag == 1){
					checkForMistakes();
				}
			}
		}
		checkForWin();
	}

	//action listener for the button 2
	private void jButton2ActionPerformed(ActionEvent evt) {
		int returnValue;

		if(lastTextFieldClicked != null && lastTextFieldClicked.getText().equals("")){
			if(verifyFlag == 1){
				checkForMistakes();
			}

			returnValue = checkForInvalidOperation("2");
			if(returnValue == 0){
				int[] cords = new int[4];
				cords = findCordinates();

				manager.addValue(cords[0], cords[1], cords[2], cords[3], lastTextFieldClicked.getText());
			lastTextFieldClicked.setText("2");
				lastTextFieldClicked.setHorizontalAlignment(JTextField.CENTER);
				lastTextFieldClicked = null;
				if(verifyFlag == 1){
					checkForMistakes();
				}
			}
		}
		checkForWin();
	}

	//action listener for the button 3
	private void jButton3ActionPerformed(ActionEvent evt) {
		int returnValue;

		if(lastTextFieldClicked != null && lastTextFieldClicked.getText().equals("")){
			if(verifyFlag == 1){
				checkForMistakes();
			}
			returnValue = checkForInvalidOperation("3");

			if(returnValue == 0){
				int[] cords = new int[4];
				cords = findCordinates();
				manager.addValue(cords[0], cords[1], cords[2], cords[3], lastTextFieldClicked.getText());
				lastTextFieldClicked.setText("3");
				lastTextFieldClicked.setHorizontalAlignment(JTextField.CENTER);
				lastTextFieldClicked = null;
				if(verifyFlag == 1){
					checkForMistakes();
				}
			}
		}
		checkForWin();
	}

	//action listener for the button 4
	private void jButton4ActionPerformed(ActionEvent evt) {
		int returnValue;

		if(lastTextFieldClicked != null && lastTextFieldClicked.getText().equals("")){
			if(verifyFlag == 1){
				checkForMistakes();
			}
			returnValue = checkForInvalidOperation("4");

			if(returnValue == 0){
				int[] cords = new int[4];
				cords = findCordinates();
				manager.addValue(cords[0], cords[1], cords[2], cords[3], lastTextFieldClicked.getText());
				lastTextFieldClicked.setText("4");
				lastTextFieldClicked.setHorizontalAlignment(JTextField.CENTER);
				lastTextFieldClicked = null;
				if(verifyFlag == 1){
					checkForMistakes();
				}
			}
		}
		checkForWin();
	}

	//action listener for the button 5
	private void jButton5ActionPerformed(ActionEvent evt) {
		int returnValue;

		if(lastTextFieldClicked != null && lastTextFieldClicked.getText().equals("")){
			if(verifyFlag == 1){
				checkForMistakes();
			}
			returnValue = checkForInvalidOperation("5");

			if(returnValue == 0){
				int[] cords = new int[4];
				cords = findCordinates();
				manager.addValue(cords[0], cords[1], cords[2], cords[3], lastTextFieldClicked.getText());
				lastTextFieldClicked.setText("5");
				lastTextFieldClicked.setHorizontalAlignment(JTextField.CENTER);
				lastTextFieldClicked = null;
				if(verifyFlag == 1){
					checkForMistakes();
				}
			}
		}
		checkForWin();
	}

	//action listener for the button 6
	private void jButton6ActionPerformed(ActionEvent evt) {
		int returnValue;

		if(lastTextFieldClicked != null && lastTextFieldClicked.getText().equals("")){
			if(verifyFlag == 1){
				checkForMistakes();
			}
			returnValue = checkForInvalidOperation("6");

			if(returnValue == 0){
				int[] cords = new int[4];
				cords = findCordinates();
				manager.addValue(cords[0], cords[1], cords[2], cords[3], lastTextFieldClicked.getText());
				lastTextFieldClicked.setText("6");
				lastTextFieldClicked.setHorizontalAlignment(JTextField.CENTER);
				lastTextFieldClicked = null;
				if(verifyFlag == 1){
					checkForMistakes();
				}
			}
		}
		checkForWin();
	}

	//action listener for the button 7
	private void jButton7ActionPerformed(ActionEvent evt) {
		int returnValue;

		if(lastTextFieldClicked != null && lastTextFieldClicked.getText().equals("")){
			if(verifyFlag == 1){
				checkForMistakes();
			}
			returnValue = checkForInvalidOperation("7");

			if(returnValue == 0){
				int[] cords = new int[4];
				cords = findCordinates();
				manager.addValue(cords[0], cords[1], cords[2], cords[3], lastTextFieldClicked.getText());
				lastTextFieldClicked.setText("7");
				lastTextFieldClicked.setHorizontalAlignment(JTextField.CENTER);
				lastTextFieldClicked = null;
				if(verifyFlag == 1){
					checkForMistakes();
				}
			}
		}
		checkForWin();
	}

	//action listener for the button 8
	private void jButton8ActionPerformed(ActionEvent evt) {
		int returnValue;

		if(lastTextFieldClicked != null && lastTextFieldClicked.getText().equals("")){
			if(verifyFlag == 1){
				checkForMistakes();
			}
			returnValue = checkForInvalidOperation("8");

			if(returnValue == 0){
				int[] cords = new int[4];
				cords = findCordinates();
				manager.addValue(cords[0], cords[1], cords[2], cords[3], lastTextFieldClicked.getText());
				lastTextFieldClicked.setText("8");
				lastTextFieldClicked.setHorizontalAlignment(JTextField.CENTER);
				lastTextFieldClicked = null;
				if(verifyFlag == 1){
					checkForMistakes();
				}
			}
		}
		checkForWin();
	}

	//action listener for the button 9
	private void jButton9ActionPerformed(ActionEvent evt) {
		int returnValue;

		if(lastTextFieldClicked != null && lastTextFieldClicked.getText().equals("")){
			if(verifyFlag == 1){
				checkForMistakes();
			}
			returnValue = checkForInvalidOperation("9");

			if(returnValue == 0){
				int[] cords = new int[4];
				cords = findCordinates();
				manager.addValue(cords[0], cords[1], cords[2], cords[3], lastTextFieldClicked.getText());
				lastTextFieldClicked.setText("9");
				lastTextFieldClicked.setHorizontalAlignment(JTextField.CENTER);
				lastTextFieldClicked = null;
				if(verifyFlag == 1){
					checkForMistakes();
				}
			}
		}
		checkForWin();
	}

	//action listener for the eraser button
	private void jButtonEraserActionPerformed(ActionEvent evt) {

		for(int i=0; i<3; i++){
			for(int j=0; j<3; j++){
				for(int k=0; k<3; k++){
					for(int l=0; l<3; l++){
						if(sudokuPanel[i][j].textField[k][l] == lastTextFieldClicked){
							if(sudokuPanel[i][j].status[k][l] == 0){
								int[] cords = new int[4];
								cords = findCordinates();
								manager.addValue(cords[0], cords[1], cords[2], cords[3], lastTextFieldClicked.getText());
								lastTextFieldClicked.setText("");
								if(verifyFlag == 1){
									checkForMistakes();
									lastTextFieldClicked.setBackground(new Color(255,255,255));
								}
								return;
							}
							else if(sudokuPanel[i][j].status[k][l] == 1){
								return;
							}
						}
					}
				}
			}
		}
	}

	//action listener for the undo button
	private void jButtonUndoActionPerformed(ActionEvent evt) {
		InfoNode info = new InfoNode();

		info = manager.undo();
		if(info == null){
			return;
		}

		sudokuPanel[info.getPanelRow()][info.getPanelColumn()].textField[info.getTextFieldRow()][info.getTextFieldColumn()].setText(info.getValue());

		lastTextFieldClicked = null;
		if(verifyFlag == 1){
			checkForMistakes();
		}
	}

	//action listener for the solution button
	private void jButtonSolutionActionPerformed(ActionEvent evt) {
		for(int i=0; i<3; i++){
			for(int j=0; j<3; j++){
				for(int k=0; k<3; k++){
					for(int l=0; l<3; l++){
						sudokuPanel[i][j].textField[k][l].setText(Integer.toString(solutionArray[(3*i)+k][(3*j)+l]));
						sudokuPanel[i][j].textField[k][l].setHorizontalAlignment(JTextField.CENTER);
						if(sudokuPanel[i][j].status[k][l] == 1){
							sudokuPanel[i][j].textField[k][l].setBackground(new Color(229,229,229));
						}
						else if(sudokuPanel[i][j].status[k][l] == 0){
							sudokuPanel[i][j].textField[k][l].setBackground(new Color(255,255,255));
						}
					}
				}
			}
		}

		//disable the buttons
		jButton1.setEnabled(false);
		jButton2.setEnabled(false);
		jButton3.setEnabled(false);
		jButton4.setEnabled(false);
		jButton5.setEnabled(false);
		jButton6.setEnabled(false);
		jButton7.setEnabled(false);
		jButton8.setEnabled(false);
		jButton9.setEnabled(false);
		jButtonEraser.setEnabled(false);
		jButtonUndo.setEnabled(false);
		checkbox1.setEnabled(false);
		jButtonSolution.setEnabled(false);

	}

	//check fot mistakes based on the solution and if found paint the corresponding textfields blue
	private void checkForMistakes(){
		for(int i=0; i<3; i++){
			for(int j=0; j<3; j++){
				for(int k=0; k<3; k++){
					for(int l=0; l<3; l++){
						if(sudokuPanel[i][j].textField[k][l].getText().equals("") || sudokuPanel[i][j].textField[k][l].getText().equals(Integer.toString(solutionArray[(3*i)+k][(3*j)+l]))){
							continue;
						}
						else{
							sudokuPanel[i][j].textField[k][l].setBackground(new Color(100, 150, 200));
						}
					}
				}
			}
		}
	}

	//listener for the checkbox
	private void checkboxStateChanged(ItemEvent e) {

		if (e.getStateChange() == ItemEvent.SELECTED){
			verifyFlag = 1;
			checkForMistakes();

		}
		else if (e.getStateChange() == ItemEvent.DESELECTED){
			verifyFlag = 0;

			for(int i=0; i<3; i++){
				for(int j=0; j<3; j++){
					for(int k=0; k<3; k++){
						for(int l=0; l<3; l++){
							if(sudokuPanel[i][j].textField[k][l].getBackground().equals(new Color(100, 150, 200))){
								sudokuPanel[i][j].textField[k][l].setBackground(new Color(255, 255, 255));
							}
						}
					}
				}
			}
		}
	}

	//check if the user tries to enter a number that already exists in the same panel row or column
	private int checkForInvalidOperation(String textFieldContent){
		int panelLine = 0;
		int panelColumn = 0;
		int textFieldLine = 0;
		int textFieldColumn = 0;
		int found = 0;

		for(int i=0; i<3; i++){
			for(int j=0; j<3; j++){
				for(int k=0; k<3; k++){
					for(int l=0; l<3; l++){
						if(sudokuPanel[i][j].textField[k][l].getBackground().equals(Color.RED)){
							sudokuPanel[i][j].textField[k][l].setBackground(new Color(255, 255, 255));
						}
					}
				}
			}
		}

		for(int i=0; i<3; i++){
			for(int j=0; j<3; j++){
				for(int k=0; k<3; k++){
					for(int l=0; l<3; l++){
						if(sudokuPanel[i][j].textField[k][l] == lastTextFieldClicked){
							sudokuPanel[i][j].textField[k][l].setBackground(new Color(255,255,255));
							panelLine = i;
							panelColumn = j;
							textFieldLine = k;
							textFieldColumn = l;
						}
					}
				}
			}
		}

		//Check for an instance of this number in the same panel
		for(int k=0; k<3; k++){
			for(int l=0; l<3; l++){
				if(sudokuPanel[panelLine][panelColumn].textField[k][l].getText().equals(textFieldContent) && !((textFieldLine == k) && (textFieldColumn == l))){
					sudokuPanel[panelLine][panelColumn].textField[k][l].setBackground(Color.RED);
					lastTextFieldClicked = null;
					found = 1;
				}
			}
		}

		//Check for an instance of this number in the same line
		for(int j=0; j<3; j++){
			for(int l=0; l<3; l++){
				if(sudokuPanel[panelLine][j].textField[textFieldLine][l].getText().equals(textFieldContent) && !((textFieldColumn == j) && (textFieldColumn == l))){
					sudokuPanel[panelLine][j].textField[textFieldLine][l].setBackground(Color.RED);
					lastTextFieldClicked = null;
					found = 1;
				}
			}
		}

		//Check for an instance of this number in the same column
		for(int i=0; i<3; i++){
			for(int k=0; k<3; k++){
				if(sudokuPanel[i][panelColumn].textField[k][textFieldColumn].getText().equals(textFieldContent) && !((textFieldLine == i) && (textFieldLine == k))){
					sudokuPanel[i][panelColumn].textField[k][textFieldColumn].setBackground(Color.RED);
					lastTextFieldClicked = null;
					found = 1;
				}
			}
		}
		if(found == 1){
			lastTextFieldClicked = null;
			return 1;
		}
		return 0;
	}

	//returns the coordinates of the last text field clicked by the user
	public int[] findCordinates(){
		int [] coordinateValues = new int[4];

		for(int i=0; i<3; i++){
			for(int j=0; j<3; j++){
				for(int k=0; k<3; k++){
					for(int l=0; l<3; l++){
						if(sudokuPanel[i][j].textField[k][l] == lastTextFieldClicked){
							coordinateValues[0] = i;
							coordinateValues[1] = j;
							coordinateValues[2] = k;
							coordinateValues[3] = l;
						}
					}
				}
			}
		}
		return coordinateValues;
	}

	//checks if all the entries are correct, and if so it pops a success message
	public void checkForWin(){

		int foundMistake = 0;

		for(int i=0; i<3; i++){
			for(int j=0; j<3; j++){
				for(int k=0; k<3; k++){
					for(int l=0; l<3; l++){
						if(sudokuPanel[i][j].textField[k][l].getText().equals(Integer.toString(solutionArray[(3*i)+k][(3*j)+l]))){
							continue;
						}
						else{
							foundMistake = 1;
						}
					}
				}
			}
		}

		if(foundMistake == 0){
			String message = "Congratulations! You won the Game!";
			JOptionPane.showMessageDialog(new JFrame(), message, "Dialog", JOptionPane.PLAIN_MESSAGE);
			jButton1.setEnabled(false);
			jButton2.setEnabled(false);
			jButton3.setEnabled(false);
			jButton4.setEnabled(false);
			jButton5.setEnabled(false);
			jButton6.setEnabled(false);
			jButton7.setEnabled(false);
			jButton8.setEnabled(false);
			jButton9.setEnabled(false);
			jButtonEraser.setEnabled(false);
			jButtonUndo.setEnabled(false);
			checkbox1.setEnabled(false);
			jButtonSolution.setEnabled(false);
		}
	}

	public static void main(String args[]) {
		//Create and display the user interface
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new SudokuGUI().setVisible(true);
			}
		});
	}

	//Variables declaration
	private static final int height = 3;
	private static final int width = 3;
	private int verifyFlag = 0;
	private JMenuBar menubar;
	private JMenu newGame;
	private JMenuItem easy;
	private JMenuItem intermediate;
	private JMenuItem expert;
	private SudokuPanel[][] sudokuPanel;
	private JPanel buttonsPanel;
	private JButton jButton1;
	private JButton jButton2;
	private JButton jButton3;
	private JButton jButton4;
	private JButton jButton5;
	private JButton jButton6;
	private JButton jButton7;
	private JButton jButton8;
	private JButton jButton9;
	private JButton jButtonEraser;
	private JButton jButtonUndo;
	private Checkbox checkbox1;
	private JButton jButtonSolution;
	private JTextField lastTextFieldClicked = null;
	private int[][] solutionArray = new int[9][9];
	ActionManager manager = new ActionManager();
}
