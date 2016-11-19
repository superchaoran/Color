import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

public class readAllGoogleVoiceMail {
	public static void main(String[] args) throws AWTException, InterruptedException{
		
		
		Robot robot = new Robot();
		while(true){
			/**
			 * Press *
			 */
			robot.keyPress(KeyEvent.VK_SHIFT);
			robot.delay(100);
	        robot.keyPress(KeyEvent.VK_8);
	        robot.delay(100);
	        robot.keyRelease(KeyEvent.VK_8);
	        robot.delay(100);
	        robot.keyRelease(KeyEvent.VK_SHIFT);
	        robot.delay(100);
	        /**
	         * Press a
	         */
	        robot.keyPress(KeyEvent.VK_A);
	        robot.delay(100);
	        /**
	         * Press I
	         */
			robot.keyPress(KeyEvent.VK_SHIFT);
			robot.delay(100);
	        robot.keyPress(KeyEvent.VK_I);
	        robot.delay(100);
	        robot.keyRelease(KeyEvent.VK_I);
	        robot.delay(100);
	        robot.keyRelease(KeyEvent.VK_SHIFT);
	        robot.delay(100);
	        robot.delay(1500);
		}
        
        
	}
}
