package visual;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.Action;
import javax.swing.JPanel;
import data.Constants;
import mech.ButtonActions;
import visual.myComponents.MyButton;
import visual.myComponents.MyJTextField;
import visual.myComponents.MyLabel;

@SuppressWarnings("serial")
public class ButtonPanel extends JPanel implements Constants, MouseListener{
	
	private MainPanel mainPanel;
	
	private MyButton resetLevelsBtn = new MyButton();
	private MyLabel currentLevelLbl = new MyLabel();
	private MyJTextField newLevelText = new MyJTextField(this);
	private MyLabel maxLevelLbl = new MyLabel();
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
			
			currentLevelLbl.setLocation(BUTTON_PANEL_ELEMENTS_POSITION[1]);
			add(currentLevelLbl);
			currentLevelLbl.addMouseListener(this);
			
			maxLevelLbl.setLocation(BUTTON_PANEL_ELEMENTS_POSITION[2]);
			maxLevelLbl.setForeground(Color.RED);
			add(maxLevelLbl);
			
			add(newLevelText);
	}
	
	public MainPanel getMainPanel(){
		return mainPanel;
	}
	
	public void setCurrentLevelText(String text){
		currentLevelLbl.setText(text);
	}
	
	public String getCurrentLevelText(){
		return currentLevelLbl.getText();
	}

	public void showCurrentLevelLabel(){
		currentLevelLbl.setVisible(true);
	}

	public void setMaxLevelText(int maxLevel) {
		maxLevelLbl.setText(String.valueOf(maxLevel));
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource().equals(currentLevelLbl)){
			
			currentLevelLbl.setVisible(false);
			newLevelText.setVisible(true);
			newLevelText.setText(currentLevelLbl.getText());
			newLevelText.setFont(currentLevelLbl.getFont());
			newLevelText.requestFocus();
			newLevelText.selectAll();
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {
		currentLevelLbl.setCursor(new Cursor(Cursor.HAND_CURSOR));
	}

	@Override
	public void mouseExited(MouseEvent e) {}
}
