package visual;

import java.awt.Frame;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import data.Constants;

public class Screen implements Constants{
	
	private JFrame fr;
	private MainPanel mp;
	private JLayeredPane jLayeredPane = new JLayeredPane();
	
	public Screen() {
		createMainPanel();
		
		fr = new JFrame("Network Puzzle");
		fr.setExtendedState(Frame.MAXIMIZED_BOTH);
		fr.setVisible(true);
		fr.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			jLayeredPane.setLayout(null);
			fr.setContentPane(jLayeredPane);
			fr.addWindowListener(mp);
	}
	
	private void createMainPanel() {
		mp = new MainPanel(this);
		mp.setSize(WINDOW);
		addComponentToLayer(mp, 0);
	}

	public static void main(String[] args) {
		@SuppressWarnings("unused")
		Screen scr = new Screen();
	}
	
	public void addComponentToLayer(JComponent c, int i){
		jLayeredPane.add(c, new Integer(i));
		jLayeredPane.repaint();
	}
}