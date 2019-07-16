/*
 *	This code is property of the Seaside High School Robotics team, C.Y.B.O.R.G. Seagulls.
 *	Used for FIRST FRC Deep Space 2019 
 *	Alexia Maye 02.28.19
 */

package org.usfirst.frc.team3673.robot;

import edu.wpi.cscore.AxisCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import com.mach.LightDrive.*;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
//import org.usfirst.frc.team3673.robot.Pipeline;

public class Robot extends TimedRobot {

	// Define Power Distribution Panel
	PowerDistributionPanel pdp = new PowerDistributionPanel();

	// Define joysticks
	public Joystick red = new Joystick(1);
	public Joystick black = new Joystick(0);

	// Declare light controller
	LightDrivePWM ldrive_pwm;

	// Declare servos for light controller
	Servo servo1;
	Servo servo2;

	int ledBrightness;
	DriverStation ds;
	Alliance alliance;
	int ledString;
	double sineWave;
	int ledColor;
	int ledChannel;

	// I'll figure out what this is for later?
	int counter = 0;

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

	// Create AxisCameras
	AxisCamera aCam;
	AxisCamera bCam;

	// Assign a port to the encoder
	public Encoder leftEncoder = new Encoder(0, 1, false, Encoder.EncodingType.k4X);
	public Encoder rightEncoder = new Encoder(2, 3, false, Encoder.EncodingType.k4X);

	
	// Set wheel size
	public double wheelDiameter = 5.98;

	// Get number of rotations since last reset
	public int leftCount = leftEncoder.get();

	// Get distance per pulse
	public double distancePerRotation = leftEncoder.getDistancePerPulse();

	// Use this formula to get a predicted distance for the next pulse
	public double distance = leftEncoder.getDistance();

	// Assign PWM ports to the spark motor controllers
	public Spark right = new Spark(0);
	public Spark left = new Spark(1);
	public Spark definitelyTheArmBoy = new Spark(2);
	public Spark definitelyTheSECONDArm = new Spark(3);
	public Spark theThirdMusketeer = new Spark(4);

	// Create servos for horizontal and vertical movement
	Servo pan;
	Servo tilt;
	
	// Create doubles to tell position of servos
	double panPosition;
	double tiltPosition;

	// Create Differential drive train
	public DifferentialDrive drive;

	// Sets conditions for the boolean isUp
	private boolean isUp(int dir) {
		return (dir ==315 || dir == 0 || dir == 45);
	}

	// Sets conditions for the boolean isDown
	private boolean isDown(int dir) {
		return (dir>=135 && dir <= 225);
	}

	// Sets conditions for the boolean isLeft
	private boolean isLeft(int dir) {
		return (dir>=225 && dir <= 315);
	}

	// Sets conditions for the boolean isRight
	private boolean isRight(int dir) {
		return (dir>=45 && dir <= 135);
	}

	@Override
	public void robotInit() {

		// Assign ports to servos
		servo1 = new Servo(8);
		servo2 = new Servo(9);

		//Initialize a new PWM LightDrive
		ldrive_pwm = new LightDrivePWM(servo1, servo2);

		ds = DriverStation.getInstance();
		ledString = 2;
		ledBrightness = 0;
		sineWave = 0.0;

		// Define the Axis Camera
		aCam = CameraServer.getInstance().addAxisCamera("aCam", "10.36.73.54");
		bCam = CameraServer.getInstance().addAxisCamera("bCam", "10.36.73.56");

		// Initiate automatic capture
		CameraServer.getInstance().startAutomaticCapture(aCam);
		CameraServer.getInstance().startAutomaticCapture(bCam);

		// Assign sparks in drive train
		drive = new DifferentialDrive(left, right);

		// Establish start position of servos
		panPosition = 0.5;
		tiltPosition = 0.5;

		// Assign servo placement
		pan = new Servo(6);
		tilt = new Servo(7);

	}

