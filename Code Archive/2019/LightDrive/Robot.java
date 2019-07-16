/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.mach.LightDrive.*;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends TimedRobot {
	//LightDriveCAN ldrive_can;
	LightDrivePWM ldrive_pwm;
	Servo servo1;
	Servo servo2;

	double panPosition;
	double tiltPosition;
	Servo pan;
	Servo tilt;
	int counter = 0;
	Joystick leftStick;
	JoystickButton tiltUp;
	JoystickButton tiltDown;
	JoystickButton panRight;
	JoystickButton panLeft;
	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		//Initialize a new CAN LightDrive

	//	ldrive_can = new LightDriveCAN();
		
		//Initialize 2 Servo outputs for a PWM LightDrive
		servo1 = new Servo(5);
		servo2 = new Servo(6);
		//Initialize a new PWM LightDrive
		ldrive_pwm = new LightDrivePWM(servo1, servo2);

		panPosition = 0.5;
		tiltPosition = 0.5;
		pan = new Servo(3);
		tilt = new Servo(4);
		leftStick = new Joystick(0);
		tiltUp = new JoystickButton(leftStick,7);
		tiltDown = new JoystickButton(leftStick,9);
		panRight = new JoystickButton(leftStick,12);
		panLeft = new JoystickButton(leftStick,11);
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {
		
		//Set colors on CAN LightDrive, also set brightness (optional)
	/*	ldrive_can.SetColor(1, Color.BLUE, (float) 0.8);
		ldrive_can.SetColor(2, Color.GREEN, (float) 0.8);
		ldrive_can.SetColor(3, Color.RED, (float) 0.8);
		ldrive_can.SetColor(4, Color.YELLOW, (float) 0.8);
		
		//Send these colors to CAN LightDrive
		//NOTE that we can't call Update() on PWM-controlled LightDrive because
		//PWM output is disabled while robot is disabled.
		ldrive_can.Update();
	*/	
		counter = 0;

		tiltPosition = 0.5;
		panPosition = 0.5;

		tilt.set(tiltPosition);
		pan.set(panPosition);
	}

	@Override
	public void disabledPeriodic() {
		//Rotate through colors on Bank1 of CAN LightDrive		
		//NOTE that we can't call Update() on PWM-controlled LightDrive because
		//PWM output is disabled while robot is disabled.
	/*	if(counter++ < 100)
		{
			ldrive_can.SetColor(1, Color.RED);		
		}
		else if(counter < 200)
		{
			ldrive_can.SetColor(1, Color.GREEN);	
		}
		else if(counter < 400)
		{
			ldrive_can.SetColor(1, Color.BLUE);	
		}
		else if(counter < 600)
		{
			ldrive_can.SetColor(1, Color.YELLOW);	
		}
		else if(counter < 800)
		{
			ldrive_can.SetColor(1, Color.PURPLE);	
		}
		else if(counter < 1000)
		{
			ldrive_can.SetColor(1, Color.TEAL);	
		}
		else if(counter < 1200)
		{
			ldrive_can.SetColor(1, Color.WHITE);
		}
		else if(counter < 1400)
		{
			ldrive_can.SetColor(1, Color.OFF);	
		}
		else if(counter > 1600)
		{
			counter = 0;
		}
		
		//Send latest color settings to CAN LightDrive
		ldrive_can.Update();
	*/	
		//Print Current and Voltage received from CAN LightDrive
		//System.out.format("LightDrive Currents: %2.1f %2.1f %2.1f %2.1f %2.1fV\r\n", ldrive_can.GetCurrent(1), ldrive_can.GetCurrent(2), ldrive_can.GetCurrent(3), ldrive_can.GetCurrent(4), ldrive_can.GetVoltage());
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * <p>You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
//		m_autonomousCommand = m_chooser.getSelected();

		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new ExampleCommand(); break; }
		 */

		// schedule the autonomous command (example)
//		if (m_autonomousCommand != null) {
//			m_autonomousCommand.start();
//		}

		tiltPosition = 0.5;
		panPosition = 0.5;

		tilt.set(tiltPosition);
		pan.set(panPosition);
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
//		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
//		if (m_autonomousCommand != null) {
//			m_autonomousCommand.cancel();
//		}
		
		counter = 0;
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
//		Scheduler.getInstance().run();
		
		//Rotate through colors on Bank1 of PWM LightDrive		
		if(counter++ < 100)
		{
			ldrive_pwm.SetColor(1, Color.RED);		
		}
		else if(counter < 200)
		{
			ldrive_pwm.SetColor(1, Color.GREEN);	
		}
		else if(counter < 400)
		{
			ldrive_pwm.SetColor(1, Color.BLUE);	
		}
		else if(counter < 600)
		{
			ldrive_pwm.SetColor(1, Color.YELLOW);	
		}
		else if(counter < 800)
		{
			ldrive_pwm.SetColor(1, Color.PURPLE);	
		}
		else if(counter < 1000)
		{
			ldrive_pwm.SetColor(1, Color.TEAL);	
		}
		else if(counter < 1200)
		{
			ldrive_pwm.SetColor(1, Color.WHITE);
		}
		else if(counter < 1400)
		{
			ldrive_pwm.SetColor(1, Color.OFF);	
		}
		else if(counter > 1600)
		{
			counter = 0;
		}
		
		//Write Latest Colors to PWM LightDrive (update PWM outputs)
		ldrive_pwm.Update();
		
		
		if (isUp(leftStick.getPOV()) && tiltPosition < 1.0) {
			tiltPosition += 0.01;
		}
		if (isDown(leftStick.getPOV()) && tiltPosition > 0.0) {
			tiltPosition -= 0.01;
		}
		if (isLeft(leftStick.getPOV()) && panPosition > 0.0) {
			panPosition -= 0.01;
		}
		if (isRight(leftStick.getPOV()) && panPosition < 1.0)	{
			panPosition += 0.01;
		}	

		tilt.set(tiltPosition);
		pan.set(panPosition);
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}

	private boolean isUp(int dir) {
		return (dir ==315 || dir == 0 || dir == 45);
	}

	private boolean isDown(int dir) {
		return (dir>=135 && dir <= 225);
	}

	private boolean isLeft(int dir) {
		return (dir>=225 && dir <= 315);
	}

	private boolean isRight(int dir) {
		return (dir>=45 && dir <= 135);
	}
}
