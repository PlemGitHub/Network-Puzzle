package mech;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.imageio.ImageIO;
import data.Constants;
import visual.MainPanel;

public class FileWork implements Constants{
	private MainPanel mainPanel;
	private File saveFile;
	private BufferedImage[] cellImages = new BufferedImage[5];
	
	public FileWork(MainPanel mainPanel) {
		this.mainPanel = mainPanel;
		defineSaveFilePath();
		importCellImages();
	}
	
	public MainPanel getMainPanl(){
		return mainPanel;
	}

	private void defineSaveFilePath() {
		String tmp = System.getenv("APPDATA");
		File plemFolder = new File(tmp+"/PlemCo");
		if (!plemFolder.exists())
			plemFolder.mkdir();
		File gameFolder = new File(plemFolder+"/Network Puzzle");
		if (!gameFolder.exists())
			gameFolder.mkdir();
		saveFile = new File(gameFolder+"/save.nps");
	}

	public boolean isSaveFileExists(){
		if (saveFile.exists())
			return true;
		else
			return false;
	}
	
	public void importCellImages(){
		BufferedImage newImage = new BufferedImage(IMAGE_SIZE, IMAGE_SIZE, BufferedImage.TYPE_INT_ARGB);
		for (int i = 0; i < 5; i++) {
			try { 
				newImage = ImageIO.read(this.getClass().getResourceAsStream("/data/cell images/"+String.valueOf(i)+".png"));
			} catch (IOException ex) {
				System.out.println(ex.toString());
			}
			cellImages[i] = newImage;
		}
	}
	
	public BufferedImage getCellImage(int i){
		return cellImages[i];
	}

	public void saveLevel(int currentLevel, int maxLevel) throws IOException {
		if (!isSaveFileExists())
			saveFile.createNewFile();
		
		BufferedWriter out = new BufferedWriter(new FileWriter(saveFile));
		
		out.write(String.valueOf(currentLevel));
		out.write(System.lineSeparator());
		out.write(String.valueOf(maxLevel));
		out.close();
	}

	public Point loadCurrentLevelFromSaveFile() throws IOException {
			BufferedReader in = new BufferedReader(new FileReader(saveFile));
			int currentLevel = Integer.valueOf(in.readLine());
			int maxLevel = Integer.valueOf(in.readLine());
			in.close();
			return new Point(currentLevel, maxLevel);
//		}
	}
}
