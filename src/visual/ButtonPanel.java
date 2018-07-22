package visual;

import java.awt.Color;
import javax.swing.Action;
import javax.swing.JPanel;
import data.Constants;
import mech.ButtonActions;
import visual.myComponents.MyButton;
import visual.myComponents.MyLabel;

@SuppressWarnings("serial")
public class ButtonPanel extends JPanel implements Constants{
	
	private MainPanel mainPanel;
	
	private MyButton resetLevelsBtn = new MyButton();
	private MyLabel levelLbl = new MyLabel();
	private Action[] buttonActions = new ButtonActions(this).getActions();
	
	public ButtonPanel(MainPanel mainPanel){
		this.mainPanel = mainPanel;
		setLayout(null);
		setSize(BUTTON_PANEL_DIMENSION);
		setBackground(Color.ORANGE);
		
		addElements();
	}

	private void addElements() {
			resetLevelsBtn.setAction(buttonActions[0]);
			resetLevelsBtn.setText("Reset levels");
			resetLevelsBtn.setLocation(BUTTON_PANEL_ELEMENTS_POSITION[0]);
			add(resetLevelsBtn);
			
			levelLbl.setLocation(BUTTON_PANEL_ELEMENTS_POSITION[1]);
			add(levelLbl);
	}
	
	public MainPanel getMainPanel(){
		return mainPanel;
	}
	
	public void setCurrentLevelText(int level){
		levelLbl.setText(String.valueOf(level));
	}
}
