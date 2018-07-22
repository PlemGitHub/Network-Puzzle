package mech;


public class GenerateLevel {
	
	private int[][] field;
	private int[][] types;
	private int[][][] neighbours;
	private int cellsQ;

	public GenerateLevel(int cellsQ, int currentLevel) {
		this.cellsQ = cellsQ;
		field = new int[cellsQ][cellsQ];
		types = new int[cellsQ][cellsQ];
		
		generateLevelDependedCells(currentLevel);
		generateConcreteLevel();
		findLonelyCellsAndCreateTheirNeighbours();
		findCellPairsAndDecreaseTheirType();
		translateNumbersToCellType();
	}

	private void generateConcreteLevel() {
		
	}

	private void generateLevelDependedCells( int currentLevel) {
		for (int k = 0; k < currentLevel; k++) {
			int i = (int)(Math.random()*cellsQ);
			int j = (int)(Math.random()*cellsQ);
			if (field[i][j] == 0){
				field[i][j] = 1;
			}else{
				k--;
			}
		}
		fillNeighboursArray();
	}

	private void findLonelyCellsAndCreateTheirNeighbours() {
		for (int i = 0; i < cellsQ; i++) {
			for (int j = 0; j < cellsQ; j++) {
				if (field[i][j] == 1){
					boolean isLonely = checkLoneliness(i, j);
					if (isLonely){
						createRandomNeighbour(i, j);
					}
				}
			}
		}
		fillNeighboursArray();
	}

		private boolean checkLoneliness(int i, int j) {
			for (int k = 0; k < 4; k++) {
				if (neighbours[i][j][k] == 1)
					return false;
			}
			return true;
		}

		private void createRandomNeighbour(int i, int j) {
			boolean creationNotFinished = true;
			do {
				int newI = i, newJ = j;
				int direction = (int)(Math.random()*4);
				switch (direction) {
					case 0: {newI -= 1;}
						break;
					case 1: {newI += 1;}
						break;
					case 2: {newJ -= 1;}
						break;
					case 3: {newJ += 1;}
						break;
				}
				if (newI < 0 || newI == cellsQ ||
					newJ < 0 || newJ == cellsQ)
						continue;
				else{
					field[newI][newJ] = 1;
					creationNotFinished = false;
				}
			} while (creationNotFinished);
			
		}

	private void translateNumbersToCellType(){
		for (int i = 0; i < cellsQ; i++) {
			for (int j = 0; j < cellsQ; j++) {
				int type = -1;
				int neighboursQ = calculateNeighbours(i, j);
				switch (neighboursQ) {
					case 1: type = 0;
						break;
					case 2: type = defineFirstOrSecond(i, j);
						break;
					case 3: type = 3;
						break;
					case 4: type = 4;
						break;
				}
				types[i][j] = type;
//				if (type == -1)
//					field[i][j] = 0;
			}
			
		}
	}

		private int calculateNeighbours(int i, int j) {
			int neighboursQ = 0;
			for (int k = 0; k < 4; k++) {
				if (neighbours[i][j][k] == 1)
					neighboursQ++;
			}
			return neighboursQ;
		}

		private int defineFirstOrSecond(int i, int j) {
			int type;
			if ((neighbours[i][j][0] == 1 && neighbours[i][j][1] == 1) ||
					(neighbours[i][j][2] == 1 && neighbours[i][j][3] == 1))
						type = 1;
			else
				type = 2;
			return type;
		}
		
	public int getCellType(int i, int j){
		return types[i][j];
	}

	private void fillNeighboursArray() {
		neighbours = new int[cellsQ][cellsQ][4];
		for (int i = 0; i < cellsQ; i++) {
			for (int j = 0; j < cellsQ; j++) {
				if (field[i][j] == 1){
					if (i-1 >= 0 && field[i-1][j] == 1)
						neighbours[i][j][0] = 1;
					if (i+1 < cellsQ && field[i+1][j] == 1)
						neighbours[i][j][1] = 1;
					if (j-1 >= 0 && field[i][j-1] == 1)
						neighbours[i][j][2] = 1;
					if (j+1 < cellsQ && field[i][j+1] == 1)
						neighbours[i][j][3] = 1;
				}
			}
		}
	}
		
	private void findCellPairsAndDecreaseTheirType() {
		int currentIteration = 0;
		int howManyThirdAndFourth = findAllThirdAndFourth();
		for (int k = 0; k < howManyThirdAndFourth; k++) {
			currentIteration++;
			if (currentIteration == 1000)
				break;
			int i = (int)(Math.random()*cellsQ);
			int j = (int)(Math.random()*cellsQ);
			int newI = i, newJ = j;
			int direction = (int)(Math.random()*4);
			int reversedDirection = 0;
			switch (direction) {
				case 0: {newI -= 1; reversedDirection = 1;} break;
				case 1: {newI += 1; reversedDirection = 0;} break;
				case 2: {newJ -= 1; reversedDirection = 3;} break;
				case 3: {newJ += 1; reversedDirection = 2;} break;
			}
			if (newI < 0 || newI == cellsQ ||
				newJ < 0 || newJ == cellsQ){
				k--;
				continue;
			}			
			if (calculateNeighbours(i, j) < 3 || calculateNeighbours(newI, newJ) < 3){
				k--;
				continue;
			}
			
			
			if (calculateNeighbours(i, j) >= 3 && calculateNeighbours(newI, newJ) >= 3){
				k++;
			}
			
			if (cellsAreConnected(i, j, newI, newJ)){
				neighbours[i][j][direction] = 0;
				neighbours[newI][newJ][reversedDirection] = 0;
			}
			else{
				k--;
				continue;
			}
		}
	}

		private boolean cellsAreConnected(int i, int j, int newI, int newJ) {
			if ((neighbours[i][j][0] == 1 && neighbours[newI][newJ][1] == 1) ||
					(neighbours[i][j][1] == 1 && neighbours[newI][newJ][0] == 1) ||
						(neighbours[newI][newJ][2] == 1 && neighbours[i][j][3] == 1) ||
							(neighbours[newI][newJ][3] == 1 && neighbours[i][j][2] == 1))
				return true;
		return false;
	}

		private int findAllThirdAndFourth() {
			int total = 0;
			for (int i = 0; i < cellsQ; i++) {
				for (int j = 0; j < cellsQ; j++) {
					int howManyNeighbours = 0;
					for (int k = 0; k < 4; k++) {
						if (neighbours[i][j][k] == 1)
							howManyNeighbours++;
					}
					if (howManyNeighbours >= 3)
						total++;
				}
			}
		return total;
	}

	public int[][] getGeneratedGameField(){
		return field;
	}
}
