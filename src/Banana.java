import java.awt.AWTException;
import java.awt.Color;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Banana {
	private static Robot robot;

	public static void main(String[] args) throws InterruptedException, AWTException {
		robot = new Robot();
		JFrame frame = new JFrame();
		
		JOptionPane.showMessageDialog(frame, "Prepare backpack bucket1 location!");
		Point bucket1_loc = MouseInfo.getPointerInfo().getLocation();
		
		//Get a Banker location
		JOptionPane.showMessageDialog(frame, "Prepare banker location!");
		Point banker_loc = MouseInfo.getPointerInfo().getLocation();
		
		JOptionPane.showMessageDialog(frame, "Prepare bank bucket location!");
		Point bucket_loc = MouseInfo.getPointerInfo().getLocation();
		
		JOptionPane.showMessageDialog(frame, "Prepare bank banana location!");
		Point banana_loc = MouseInfo.getPointerInfo().getLocation();
		
		JOptionPane.showMessageDialog(frame, "Prepare bank deposit location!");
		Point deposit_loc = MouseInfo.getPointerInfo().getLocation();
		
		JOptionPane.showMessageDialog(frame, "Prepare lockcolor and location!");
		Point lock_loc = MouseInfo.getPointerInfo().getLocation();
		int lock_color = robot.getPixelColor(lock_loc.x,lock_loc.y).getRGB();
	
		while(true){
			//click banker
			leftClick(banker_loc);
			robot.delay(100);
			//check bucket in bank, lock is there
			while (robot.getPixelColor(lock_loc.x, lock_loc.y).getRGB() != lock_color){
				robot.delay(100);
			}
			
			//deposit
			leftClick(deposit_loc);
			
			//right click bucket and withdraw 5
			rightClick(bucket_loc);
			robot.delay(100);
			robot.mouseMove(bucket_loc.x, bucket_loc.y + 50);
			robot.delay(100);
			leftClick(MouseInfo.getPointerInfo().getLocation());
			robot.delay(100);
			
			//right click banana and withdraw 20
			rightClick(banana_loc);
			robot.delay(100);
			robot.mouseMove(banana_loc.x, banana_loc.y + 75);
			robot.delay(100);
			leftClick(MouseInfo.getPointerInfo().getLocation());
			robot.delay(100);
			
	        
	        robot.keyPress(KeyEvent.VK_ESCAPE);
	        robot.delay(100);
	        robot.keyRelease(KeyEvent.VK_ESCAPE);
	        robot.delay(200);
	        
			//check backpack unlock
			while (robot.getPixelColor(lock_loc.x, lock_loc.y).getRGB() == lock_color){
				// Close bank
		        robot.delay(100);
			}
			
			//Click bucket 1, 2, 3, 4
			leftClick(bucket1_loc);
			
			leftClick(new Point(bucket1_loc.x+46, bucket1_loc.y));
			robot.delay(100);
			
			
			leftClick(new Point(bucket1_loc.x+92, bucket1_loc.y));
			robot.delay(100);
			
			leftClick(new Point(bucket1_loc.x+138, bucket1_loc.y));
			robot.delay(100);	
			
		}
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
	
	public static void rightClick(Point p) {
		robot.mouseMove(p.x, p.y);
		robot.delay(100);
		robot.mousePress(MouseEvent.BUTTON3_MASK);
		robot.mouseRelease(MouseEvent.BUTTON3_MASK);
	}
}

