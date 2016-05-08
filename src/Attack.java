import java.awt.AWTException;
import java.awt.Color;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Attack {
	private static Robot robot;

	public static void main(String[] args) throws InterruptedException, AWTException {
		robot = new Robot();
		JFrame frame = new JFrame();
	
		//Get a search range
		JOptionPane.showMessageDialog(frame, "Prepare range upper left!");
		Point p1_0 = MouseInfo.getPointerInfo().getLocation();
		
		JOptionPane.showMessageDialog(frame, "Prepare range lower right!");
		Point p1_1 = MouseInfo.getPointerInfo().getLocation();
		
		//Get a search color
		JOptionPane.showMessageDialog(frame, "Prepare search color1!");
		int color0 = getPointerColor().getRGB();
		
		Point move;
		while(true){		
			move = searchColorLocationFast(color0, p1_0, p1_1);
			if(move != null){
				leftClick(move);
				robot.delay(4000);
			}
			else {
				System.out.println("Not Found!");
			}
			robot.delay(100);
		}
	}
	
	public static Color getPointerColor() throws InterruptedException{
		Point p = MouseInfo.getPointerInfo().getLocation();
		Color pixelColor = robot.getPixelColor(p.x, p.y);
		return pixelColor;
	}
	
	public static void leftClick(Point p) {
		robot.mouseMove(p.x, p.y);
		robot.delay(200);
		robot.mousePress(MouseEvent.BUTTON1_MASK);
		robot.mouseRelease(MouseEvent.BUTTON1_MASK);
	}
	
	public static Point searchColorLocationFast(int color, Point p1, Point p2){
	    int width = p2.x - p1.x;
	    int height = p2.y - p1.y;
	    
		BufferedImage image = robot.createScreenCapture(new Rectangle(p1.x, p1.y, width, height));
		for (int row = 0; row < image.getWidth(); row++) {
			for (int col = 0; col < image.getHeight(); col++) {
		   			if(color == image.getRGB(row, col)){
						System.out.println("Found:" + color);
						return new Point(row+p1.x, col+p1.y);
					}	
			}
		}
		return null;
	}
}
