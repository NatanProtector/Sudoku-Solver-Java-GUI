import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;

public class sudokuGUI extends JFrame implements ActionListener{
	boolean solved = false;
	sudokuSolver solver;
	JLabel label;
	JPanel Secondpanel;
	Panel2D panel;
	JButton button;
	JTextField text;
	JTextField[] textList;
	JFrame frame;
	public sudokuGUI() {
		solver = new sudokuSolver();
		button = new JButton("Solve");
		button.addActionListener(this);
		button.setPreferredSize(new Dimension(100,30));
		button.setFocusable(false);
		label = new JLabel("");
		panel = new Panel2D();
		textList = new JTextField[9*9];
		for (int i = 0; i<9*9 ; i++) {
			text = new JTextField();
			text.setFont(new Font("MV Boli",Font.BOLD,20));
			text.setHorizontalAlignment(JTextField.CENTER);
			text.setPreferredSize(new Dimension(30,30));
			textList[i] = text;
			textList[i].addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					label.setText("");
					for (int i = 0 ; i<textList.length ;i++) {
						if (textList[i] == e.getSource())
							textList[i].setBackground(Color.white);
					}
				}
			});
			panel.add(text);
		}
		Secondpanel = new JPanel();
		Secondpanel.setPreferredSize(new Dimension(330,100));
		Secondpanel.setLayout(new FlowLayout(FlowLayout.LEADING,50,-1));
		Secondpanel.add(button);
		Secondpanel.add(label);
		this.add(panel);
		this.add(Secondpanel);
		this.setTitle("Natan's sudoku solver");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocation(450,150);
		this.setSize(360,420);
		this.setLayout(new FlowLayout());
		this.setResizable(false);
		this.setVisible(true);
	}
	
	public boolean checkValidSudoku() {
		if (!correctNumOfClues())
			return false;
		for (int i = 0 ; i<textList.length ; i++) {
			String s = textList[i].getText();
			if (s.length() == 0)
				continue;
			if (
					s.length() != 1 ||
					!Character.isDigit(s.charAt(0)) ||
					(int)(s.charAt(0)-48) > 9
				) {
				textList[i].setBackground(Color.RED);
				return false;
			}
		}
		return true;
	}
	public int[][] generateGrid() {
		int[][] grid = new int[9][9];
		int k = 0;
		for (int i = 0 ; i<grid.length ; i++) {
			for (int j = 0 ; j< grid.length ; j++) {
				String s = textList[k].getText();
				int n;
				if (s.length() == 0)
					n = 0;
				else
					n = (int)(textList[k].getText().charAt(0)-48);
				grid[i][j] = n;
				k++;
			}
		}
		return grid;
	}
	public void displayResluts(int[][] board) {
		int k = 0;
		for (int i = 0 ; i<board.length ; i++) {
			for (int j = 0 ; j< board.length ; j++) {
				System.out.println(board[i][j]);
				this.textList[k].setText(""+board[i][j]);
				k++;
			}
		}
		this.button.setText("Reset");
	}
	public void reset() {
		for (JTextField text: textList) {
			text.setText("");
		}
		this.button.setText("Solve");
	}
	public void displayInvalid() {
		label.setForeground(Color.RED);
		label.setText("INVALID SUDOKU GRID");
	}
	public boolean correctNumOfClues() {
		int counter = 0;
		for (JTextField j : textList) {
			if (j.getText().length() != 0)
				counter++;
		}
		return counter>=17;
	}
	public void actionPerformed(ActionEvent e) {
		/**
		 * suggestion for later:
		 * using actionListener turn all the text fields into single int fields
		 * to remove unnecessary methods checks 
		 */
		if (e.getSource() == button) {
			if (solved) {
				reset();
				this.solved = false;
			} else {
				if (checkValidSudoku()) {
					int[][] grid = generateGrid();
					solver.setBoard(grid);
					if (solver.solve()) {
						int[][] solvedBoard = solver.getBoard();
						displayResluts(solvedBoard);
						this.solved = true;
					}
					else
						displayInvalid();
				} else {
					displayInvalid();
				}
			}
		}
	}
}
