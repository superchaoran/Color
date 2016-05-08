import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.AWTException;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Mining{
	
	private static Robot robot;
	private static long previousTime;
	public static void main(String[] args) throws AWTException, InterruptedException, MalformedURLException{
		robot = new Robot();
		JFrame frame = new JFrame();
		ArrayList<Integer> colors = new ArrayList<Integer>();
		ArrayList<Point> points = new ArrayList<Point>();

		//barbarians
//		colors.add(-1860767);
//		colors.add(-14936314);
//		colors.add(-9088971);
		
		//iron ores
//		printMouseLocationAndColor(frame);
		JOptionPane.showMessageDialog(frame, "Prepare range upper left!");
		//Get a search range
		Point p0_0 = MouseInfo.getPointerInfo().getLocation();
		JOptionPane.showMessageDialog(frame, "Prepare range lower right!");
		
		Point p0_1 = MouseInfo.getPointerInfo().getLocation();
		JOptionPane.showMessageDialog(frame, "Prepare search color0!");
		points.add(p0_0);points.add(p0_1);
		
		
		//Get a search color
		Color color0 = getPointerColor();
		colors.add(color0.getRGB());
		
		//Get a search range
		JOptionPane.showMessageDialog(frame, "Prepare range upper left!");
		Point p1_0 = MouseInfo.getPointerInfo().getLocation();
		
		JOptionPane.showMessageDialog(frame, "Prepare range lower right!");
		Point p1_1 = MouseInfo.getPointerInfo().getLocation();
		
		JOptionPane.showMessageDialog(frame, "Prepare search color1!");
		points.add(p1_0);points.add(p1_1);
		
		//Get a search color
		Color color1 = getPointerColor();
		colors.add(color1.getRGB());
		
		JOptionPane.showMessageDialog(frame, "Prepare drop location!Drop location must have ore!");
		//Get a Drop backpack location;
		Point pDrop = MouseInfo.getPointerInfo().getLocation();
		int fullDropColor = getPointerColor().getRGB();
		makeADrop(pDrop);
		
		

		JOptionPane.showMessageDialog(frame, "Prepare drop location color!Drop location must be empty!");
		robot.mouseMove(pDrop.x, pDrop.y);
		robot.delay(600);
		int emptyDropColor = getPointerColor().getRGB();

		//Point move = searchColorLocationSlow(color, p1, p2);
		Point move;
		int count = 0;
		previousTime = System.currentTimeMillis();
		while(true){		
			//clearPack(pDrop, fullDropColor);
			//clearPack(new Point(999, 648), new Color(22,30,31).getRGB());
			clearPack(pDrop, fullDropColor, emptyDropColor);
			//clearPack(new Point(pDrop.x + 50, pDrop.y), fullDropColor);
			robot.delay(100);
			int index = count++ % 2;
			move = searchColorLocationFast(colors.get(index), points.get(index*2), points.get(index*2+1));
			if(move != null){
				leftClick(move);
				robot.delay(100);
			}
			else {
				System.out.println("Not Found!");
			}
			robot.delay(100);
		}

	}
	
	public static void playSound() throws InterruptedException{
	    Mixer mixer;
	    Clip clip = null;
	    Mixer.Info[] mixInfos = AudioSystem.getMixerInfo();
	    mixer = AudioSystem.getMixer(mixInfos[0]);
	    DataLine.Info dataInfo = new DataLine.Info(Clip.class, null);
	    try {
			clip = (Clip)mixer.getLine(dataInfo);
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    	URL soundURL = ColorPick.class.getResource("beep-01a.wav");

				AudioInputStream audioStream;
				try {
					audioStream = AudioSystem.getAudioInputStream(soundURL);
					clip.open(audioStream);
				} catch (UnsupportedAudioFileException | LineUnavailableException| IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				clip.start();
				clip.loop(100);
	    do{
	    	Thread.sleep(100);	    		
	    }while(true);
	}
	public static void printMouseLocationAndColor(JFrame frame) throws InterruptedException{
		JOptionPane.showMessageDialog(frame, "Prepare to get mouse location and color");
		System.out.println(getPointerColor());
		System.out.println(MouseInfo.getPointerInfo().getLocation());
		
		/*empty backpack*/
//		java.awt.Color[r=22,g=30,b=31]
//		java.awt.Point[x=999,y=648]

	}
	
	public static void clearPack(Point p, int fullColor, int emptyColor) throws InterruptedException{
			int currentRGB = robot.getPixelColor(p.x, p.y).getRGB();
			
			if( currentRGB != emptyColor) {
				if (currentRGB == fullColor) {
					//sees ore and reinitialize fail safe counter
					previousTime = System.currentTimeMillis();
				} else {
					//if pack not empty, but not ore, may have item or being covered by another window
					if((System.currentTimeMillis() - previousTime)> 30000){
						playSound();
					}			
				}
				makeADrop(p);
				robot.delay(100);
				dropEmerald(p);
			} else {
				//if pack empty for too long
				if((System.currentTimeMillis() - previousTime)> 30000){
					playSound();
				}
			}
	}
	
	public static void makeADrop(Point p){
		rightClick(new Point(p.x + 10, p.y + 5));
		robot.delay(100);
		Point newP = new Point(p.x + 10, p.y + 55);
		robot.mouseMove(newP.x, newP.y);
		robot.delay(100);
		leftClick(newP);
	}
	
	public static void dropEmerald(Point p){
		rightClick(new Point(p.x + 10, p.y + 5));
		robot.delay(100);
		Point newP = new Point(p.x + 10, p.y + 70);
		robot.mouseMove(newP.x, newP.y);
		robot.delay(100);
		leftClick(newP);
	}
	
	public static Color getPointerColor() throws InterruptedException{
		Point p = MouseInfo.getPointerInfo().getLocation();
		Color pixelColor = robot.getPixelColor(p.x, p.y);
		return pixelColor;
	}
	
	public static Point searchColorLocationSlow(Color color, Point p1, Point p2){
		for (int i = p1.x; i <= p2.x; i++) {
			for (int j = p1.y; j <= p2.y; j++) {
				if(isSameColor(robot.getPixelColor(i,j),color)){
					System.out.println("Found:" +i + ":"+ j);
					return new Point(i,j);
				}
			}
			System.out.println("Still Running)");
		}
		return null;
	}
	
	public static Point searchColorLocationFast(int color, Point p1, Point p2){
	    int width = p2.x - p1.x;
	    int height = p2.y - p1.y;
	    

		BufferedImage image = robot.createScreenCapture(new Rectangle(p1.x, p1.y, width, height));

		for (int row = 0; row < image.getWidth(); row++) {
			for (int col = 0; col < image.getHeight(); col++) {
				//System.out.println(image.getRGB(row, col));
				//robot.mouseMove(row+p1.x, col+p1.y);
		   			if(color == image.getRGB(row, col)){
						System.out.println("Found:" + color);
						return new Point(row+p1.x, col+p1.y);
					}	
			}
		}
		return null;
	}
	
	public static boolean isSameColor(Color c1, Color c2){
		if(c1.getRed() == c2.getRed() && c1.getGreen() == c2.getGreen() && c1.getBlue() == c2.getBlue()){
			return true;
		}
		return false;
	}
	
	public static void leftClick(Point p) {
		robot.mouseMove(p.x, p.y);
		robot.delay(200);
		robot.mousePress(MouseEvent.BUTTON1_MASK);
		robot.mouseRelease(MouseEvent.BUTTON1_MASK);
	}
	
	public static void rightClick(Point p) {
		robot.mouseMove(p.x, p.y);
		robot.delay(100);
		robot.mousePress(MouseEvent.BUTTON3_MASK);
		robot.mouseRelease(MouseEvent.BUTTON3_MASK);
	}

}
