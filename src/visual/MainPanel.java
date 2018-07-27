package visual;

import java.awt.Color;
import java.awt.Component;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import data.Constants;
import mech.FileWork;

@SuppressWarnings("serial")
public class MainPanel extends JPanel implements Constants, WindowListener{
	
	private final String exitStr = "exitStr";
	
	private MainPanel mainPanel = this;
	private GameFieldPanel gameFieldPanel;
	private ButtonPanel buttonPanel;
	private Screen screen;
	private FileWork fileWork = new FileWork(this);
	private int maxLevel;
	
	public MainPanel(Screen screen) {
		this.screen = screen;
		setLayout(null);
		setBackground(Color.WHITE);
		
		buttonPanel = new ButtonPanel(this);
		add(buttonPanel);
				
		getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ESCAPE"), exitStr);
		getActionMap().put(exitStr, exitAction);
		
		if (fileWork.isSaveFileExists())
			loadGameIfSaveFileExists();
		else
			createNewGameField(0, 0);
	}

	private void loadGameIfSaveFileExists() {
		int currentLevel = 0, maxLevel = 0;		
		try {
			Point levels = fileWork.loadCurrentLevelFromSaveFile();
			currentLevel = levels.x;
			maxLevel = levels.y;
		} catch (IOException e) {
			e.printStackTrace();
		}
		createNewGameField(currentLevel, maxLevel);
	}

	private Action exitAction = new AbstractAction() {
		@Override
		public void actionPerformed(ActionEvent e) {
			windowClosing(null);
		}
	};
	
	public FileWork getFileWork(){
		return fileWork;
	}
	/**
	 * 
	 * @param currentLevel - if 0 -> resets current level to START_LEVEL;
	 * @param maxLevel - if 0 -> checks if new max level reached.
	 */
	public void createNewGameField(int currentLevel, int maxLevel){
		currentLevel = (currentLevel == 0)? START_LEVEL:currentLevel;
		if (maxLevel == 0 && currentLevel >= this.maxLevel){
			maxLevel = currentLevel;
		}
		this.maxLevel = maxLevel;
		for (Component c: getComponents()) {
			if (c instanceof GameFieldPanel)
				remove(c);
		}
		gameFieldPanel = new GameFieldPanel(this, currentLevel);
		add(gameFieldPanel);
		buttonPanel.setCurrentLevelText(String.valueOf(currentLevel));
		buttonPanel.setMaxLevelText(maxLevel);
		repaint();
	}
	
	public int getMaxLevel(){
		return maxLevel;
	}

	public void deleteGameField() {
		for (Component c : getComponents()) {
			if (c instanceof GameFieldPanel){
				remove(c);
				gameFieldPanel = null;
			}
		}
		repaint();
	}
	
	public ButtonPanel getButtonPanel(){
		return buttonPanel;
	}
	
	public boolean isGameFieldPanelEmpty(){
		if (gameFieldPanel == null)
			return true;
		else
			return false;
	}
	
	public void addComponentToLayer(JComponent c, int i){
		screen.addComponentToLayer(c, i);
	}
	
	public GameFieldPanel getGameFieldPanel(){
		return gameFieldPanel;
	}

	@Override
	public void windowClosing(WindowEvent e) {
		int answer = JOptionPane.showConfirmDialog(mainPanel, "Save game before exit?", "Exit confirmation", 
			JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
		switch (answer) {
		case JOptionPane.YES_OPTION: 
			try {
				fileWork.saveLevel(gameFieldPanel.getCurrentLevel(), maxLevel);
				System.exit(0);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			break;
		case JOptionPane.NO_OPTION:
			System.exit(0);
			break;
		}		
	}

	@Override
	public void windowOpened(WindowEvent e) {}

	@Override
	public void windowClosed(WindowEvent e) {}

	@Override
	public void windowIconified(WindowEvent e) {}

	@Override
	public void windowDeiconified(WindowEvent e) {}

	@Override
	public void windowActivated(WindowEvent e) {}

	@Override
	public void windowDeactivated(WindowEvent e) {}
}