	@Override
	public void disabledInit() {

		counter = 0;

		// Establish servo placement
		tiltPosition = 0.5;
		panPosition = 0.5;

		// Set servo placement
		tilt.set(tiltPosition);
		pan.set(panPosition);

	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void autonomousInit() {

		// Reset rotation count
		leftEncoder.reset();
		rightEncoder.reset();

		// Enable motor safety
		drive.setSafetyEnabled(true);

		// Set distance per pulse based on wheel circumference (in inches)
		leftEncoder.setDistancePerPulse((wheelDiameter * 3.14159265358979323846264) / 2048);
		rightEncoder.setDistancePerPulse((wheelDiameter * 3.14159265358979323846264) / 2048);

		// Establish servo position
		tiltPosition = 0.5;
		panPosition = 0.5;

		//Set servo position
		tilt.set(tiltPosition);
		pan.set(panPosition);

		counter = 0;

	}

	@Override
	public void autonomousPeriodic() {

		// If hat is pressed down
		if (red.getPOV() > 95 && black.getPOV() < 265){

			// Set actuator to go down at 0.2 speed
			definitelyTheArmBoy.set(-0.9);
			definitelyTheSECONDArm.set(-0.65);
			theThirdMusketeer.set(0.7);

		}

		// If hat is pressed up
		else if (red.getPOV() >= 0 && black.getPOV() < 85 || black.getPOV() > 275){

			// Set actuator to go up at 0.2 speed
			definitelyTheArmBoy.set(0.83);
			definitelyTheSECONDArm.set(0.69);
			theThirdMusketeer.set(-0.7);

		}

		// If hat is neither pressed up nor down
		else {

			// Stop the arm
			definitelyTheArmBoy.set(0.0);
			definitelyTheSECONDArm.set(0.0);
			theThirdMusketeer.set(0.0);

		}

		// If the hat is pressed upwards
		if (isUp(black.getPOV()) && tiltPosition < 1.0) {
			
			// Set the tilt position to go up
			tiltPosition += 0.01;
	
		}
	
		if (isDown(black.getPOV()) && tiltPosition > 0.0) {
	
			// Set the tilt position to go down
			tiltPosition -= 0.01;
			
		}
		
		if (isLeft(black.getPOV()) && panPosition > 0.0) {
	
			// Set the pan position to go back
			panPosition -= 0.01;
	
		}
	
		if (isRight(black.getPOV()) && panPosition < 1.0)	{
			
			// Set the pan position to go forwards
			panPosition += 0.01;
	
		}

		// Set up tank drive based on joystick inputs
		drive.tankDrive(red.getY(), black.getY());
		
	}

	
	@Override
	public void teleopInit() {

		// Set distance per pulse based on wheel circumference (in inches)
		leftEncoder.setDistancePerPulse((wheelDiameter * 3.14159265358979323846264) / 2048);

		alliance = ds.getAlliance();
		ledColor = alliance.ordinal();
		/*
		ledChannel = (ledString - 1) * 3 + (ledColor + 1);
		for(int ch=0; ch<4; ch++) {
			ldrive_pwm.SetColor(ch+1, Color.);
		}
		*/

		counter = 0;

	}

	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();

		// Sets encoders to not be reversed
		leftEncoder.setReverseDirection(false);
		rightEncoder.setReverseDirection(false);


		// If hat is pressed down
		if (red.getPOV() > 95 && red.getPOV() < 265){

			// Set actuator to go down at 0.2 speed
			definitelyTheArmBoy.set(-0.9);
			definitelyTheSECONDArm.set(-0.65);
			theThirdMusketeer.set(0.7);

		}

		// If hat is pressed up
		else if (red.getPOV() >= 0 && red.getPOV() < 85 || red.getPOV() > 275){

			// Set actuator to go up at 0.2 speed
			definitelyTheArmBoy.set(0.83);
			definitelyTheSECONDArm.set(0.69);
			theThirdMusketeer.set(-0.7);

		}

		// If hat is neither pressed up nor down
		else {

			// Stop the arm
			definitelyTheArmBoy.set(0.0);
			definitelyTheSECONDArm.set(0.0);
			theThirdMusketeer.set(0.0);

		}

		// If redThree is being pressed
		if (redThree.get()){
			
			// Set the middle actuator to half speed
			theThirdMusketeer.set(0.5);
		
		}
		
		// Otherwise, if redFour is being pressed
		else if (redFour.get()) {
		
			// Set the middle actuator backwards to half speed
			theThirdMusketeer.set(-0.5);
		
		}
		
		// If redThree and redFour and the POV hat are NOT being pressed
		else if (!redThree.get() && !redFour.get() && red.getPOV() == -1) {
		
			// Set the middle actuator to not move
			theThirdMusketeer.set(0.0);
		
		}

		// Define drive type as tank drive (two joysticks)
		drive.tankDrive(red.getY(), black.getY());

		// If the hat is pressed upwards
		if (isUp(black.getPOV()) && tiltPosition < 1.0) {
			
			// Set the tilt position to go up
			tiltPosition += 0.01;
		
		}
		
		if (isDown(black.getPOV()) && tiltPosition > 0.0) {
		
			// Set the tilt position to go down
			tiltPosition -= 0.01;
		
		}
		
		if (isLeft(black.getPOV()) && panPosition > 0.0) {
		
			// Set the pan position to go back
			panPosition -= 0.01;
		
		}
		
		if (isRight(black.getPOV()) && panPosition < 1.0)	{
		
			// Set the pan position to go forwards
			panPosition += 0.01;
		
		}	
 
		// Send tilt and pan positions to servos
		tilt.set(tiltPosition);
		pan.set(panPosition);


	}

	@Override
	public void testPeriodic() {

		System.out.println(red.getThrottle());
	}
}

// .- .-.. . -..- .. .-    -- .- -.-- .
// -- .- -. ..- . .-..    .. ...   .-   -.. --- .-. -.-
