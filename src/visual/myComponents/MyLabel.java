package visual.myComponents;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JButton;
import javax.swing.JLabel;
import data.Constants;

@SuppressWarnings("serial")
public class MyLabel extends JLabel implements Constants{
	
	// default
	private double heightK = 0.7;
	private double widthK = 0.7;
	
	/**
	 * Enable text-checking to fit exactly "&lthtml&gt&ltcenter&gtbla&ltbr&gtbla&ltbr&gtbla&lt/center&gt&lt/html&gt" format,
	 * where &ltbr&gt tags are optional. <br>
	 * If <b>True</b> - calculates line quantity and finds the longest line.
	 */
	private final boolean checkHTMLcode = true;
	private int lines = 1;
	private String longestLine = "";

	public MyLabel() {
		setSize(LABEL_DIMENSION);
		setBackground(Color.YELLOW);
		
		setFocusable(false);
		setVerticalAlignment(JButton.CENTER);
		setHorizontalAlignment(JButton.CENTER);
	}
	
	@Override
	public void setText(String text) {
		if (text != null){
			if (checkHTMLcode)
				calculateLinesAndMaxLettersLine(text);
			defineFont();
			super.setText(text);
		}
	}

	private String calculateLinesAndMaxLettersLine(String text) {
		if (isHTMLenabled(text)){
			lines = 1;
			ArrayList<String> linesList = new ArrayList<>();
			String textWOhtml = text.substring(14, text.length()-16);
			int indexBRtag;
			while ((indexBRtag = textWOhtml.lastIndexOf("<br>")) != -1) {
				lines++;
				String currentLine = textWOhtml.substring(0, indexBRtag);
				linesList.add(currentLine);
				textWOhtml = textWOhtml.substring(indexBRtag+4, textWOhtml.length());
			}
			linesList.add(textWOhtml);
			
			for (String nextLine : linesList) {
				if (nextLine.length() > longestLine.length())
					longestLine = nextLine;
			}
		}
		return text;
	}

		private boolean isHTMLenabled(String text) {
			Pattern p = Pattern.compile("<[Hh][Tt][Mm][Ll]><center>.*</center></[Hh][Tt][Mm][Ll]>");
			Matcher m = p.matcher(text);
		return m.matches();
	}

		protected void defineFont() {
			int size = defineFontSize();
			setFont(new Font("", Font.PLAIN, size));
		}

			private int defineFontSize() {
				int size = 0;
				do {
					size++;
				} while (size < getHeight()*heightK/lines);
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
		g.drawRect(0, 0, getWidth()-1, getHeight()-1);
	}
	
	private Rectangle2D getTextBound(Graphics g){
		String text = checkHTMLcode? longestLine:getText();
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
	
	public void setHeightK(double k){
		heightK = k;
	}
	
	public void setWidthK(double k){
		widthK = k;
	}
}
