package visual;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import data.Constants;
import mech.CellMouseListener;

@SuppressWarnings("serial")
public class Cell extends JPanel implements Constants{
		
	private GameFieldPanel gf;
	private int cellType;
	private int angle;
	private Point pointIJ;

	public Cell(GameFieldPanel gf, int cellType, int i, int j) {
		this.gf = gf;
		this.cellType = cellType;
		this.pointIJ = new Point(i, j);
		for (int k = 0; k < (int)(Math.random()*4); k++) {
			rotateOnClick(1);
		}
		setLayout(null);
		setOpaque(false);
		
		addMouseListener(new CellMouseListener(this));
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(Color.BLACK);
		coverWithImage(g);
	}
	
		private void coverWithImage(Graphics g) {
			BufferedImage currentImage = gf.getMainPanel().getFileWork().getCellImage(cellType);
			currentImage = resize(currentImage);
			currentImage = rotate(currentImage);
			g.drawImage(currentImage, 0, 0, null);
		}

			public BufferedImage resize(BufferedImage img){
				Image tmp = img.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);		
				img = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
				
				Graphics2D g2d = img.createGraphics();
				g2d.drawImage(tmp, 0, 0, null);
				g2d.dispose();
				return img;
			}
	
			private BufferedImage rotate(BufferedImage cI) {
				AffineTransform at = new AffineTransform();
				at.rotate(angle*Math.PI*2/360, getWidth()/2, getHeight()/2);
				BufferedImage newImage = new BufferedImage(cI.getWidth(), cI.getHeight(), BufferedImage.TYPE_INT_ARGB);
				Graphics2D g2 = newImage.createGraphics();
				g2.drawImage(cI, at, null);
				g2.dispose();
			return newImage;
			}

	public void rotateOnClick(int i){
		gf.rotateJunctions(pointIJ.x, pointIJ.y, i);
		angle += 90*i;
		angle = angle > 270? 0:angle;
		angle = angle < 0? 270:angle;
		repaint();
		gf.checkEndOfLevel();
	}
}
