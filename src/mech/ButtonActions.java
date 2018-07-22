package mech;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import visual.ButtonPanel;

public class ButtonActions {

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
			corePanel.getMainPanel().createNewGameField(0);
		}
	};
}
