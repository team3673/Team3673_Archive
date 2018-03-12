/*
 *	This code is property of the Seaside High School Robotics team, C.Y.B.O.R.G. Seagulls.
 *	Used for FIRST FRC PowerUp 2018 
 *	Alexia M. Walgren 02.19.18
 */


package org.usfirst.frc.team3673.robot;

import edu.wpi.cscore.AxisCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.buttons.JoystickButton;


public class Robot extends IterativeRobot {
	
	// Define joysticks
	public Joystick red = new Joystick(1);
	public Joystick black = new Joystick(0);
	
	public long startTime;
	
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
	
	// Int for time around in auto
	public int autoStep = 1;
	
	// Boolean for whether or not grabber is used in auto
	public boolean grabBool = false;
	
	AxisCamera aCam;
	
	public double ejectDistance;
	
	// Boolean for whether or not encode.reset is used in auto
	public boolean once = false;
	
	// Assign a port to the navX gyro
	public AHRS ahrs = new AHRS(SPI.Port.kMXP); 
	
	// Define a new Compressor
	public Compressor compress = new Compressor(0);
	
	// Define Solenoid
	public DoubleSolenoid LeftSolenoid = new DoubleSolenoid(0, 1);
	public DoubleSolenoid RightSolenoid = new DoubleSolenoid(2, 3);
	
	// Assign a port to the encoder
	public Encoder encode = new Encoder(0, 1, false, Encoder.EncodingType.k4X);
	
	// Set wheel size
	public double wheelDiameter = 5.98;
	
	// Get number of rotations since last reset
	public int count = encode.get();
	
	// Get distance per pulse
	public double distancePerRotation = encode.getDistancePerPulse();
	
	// Use this formula to get a predicted distance for the next pulse
	public double distance = encode.getDistance();
	
	// Assign PWM ports to the spark motor controllers
	public Spark rightFront = new Spark(3);
	public Spark rightBack = new Spark(1); 
	public Spark leftFront = new Spark(4);
	public Spark leftBack = new Spark(2); 
	public Spark highGuy = new Spark(5);
	public Spark dartActuator = new Spark(6);
	public Spark leftGrab = new Spark(7);
	public Spark rightGrab = new Spark(8);
	public Spark grabPivot = new Spark(9);
	
	// Add front and rear motors to the right side group
	public SpeedControllerGroup right = new SpeedControllerGroup(rightFront, rightBack);
	
	// Add front and rear motors to the left side group
	public SpeedControllerGroup left = new SpeedControllerGroup(leftFront, leftBack);
	
	// Define groups to be used for driving
	public DifferentialDrive drive = new DifferentialDrive(left, right);
	
	// Solenoid toggling boolean
	boolean solToggle; // = false;
	
	// Solenoid is extended boolean
	boolean isExtended; // = false;
	
	//Double leftSpeed to prevent junk values
	double leftSpeed;
	
	//Double rightSpeed to prevent junk values
	double rightSpeed;
	
	private int twoMode = 0;
	private int oneMode = 0;
	private int threeMode = 0;
	
	private SendableChooser oneAutoCommand;
	SendableChooser oneChooser;
	
	private SendableChooser twoAutoCommand;
	SendableChooser twoChooser;
	
	private SendableChooser threeAutoCommand;
	SendableChooser threeChooser;
	
	@Override
	public void robotInit() {
		aCam = CameraServer.getInstance().addAxisCamera("aCam", "10.36.73.204");
		CameraServer.getInstance().startAutomaticCapture(aCam);
		
		oneChooser = new SendableChooser();
		oneChooser.addDefault("Baseline", 1);
		oneChooser.addObject("Don't use auto", 2);
		oneChooser.addObject("Use auto", 3);
		
		SmartDashboard.putData("First Autonomous Selector", oneChooser);
		
		twoChooser = new SendableChooser();
		twoChooser.addDefault("Baseline", 1);
		twoChooser.addObject("Don't use auto", 2);
		twoChooser.addObject("Use auto", 3);
		
		SmartDashboard.putData("Mid Autonomous Selector", twoChooser);
		
		threeChooser = new SendableChooser();
		threeChooser.addDefault("Baseline", 1);
		threeChooser.addObject("Don't use auto", 2);
		threeChooser.addObject("Use auto", 3);
		
		SmartDashboard.putData("Last Autonomous Selector", threeChooser);
		
		
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
		
		//Reset rotation count
		encode.reset();
		
		oneMode = (int) oneChooser.getSelected();
		
		twoMode = (int) twoChooser.getSelected();
		
		threeMode = (int) threeChooser.getSelected();
		
		startTime = System.currentTimeMillis();
		
		// Disable motor safety
		drive.setSafetyEnabled(true);
		
		// Set distance per pulse based on wheel circumference (in inches)
		encode.setDistancePerPulse((wheelDiameter*3.14159265358979323846264)/2048);
		
		// Reset AHRS (gyro)
		ahrs.reset();
		
		//Double leftSpeed to prevent junk values
		leftSpeed = 0.0;
		
		//Double rightSpeed to prevent junk values
		rightSpeed = 0.0;
		
		once = false;
		
		autoStep = 1;
		
		ejectDistance = 0;
		
	}
	
