package visual;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import data.Constants;
import mech.GenerateLevel;

@SuppressWarnings("serial")
public class GameFieldPanel extends JPanel implements Constants{
	
	private static final double kWidth = 0.8;
	private static final double kHeight = 0.95;
	public int[][][] junctions;
	private GenerateLevel genLevel;
	
	private MainPanel mainPanel;
	private int currentLevel;
	/**
	 * Game field size in cells.
	 */	
	private int cellsQ;
	
	/**
	 * One cell size in pixels.
	 */	
	private int cellSize;
	
	public GameFieldPanel(MainPanel mainPanel, int currentLevel) {
		this.mainPanel = mainPanel;
		this.currentLevel = currentLevel;
		setLayout(null);
		setBackground(Color.WHITE);
		generateNewLevel();
			calculateCellSize();
			calculateGameFieldBounds();
			drawFieldCells();
	}

	private void generateNewLevel() {
		cellsQ = (int)(Math.sqrt(currentLevel))+1;
		junctions = new int[cellsQ][cellsQ][4];
		genLevel = new GenerateLevel(cellsQ, currentLevel);
	}

	private void calculateCellSize() {
			int horCellSize = (int)(WINDOW_WIDTH * kWidth) / cellsQ;
			int vertCellSize = (int)(WINDOW_HEIGHT * kHeight) / cellsQ;
			// choose the smaller size
			cellSize = Math.min(horCellSize, vertCellSize);
	}

	private void calculateGameFieldBounds() {
		int width = cellSize*cellsQ;
		int height = width;
		
		int x = FIELD_X_CENTER-width/2;
		int y = 0;
		setBounds(x, y, width, height);
	}
	
	private void drawFieldCells() {
		int[][] field = genLevel.getGeneratedGameField();
		for (int i = 0; i < cellsQ; i++) {
			for (int j = 0; j < cellsQ; j++) {
				int a = field[i][j];
				if (a == 1){
					int cellType = genLevel.getCellType(i, j);
					defineCellJunctions(i, j, cellType);
					Cell cell = new Cell(this, cellType, i, j);
					add(cell);
				}
			}
		}
	}
	
	private void defineCellJunctions(int i, int j, int type) {
		switch (type) {
			case 0: junctions[i][j][0] = 1;
				break;
			case 1: {
				junctions[i][j][0] = 1;
				junctions[i][j][2] = 1;
			}break;
			case 2: {
				junctions[i][j][0] = 1;
				junctions[i][j][1] = 1;
			}break;
			case 3: {
				junctions[i][j][0] = 1;
				junctions[i][j][1] = 1;
				junctions[i][j][2] = 1;
			}break;
			case 4: {
				junctions[i][j][0] = 1;
				junctions[i][j][1] = 1;
				junctions[i][j][2] = 1;
				junctions[i][j][3] = 1;
			}break;
		}
	}
	
	public void rotateJunctions(int i, int j, int sign){
		int[] tempJ = new int[4];
		for (int k = 0; k < 4; k++) {
			tempJ[k] = junctions[i][j][k];
		}
		switch (sign) {
			case 1: for (int k = 0; k < 4; k++) {
				int pos = k==3? 0:k+1;
				junctions[i][j][pos] = tempJ[k];
			}break;
			
			case -1: for (int k = 3; k >= 0; k--) {
				int pos = k==0? 3:k-1;
				junctions[i][j][pos] = tempJ[k];
			}break;
		}
	}
	
	public void checkEndOfLevel(){
		if (isLevelComplete()){
			JOptionPane.showMessageDialog(mainPanel, "Level complete!", "Success", JOptionPane.INFORMATION_MESSAGE);
			mainPanel.createNewGameField(currentLevel+1, 0);
		}
	}
	
	private boolean isLevelComplete() {
		for (int i = 0; i < cellsQ; i++) {
			for (int j = 0; j < cellsQ; j++) {
				// check right neighbour
				if (i < cellsQ-1){
					if (junctions[i][j][1] == 1 && junctions[i+1][j][3] == 0)
						return false;
				}else{
					if (junctions[i][j][1] == 1)
						return false;
				}
				// check left neighbour
				if (i > 0){
					if (junctions[i][j][3] == 1 && junctions[i-1][j][1] == 0)
						return false;
				}else
					if (junctions[i][j][3] == 1)
						return false;					
				// check lower neighbour
				if (j < cellsQ-1){
					if (junctions[i][j][2] == 1 && junctions[i][j+1][0] == 0)
						return false;
				}else
					if (junctions[i][j][2] == 1)
						return false;
				// check upper neighbour
				if (j > 0){
					if (junctions[i][j][0] == 1 && junctions[i][j-1][2] == 0)
						return false;
				}else
					if (junctions[i][j][0] == 1)
						return false;					
			}
		}
		return true;
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, getWidth()-1, getHeight()-1);
		drawCellLines(g);
	}

	private void drawCellLines(Graphics g) {
		for (int i = 1; i < cellsQ; i++) {
			g.drawLine(i*cellSize, 0, i*cellSize, getHeight());
			g.drawLine(0, i*cellSize, getWidth(), i*cellSize);
		}
	}

	public MainPanel getMainPanel(){
		return mainPanel;
	}
	
	public int getCurrentLevel(){
		return currentLevel;
	}
	
	public int getCellSize(){
		return cellSize;
	}
}
