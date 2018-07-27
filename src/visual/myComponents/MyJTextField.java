package visual.myComponents;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.regex.Pattern;
import javax.swing.JTextField;
import data.Constants;
import visual.ButtonPanel;

@SuppressWarnings("serial")
public class MyJTextField extends JTextField implements Constants, KeyListener{
	
	private ButtonPanel buttonPanel;

	public MyJTextField(ButtonPanel buttonPanel) {
		this.buttonPanel = buttonPanel;
		setLocation(BUTTON_PANEL_ELEMENTS_POSITION[1]);
		setSize(LABEL_DIMENSION);
		setHorizontalAlignment(JTextField.CENTER);
		addKeyListener(this);
		setVisible(false);
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		if (e.getKeyChar()=='\n'){
			if (Pattern.matches("\\d+", getText())){
				if (Integer.valueOf(getText()) < START_LEVEL)
					setText(String.valueOf(START_LEVEL));
				if (Integer.valueOf(getText()) > buttonPanel.getMainPanel().getMaxLevel())
					setText(String.valueOf(buttonPanel.getMainPanel().getMaxLevel()));
				
				setVisible(false);
				buttonPanel.showCurrentLevelLabel();
				buttonPanel.setCurrentLevelText(getText());
				int newLevel = Integer.valueOf(buttonPanel.getCurrentLevelText());
				int currentLevel = buttonPanel.getMainPanel().getGameFieldPanel().getCurrentLevel();
				if (newLevel != currentLevel){
					buttonPanel.getMainPanel().createNewGameField(newLevel, buttonPanel.getMainPanel().getMaxLevel());
				}
			}
		}
		
		char c = e.getKeyChar();
		if ( ((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
			e.consume();  // ignore event
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
	}

	@Override
	public void keyReleased(KeyEvent e) {}
}
