package data;

import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
public interface Constants {
	
	int WINDOW_WIDTH = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width;
	int WINDOW_HEIGHT = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height;
	Dimension WINDOW = new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT);
	
	int _BUTTON_WIDTH = WINDOW_HEIGHT/5;
	int _BUTTON_HEIGHT = _BUTTON_WIDTH /4;
	
	int FIELD_X_CENTER = _BUTTON_WIDTH + (WINDOW_WIDTH-_BUTTON_WIDTH)/2;
	
	Dimension BUTTON_DIMENSION = new Dimension(_BUTTON_WIDTH, _BUTTON_HEIGHT);
	Dimension LABEL_DIMENSION = new Dimension(_BUTTON_WIDTH, _BUTTON_HEIGHT*2);
	Dimension BUTTON_PANEL_DIMENSION = new Dimension(_BUTTON_WIDTH, WINDOW_HEIGHT);
	
	Point[] BUTTON_PANEL_ELEMENTS_POSITION = {
								new Point(0,0),
								new Point(0, _BUTTON_HEIGHT),
								new Point(0, _BUTTON_HEIGHT*3)
								};
	int IMAGE_SIZE = 101;
	
	int TIMER_DELAY = 8;
	int D_ANGLE = 10;
	
	int START_LEVEL = 10;
}
