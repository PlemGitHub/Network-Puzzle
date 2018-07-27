package visual;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import javax.swing.Timer;

import data.Constants;
import mech.CellMouseListener;

@SuppressWarnings("serial")
public class Cell extends JPanel implements Constants, ActionListener{
		
	private GameFieldPanel gf;
	private int angle;
	private Point pointIJ;
	private Timer rotateTimer = new Timer(0, null);
	private int doRotations;
	private int rotationSign = 1;
	private BufferedImage startCellImage, currentImage;

	public Cell(GameFieldPanel gf, int cellType, int i, int j) {
		this.gf = gf;
		this.pointIJ = new Point(i, j);
		int cellSize = gf.getCellSize();
		setBounds(cellSize*i, cellSize*j, cellSize, cellSize);
		
		startCellImage = gf.getMainPanel().getFileWork().getCellImage(cellType);	
		startCellImage = resize(startCellImage);
		currentImage = startCellImage;
		
			int rnd = (int)(Math.random()*4)+1;
			for (int k = 0; k < rnd; k++) {
				preRotate();
			}
		
		setLayout(null);
		setOpaque(false);
		
		addMouseListener(new CellMouseListener(this));
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(Color.BLACK);
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
	
			private BufferedImage rotate(int dAngle) {
				angle += dAngle;
				AffineTransform at = new AffineTransform();
				at.rotate(angle*Math.PI*2/360, getWidth()/2, getHeight()/2);
				BufferedImage newImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
				Graphics2D g2 = newImage.createGraphics();
				g2.drawImage(startCellImage, at, null);
				g2.dispose();
			return newImage;
			}

	public void rotateOnClick(int i){
		gf.rotateJunctions(pointIJ.x, pointIJ.y, i);
		
		rotationSign = i;
		doRotations++;
		if (!rotateTimer.isRunning()){
			rotateTimer = new Timer(TIMER_DELAY, this);
			rotateTimer.start();
		}
	}
	
	private void preRotate(){
		gf.rotateJunctions(pointIJ.x, pointIJ.y, 1);
		currentImage = rotate(90);
	}
	
		// rotate Timer action
		@Override
		public void actionPerformed(ActionEvent e) {
			currentImage = rotate(rotationSign*D_ANGLE);
			if (angle % 90 == 0) {
				doRotations--;
				if (doRotations == 0){
					rotateTimer.stop();
					repaint();
					gf.checkEndOfLevel();
				}
			}
			repaint();
		}
}