	@Override
	public void autonomousPeriodic() {
		
		// Use this to get distance
		distance = -encode.getDistance();
		
		// Set distance per pulse based on wheel circumference (in inches)
		encode.setDistancePerPulse((wheelDiameter*3.14159265358979323846264)/2048);
				
		
		long currentTime = System.currentTimeMillis();
		
		double timeLapse = (currentTime - startTime)/1000.0;
		
		
		
		
		// Get game information in the format of R/L (your switch) R/L (the scale) R/L (their switch)
		String gameData = DriverStation.getInstance().getGameSpecificMessage();
		
		
		
		//System.
		
		dartActuator.set(-0.4);
		grabPivot.set(1.0);
		
		/*if (distance < 98){
			// Move forward
			leftSpeed = -0.5;
			rightSpeed = -0.48;
		}
		else{
			// Keep robot from moving forward
			rightSpeed = 0.0;
			leftSpeed = 0.0;
		}
		*/
		
		
			
			switch(oneMode){
			
			case 1:
	
					leftSpeed = -0.5;
					rightSpeed = -0.5;
				
				
			case 2:
				
				leftSpeed = 0.0;
				rightSpeed = 0.0;
				
				
			case 3:	
	
			
			// If enemy alliance's switch is on our side
			if(gameData.charAt(0) == 'L'){if (distance < 130 && autoStep == 1) {
				
				// Move forward
				leftSpeed = -0.6;
				rightSpeed = -0.6152;
				
				//System.out.println(distance);
			
				// Reset ahrs
				//ahrs.reset();
				
			}
			
			// Robot has moved 130"
			else {
				
				// Keep robot from moving forward
				leftSpeed = 0.0;
				rightSpeed = 0.0;
				
				if(ahrs.getAngle() < 60){
					
					// Turn right
					leftSpeed = 0.54;
					rightSpeed = -0.52;
					
					//System.out.print(ahrs.getAngle());
					
					// Reset encoder count and therefore distance
					//encode.reset();
					
				}
				
				// Robot has turned 90 degrees
				else {
					
					// Keep robot from turning right 
					rightSpeed = 0.0;
					leftSpeed = 0.0;
					
					if (timeLapse > 6 && once == false) {
						encode.reset();
						distance = encode.getDistance();
						System.out.println("RESET");
						
						autoStep = 2;
						
						once = true;
					}
					
					System.out.println(distance);
					
					if (distance < 25 && autoStep == 2) {
						
						//System.out.println("Hey no kill I'm doin the thing");
						
						leftSpeed = -0.71;
						rightSpeed = -0.7252;
						
					}
					
					else if (distance >= 33 && autoStep == 2) {
						LeftSolenoid.set(DoubleSolenoid.Value.kReverse);
						RightSolenoid.set(DoubleSolenoid.Value.kReverse);
						
						
					}
				}	
			}
		} 
			
			//1, **R
			else{
				
				leftSpeed = -0.5162;
				rightSpeed = -0.5;
	}
			
		
		
		
		
		
		case 4:
				
				if(gameData.charAt(0) == 'L') {
					
					//2, **L
					System.out.println("2, L**");
				
					leftSpeed = -0.5362;
					rightSpeed = -0.5;
				
					if (timeLapse > 6){
					
						LeftSolenoid.set(DoubleSolenoid.Value.kReverse);
						RightSolenoid.set(DoubleSolenoid.Value.kReverse);
						
					} 
					else {
						
					}
					
				}
				else {
				//2, **R
				System.out.println("2, R**");
				
				leftSpeed = -0.5;
				rightSpeed = -0.5362;
			
				if (timeLapse > 6){
				
					LeftSolenoid.set(DoubleSolenoid.Value.kReverse);
					RightSolenoid.set(DoubleSolenoid.Value.kReverse);
					
				} 
				else {
					
				
				}
				

			
				
	}	
		
		
		
		
		
		case 5:
				
			// If enemy alliance's switch is on our side
			if(gameData.charAt(0) == 'R'){
				
				
				if (distance < 130 && autoStep == 1) {
					
					// Move forward
					leftSpeed = -0.6;
					rightSpeed = -0.6152;
					
					//System.out.println(distance);
				
					// Reset ahrs
					//ahrs.reset();
					
				}
				
				// Robot has moved 130"
				else {
					
					// Keep robot from moving forward
					leftSpeed = 0.0;
					rightSpeed = 0.0;
					
					if(ahrs.getAngle() < 60){
						
						// Turn right
						leftSpeed = -0.52;
						rightSpeed = 0.5;
						
						//System.out.print(ahrs.getAngle());
						
						// Reset encoder count and therefore distance
						//encode.reset();
						
					}
					
					// Robot has turned 90 degrees
					else {
						
						// Keep robot from turning right 
						rightSpeed = 0.0;
						leftSpeed = 0.0;
						
						if (timeLapse > 6 && once == false) {
							encode.reset();
							distance = encode.getDistance();
							System.out.println("RESET");
							
							autoStep = 2;
							
							once = true;
						}
						
						System.out.println(distance);
						
						if (distance < 25 && autoStep == 2) {
							
							//System.out.println("Hey no kill I'm doin the thing");
							
							leftSpeed = -0.71;
							rightSpeed = -0.7252;
							
						}
						
						else if (distance >= 33 && autoStep == 2) {
							LeftSolenoid.set(DoubleSolenoid.Value.kReverse);
							RightSolenoid.set(DoubleSolenoid.Value.kReverse);
							
							
						}
					}	
				}
			
				
				
				
				
			} 
			
			else {
				
				leftSpeed = -0.5162;
				rightSpeed = -0.5;
				
			}			
		}
		
		//System.out.println(leftSpeed + " " + rightSpeed);
		
		// Set drive parameters
		drive.tankDrive(leftSpeed, rightSpeed);
		
		//LeftSolenoid.set(DoubleSolenoid.Value.kForward);
		//RightSolenoid.set(DoubleSolenoid.Value.kForward);
	}
	

