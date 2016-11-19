import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Herb {
	private static Robot robot;

	public static void main(String[] args) throws InterruptedException, AWTException {
		robot = new Robot();
		JFrame frame = new JFrame();
		
		JOptionPane.showMessageDialog(frame, "Prepare backpack herb1 location!");
		Point herb1_loc = MouseInfo.getPointerInfo().getLocation();
			
		JOptionPane.showMessageDialog(frame, "Prepare backpack herbLast location!Must point on the grimy part");
		Point herbLast_loc = MouseInfo.getPointerInfo().getLocation();
		
		
		JOptionPane.showMessageDialog(frame, "Prepare clean button location after click on a grimy herb");
		Point cleanButton_loc = MouseInfo.getPointerInfo().getLocation();
		
		//Get a Banker location
		JOptionPane.showMessageDialog(frame, "Prepare banker location!");
		Point banker_loc = MouseInfo.getPointerInfo().getLocation();
		
		JOptionPane.showMessageDialog(frame, "Prepare bank herb location!");
		Point bankHerb_loc = MouseInfo.getPointerInfo().getLocation();
		
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
			robot.delay(100);
			
			//right click herb and withdraw all
			rightClick(bankHerb_loc);
			robot.delay(400);
			robot.mouseMove(bankHerb_loc.x, bankHerb_loc.y + 110);
			robot.delay(200);
			leftClick(MouseInfo.getPointerInfo().getLocation());
			robot.delay(200);
			
	        //close bank
	        robot.keyPress(KeyEvent.VK_ESCAPE);
	        robot.delay(100);
	        robot.keyRelease(KeyEvent.VK_ESCAPE);
	        robot.delay(200);
	        
			//check backpack unlock
			while (robot.getPixelColor(lock_loc.x, lock_loc.y).getRGB() == lock_color){
				// Close bank
		        robot.delay(100);
			}
			
			//Click grimy herb
			leftClick(herb1_loc);
			
			robot.delay(100);
			
			//wait for clean screen to appear
//			while (robot.getPixelColor(cleanScreenTitle_loc.x, cleanScreenTitle_loc.y).getRGB() != cleanScreenTitle_color){
//				// Close bank
//		        robot.delay(100);
//			}
//			
			robot.delay(2000);
			
			//Click clean button
			leftClick(cleanButton_loc);
			robot.delay(100);
			
			robot.delay(15000);
			
			//Wait for last grimy herb turn clean
			robot.mouseMove(herbLast_loc.x, herbLast_loc.y);
			int herbLast_color = robot.getPixelColor(herbLast_loc.x, herbLast_loc.y).getRGB();
//			while (robot.getPixelColor(herbLast_loc.x, herbLast_loc.y).getRGB() == herbLast_color){
//				// Close bank
////				System.out.println("1:"+robot.getPixelColor(herbLast_loc.x, herbLast_loc.y).getRGB());
////				System.out.println("2:"+herbLast_color);
//		        robot.delay(500);
//			}
			robot.delay(500);
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
		robot.delay(200);
		robot.mousePress(MouseEvent.BUTTON3_MASK);
		robot.mouseRelease(MouseEvent.BUTTON3_MASK);
	}
}
