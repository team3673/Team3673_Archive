
package org.usfirst.frc.team3673.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.SpeedControllerGroup;


public class Robot extends IterativeRobot {
	
	// Define joysticks
	Joystick red = new Joystick(1);
	Joystick black = new Joystick(0);
	
	// Define joystick buttons for red
	public JoystickButton redTrigger = new JoystickButton(red, 1);
	public JoystickButton redTwo = new JoystickButton(red, 2);
	public JoystickButton redThree = new JoystickButton(red, 3);
	public JoystickButton redFour = new JoystickButton(red, 4);
	public JoystickButton redFive = new JoystickButton(red, 5);
	public JoystickButton redSix = new JoystickButton(red, 6);
	public JoystickButton redSeven = new JoystickButton(red, 7);
	public JoystickButton redEight = new JoystickButton(red, 8);
	public JoystickButton redNine = new JoystickButton(red, 9);
	public JoystickButton redTen = new JoystickButton(red, 10);
	public JoystickButton redEleven = new JoystickButton(red, 11);
	public JoystickButton redTwelve = new JoystickButton(red, 12);

	// Define joystick buttons for black
	public JoystickButton blackTrigger = new JoystickButton(black, 1);
	public JoystickButton blackTwo = new JoystickButton(black, 2);
	public JoystickButton blackThree = new JoystickButton(black, 3);
	public JoystickButton blackFour = new JoystickButton(black, 4);
	public JoystickButton blackFive = new JoystickButton(black, 5);
	public JoystickButton blackSix = new JoystickButton(black, 6);
	public JoystickButton blackSeven = new JoystickButton(black, 7);
	public JoystickButton blackEight = new JoystickButton(black, 8);
	public JoystickButton blackNine = new JoystickButton(black, 9);
	public JoystickButton blackTen = new JoystickButton(black, 10);
	public JoystickButton blackEleven = new JoystickButton(black, 11);
	public JoystickButton blackTwelve = new JoystickButton(black, 12);
	
	// Assign a port to the navX gyro
	AHRS ahrs = new AHRS(SPI.Port.kMXP); 
	
	// Assign PWM ports to the spark motor controllers
	Spark rightFront = new Spark(3);
	Spark rightBack = new Spark(1); 
	Spark leftFront = new Spark(0);
	Spark leftBack = new Spark(2); 
	
	// Add front and rear motors to the right side group
	public SpeedControllerGroup right = new SpeedControllerGroup(rightFront, rightBack);
	
	// Add front and rear motors to the left side group
	public SpeedControllerGroup left = new SpeedControllerGroup(leftFront, leftBack);
	
	// Define groups to be used for driving
	public DifferentialDrive drive = new DifferentialDrive(left, right);
	
	@Override
	public void robotInit() {
	
	}

	@Override
	public void disabledInit() {

	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	
	@Override
	public void autonomousInit() {
		
	}

	
	@Override
	public void autonomousPeriodic() {
	/*	float xVel = ahrs.getVelocityX();
		float time = System.currentTimeMillis()*1000;
		float distanceX = xVel*time;
		
		float yVel = ahrs.getVelocityY();
		float distanceY = yVel*time;
		
		String gameData;
		gameData = DriverStation.getInstance().getGameSpecificMessage();
		if(gameData.charAt(2) == 'L')
		{
			 leftFront.set(0.4);
			 leftBack.set(0.4);
			 rightFront.set(-0.4);
			 rightBack.set(-0.4); 
		} else {
			leftFront.set(0.0);
			leftBack.set(0.0);
			rightFront.set(0.0);
			rightBack.set(0.0);
		}			
		*/
	}

	@Override
	public void teleopInit() {
	
	
	 
	}
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		
		// Define drive type as tank drive (two joysticks)
		 drive.tankDrive(red.getY(), black.getY());
		
    }		
		
		
		
		

	
	@Override
	public void testPeriodic() {
	}
}
