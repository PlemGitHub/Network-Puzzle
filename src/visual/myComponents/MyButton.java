package visual.myComponents;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import javax.swing.JButton;
import data.Constants;

@SuppressWarnings("serial")
public class MyButton extends JButton implements Constants{
	
	private final double heightK = 0.8;
	private final double widthK = 0.78;

	public MyButton() {
		setSize(BUTTON_DIMENSION);
		setBackground(Color.YELLOW);
		
		setFocusable(false);
		setVerticalAlignment(JButton.CENTER);
		setHorizontalAlignment(JButton.CENTER);
	}
	
	@Override
	public void setText(String text) {
		super.setText(text);
		defineFont();
	}

		protected void defineFont() {
			int size = defineFontSize();
			setFont(new Font("", Font.PLAIN, size));
		}

			private int defineFontSize() {
				int size = 0;
				do {
					size++;
				} while (size < getHeight()*heightK);
				return size;
			}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		Rectangle2D bound = getTextBound(g);
		
		while (isBoundLargerThanWidthKOfWidth(bound.getWidth())) {
			setFont(new Font("", Font.PLAIN, getFont().getSize()-1));
			bound = getTextBound(g);
		}
	}
	
	private Rectangle2D getTextBound(Graphics g){
		String text = getText();
		Font f = getFont();
		FontMetrics fm = g.getFontMetrics(f);
		Rectangle2D bound = fm.getStringBounds(text, g);
		return bound;
	}
	
	private boolean isBoundLargerThanWidthKOfWidth(double textWidth){
		int textWidthInt = (int)textWidth;
		if (textWidthInt > getWidth()*widthK)
			return true;
		else
			return false;
	}
}