	@Override
	public void teleopInit() {
	
		//Reset rotation count
		encode.reset();
		
		// Set distance per pulse based on wheel circumference (in inches)
		encode.setDistancePerPulse((wheelDiameter*3.14159265358979323846264)/2048);
	 
	}
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		encode.setReverseDirection(false);
		
		
		
			// Turn on compressor
			compress.setClosedLoopControl(true);
	
			// If sixth button on red controller is pressed
			if(redSix.get()){
				
				// Set climber to go up
				highGuy.set(1.0);
			
			}
			
			// If fourth button on red controller is pressed
			else if (redFour.get()){
				
				// Set climber to go down
				highGuy.set(-1.0);
			
			}
			
			// If neither the fourth nor sixth red button are pressed
			else{
				
				// Set climber to be off
				highGuy.set(0.0);
				
			}
			
			
			// If fifth button on red controller is pressed
						if(redThree.get()){
							
							// Set actuator to go out
							dartActuator.set(1.0);
						
						}
						
						// If third button on red controller is pressed
						else if (redFive.get()){
							
							// Set actuator to go in
							dartActuator.set(-1.0);
						
						}
						
						// If neither the third nor fifth red button are pressed
						else{
							
							// Set climber to be off
							dartActuator.set(0.0);
							
						}
						
				//
						if (blackSix.get()) {
							
							grabPivot.set(-1.0);
							
						}
						else if (blackFour.get()){
							
							grabPivot.set(1.0);
						}
						else {
							
							grabPivot.set(0.0);
							
						}
			
		
		// If red trigger is pressed
		if(red.getTrigger() && !solToggle && !isExtended ){
			
			 // Turn on pneumatics
			LeftSolenoid.set(DoubleSolenoid.Value.kForward);
			RightSolenoid.set(DoubleSolenoid.Value.kForward);
			
			// Boolean isExtended says solenoids are forward
			isExtended = true;
			
		}
		else if(red.getTrigger() && !solToggle && isExtended) {
			
			//Set solenoids to reverse
			LeftSolenoid.set(DoubleSolenoid.Value.kReverse);
			RightSolenoid.set(DoubleSolenoid.Value.kReverse);
			
			// Boolean isExtended says solenoids are not extended
			isExtended = false;
			
		}

		else {
			
			// Set solenoids to off
			LeftSolenoid.set(DoubleSolenoid.Value.kOff);
			RightSolenoid.set(DoubleSolenoid.Value.kOff);
			
		}
		
		if (redFour.get()){
			
			// Grab
			leftGrab.set(-0.5);
			rightGrab.set(0.5);
		
		}
		else if (redSix.get()) {
			
			// Negative grab??
			leftGrab.set(0.5);
			rightGrab.set(-0.5);
			
		}
		else{
			
			// Set Grab to off
			leftGrab.set(0.0);
			rightGrab.set(0.0);
		}
		
		// Define drive type as tank drive (two joysticks)
		 drive.tankDrive(red.getY(), black.getY());
		 
		 //Boolean for red.getTrigger() for toggle
		 solToggle = red.getTrigger();
		
    }		

	
	@Override
	public void testPeriodic() {
	}
}

//	 .- .-.. . -..- .. .-  -- .- -.-- .
