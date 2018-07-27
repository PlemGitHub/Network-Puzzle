package mech;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;

import data.Constants;
import visual.ButtonPanel;

public class ButtonActions implements Constants{

	ButtonPanel corePanel;
	
	public ButtonActions(ButtonPanel corePanel) {
		this.corePanel = corePanel;
	}
	
	public Action[] getActions(){
		Action[] actions = {resetLevelsAction};
		return actions;
	}
	
	@SuppressWarnings("serial")
	private Action resetLevelsAction = new AbstractAction() {
		@Override
		public void actionPerformed(ActionEvent e) {
			int answer = JOptionPane.showConfirmDialog(corePanel.getMainPanel(), "Press \"Yes\" to reset all progress", 
					"Reset confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (answer == JOptionPane.YES_OPTION)
				corePanel.getMainPanel().createNewGameField(0, START_LEVEL);
		}
	};
}
