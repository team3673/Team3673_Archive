package org.usfirst.frc.team3673.AlexiaW2018;

import edu.wpi.cscore.AxisCamera;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.PWMSpeedController;
import edu.wpi.first.wpilibj.SolenoidBase;
import edu.wpi.first.wpilibj.SafePWM;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.*;

import org.usfirst.frc.team3673.AlexiaW2018.commands.*;
import org.usfirst.frc.team3673.AlexiaW2018.subsystems.*;

import com.ctre.CANTalon;
import com.kauailabs.navx.frc.AHRS;


public class Robot extends IterativeRobot {
	CANTalon leftFront = new CANTalon(1);
	CANTalon leftBack = new CANTalon(2);
	CANTalon rightFront = new CANTalon(3);
	CANTalon rightBack = new CANTalon(4);
	
	DriveCommands DriveCommands;
	
	RobotDrive drive = new RobotDrive(leftBack, leftBack, leftBack, leftBack);
	
	AHRS ahrs = new AHRS(SPI.Port.kMXP);
	AxisCamera aCam = new AxisCamera("aCam","10.36.73.47");
	AnalogInput ultrasonic = new AnalogInput(3);
	DoubleSolenoid gearPickup = new DoubleSolenoid(SPI.kPCMModules, SPI.kSolenoidChannels);

	
	Joystick red = new Joystick(0);
	Joystick black = new Joystick(1);
	
	JoystickButton redTrigger = new JoystickButton(red, 1);
	JoystickButton redTwo = new JoystickButton(red, 2);
	JoystickButton redThree = new JoystickButton(red, 3);
	JoystickButton redFour = new JoystickButton(red, 4);
	JoystickButton redFive = new JoystickButton(red, 5);
	JoystickButton redSix = new JoystickButton(red, 6);
	JoystickButton redSeven = new JoystickButton(red, 7);
	JoystickButton redEight = new JoystickButton(red, 8);
	JoystickButton redNine = new JoystickButton(red, 9);
	JoystickButton redTen = new JoystickButton(red, 10);
	JoystickButton redEleven = new JoystickButton(red, 11);
	JoystickButton redTwelve = new JoystickButton(red, 12);

	JoystickButton blackTrigger = new JoystickButton(black, 1);
	JoystickButton blackTwo = new JoystickButton(black, 2);
	JoystickButton blackThree = new JoystickButton(black, 3);
	JoystickButton blackFour = new JoystickButton(black, 4);
	JoystickButton blackFive = new JoystickButton(black, 5);
	JoystickButton blackSix = new JoystickButton(black, 6);
	JoystickButton blackSeven = new JoystickButton(black, 7);
	JoystickButton blackEight = new JoystickButton(black, 8);
	JoystickButton blackNine = new JoystickButton(black, 9);
	JoystickButton blackTen = new JoystickButton(black, 10);
	JoystickButton blackEleven = new JoystickButton(black, 11);
	JoystickButton blackTwelve = new JoystickButton(black, 12);
	

	Command autonomousCommand;
	SendableChooser<Command> chooser = new SendableChooser<>();

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	
	
	
	@Override
	public void robotInit() {
		GearCamera gearCamera = new GearCamera();
		
		
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {

	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		autonomousCommand = chooser.getSelected();

		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new ExampleCommand(); break; }
		 */

		// schedule the autonomous command (example)
		if (autonomousCommand != null)
			autonomousCommand.start();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
		GearCamera gearCamera = new GearCamera();
		CameraServer.getInstance().startAutomaticCapture(aCam);
		boolean CamConnect = aCam.isConnected();
		SmartDashboard.putBoolean("CamConnect", false);
	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (autonomousCommand != null)
			autonomousCommand.cancel();
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		DriveCommands DriveCommands = new DriveCommands();
		Scheduler.getInstance().run();
		boolean invert = blackTwelve.get();
		boolean notInverted = redTwelve.get();
		SmartDashboard.putBoolean("invert", true);
			if (invert == true) {
			drive.setInvertedMotor(RobotDrive.MotorType.kFrontLeft, true);
			drive.setInvertedMotor(RobotDrive.MotorType.kFrontRight, true);
			drive.setInvertedMotor(RobotDrive.MotorType.kRearLeft, true);
			drive.setInvertedMotor(RobotDrive.MotorType.kRearLeft, true);
			}
//		problematic fave
			else if (invert == true && notInverted == true){
			drive.setInvertedMotor(RobotDrive.MotorType.kFrontLeft, false);
			drive.setInvertedMotor(RobotDrive.MotorType.kFrontRight, false);
			drive.setInvertedMotor(RobotDrive.MotorType.kRearLeft, false);
			drive.setInvertedMotor(RobotDrive.MotorType.kRearRight, false);
			
			}
		
			else if (notInverted == true){
			drive.setInvertedMotor(RobotDrive.MotorType.kFrontLeft, false);
			drive.setInvertedMotor(RobotDrive.MotorType.kFrontRight, false);
			drive.setInvertedMotor(RobotDrive.MotorType.kRearLeft, false);
			drive.setInvertedMotor(RobotDrive.MotorType.kRearRight, false);
		}
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}
}
