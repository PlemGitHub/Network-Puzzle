package mech;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import visual.Cell;

public class CellMouseListener implements MouseListener {
	
	private Cell cell;
	
	public CellMouseListener(Cell cell) {
		this.cell = cell;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1)
			cell.rotateOnClick(1);
		if (e.getButton() == MouseEvent.BUTTON3)
			cell.rotateOnClick(-1);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

}
