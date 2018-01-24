/* Code written by Alexia Walgren
*  1.24.2018
*  Slate One is a program meant to only drive, in case of emergencies.
*/
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
