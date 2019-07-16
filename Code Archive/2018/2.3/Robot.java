/*
 *	This code is property of the Seaside High School Robotics team, C.Y.B.O.R.G. Seagulls.
 *	Used for FIRST FRC PowerUp 2018 
 *	Alexia M. Walgren 02.19.18
 */


package org.usfirst.frc.team3673.robot;

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
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.buttons.JoystickButton;


public class Robot extends IterativeRobot {
	
	// Define joysticks
	public Joystick red = new Joystick(1);
	public Joystick black = new Joystick(0);
	
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
	public double distance = -encode.getDistance();
	
	// Assign PWM ports to the spark motor controllers
	public Spark rightFront = new Spark(3);
	public Spark rightBack = new Spark(1); 
	public Spark leftFront = new Spark(4);
	public Spark leftBack = new Spark(2); 
	public Spark highGuy = new Spark(5);
	public Spark dartActuator = new Spark(6);
	public Spark leftGrab = new Spark(7);
	public Spark rightGrab = new Spark(8);
	public Spark newFour = new Spark(9);
	
	// Add grabber motors to grab group
	public SpeedControllerGroup grab = new SpeedControllerGroup(leftGrab, rightGrab);
	
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
		
		//Reset rotation count
		encode.reset();
		
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
		
	}

	
	@Override
	public void autonomousPeriodic() {
		
		// Use this to get distance
		double distance = encode.getDistance();
		
		// Get game information in the format of R/L (your switch) R/L (the scale) R/L (their switch)
		String gameData = DriverStation.getInstance().getGameSpecificMessage();
		
		// If the robot is at Station 1
		if(DriverStation.getInstance().getLocation() == 1){
			
			// If enemy alliance's switch is on our side
			if(gameData.charAt(2) == 'L'){
				
				//1, **L
				System.out.println("1, **L");
				
				// If you've not yet moved 386.6" and auto is on step 1	
				if(distance < 386.6 && autoStep == 1){
					
					// Move forward
					leftSpeed = -0.5;
					rightSpeed = -0.48;
					
					// Reset ahrs
					ahrs.reset();
					
				}
				
				// If you have moved 386.6"
				else{
					
					// Keep robot from moving forward
					rightSpeed = 0.0;
					leftSpeed = 0.0;
					
					// If robot has not yet turned 90 degrees (60 = 90, don't ask me why, it just does)
					if(ahrs.getAngle() < 60){
						
						// Turn right
						leftSpeed = -0.5;
						rightSpeed = 0.48;
						
						// Set auto step to 2
						autoStep = 2;
						
						// Reset encoder count and therefore distance 
						encode.reset();
						
					}
					
					// Robot has turned 90 degrees
					else {
						
						// Keep robot from turning right 
						rightSpeed = 0.0;
						leftSpeed = 0.0;
						
						// If you've not yet moved 53" and auto is on step 2
						if (distance < 53 && autoStep == 2) {
							
							// Move forward
							leftSpeed = -0.5;
							rightSpeed = -0.48;
							
							// Reset ahrs
							ahrs.reset();
							
						}
						
						// You have moved 53"
						else{
							
							// Keep robot from moving forward
							rightSpeed = 0.0;
							leftSpeed = 0.0;
							
							// If robot has not yet turned 90 degrees 
							if (ahrs.getAngle() < 60){
								
								// Turn left
								leftSpeed = 0.5;
								rightSpeed = -0.48;
								
							}
							
							// Robot has turned 90 degrees
							else {
								
								// Keep robot from turning left
								rightSpeed = 0.0;
								leftSpeed = 0.0;
								
								// Reset encoder count and therefore distance
								encode.reset();
								
								// If you've not yet moved 32.8"
								if (distance < 32.8){
									
									// Move forward
									leftSpeed = -0.5;
									rightSpeed = -0.48;
									
									// Reset ahrs
									ahrs.reset();
									
								}
								
								// You have moved 32.8"
								else{
									
									// Keep robot from moving forward
									rightSpeed = 0.0;
									leftSpeed = 0.0;
									
									// If we haven't yet released the grabber in auto
									if(grabBool == false){
									
										// Set solenoids to go in reverse
										LeftSolenoid.set(DoubleSolenoid.Value.kReverse);
										RightSolenoid.set(DoubleSolenoid.Value.kReverse);
									
										// Set grabBool to true so it only does this once
										grabBool = true;
										
									}
									
									// Grabber has already been released
									else {
										
										// Turn off solenoid values
										LeftSolenoid.set(DoubleSolenoid.Value.kOff);
										RightSolenoid.set(DoubleSolenoid.Value.kOff);
										
									}
									
								}
							}
						}	
					}	
				}
			} 
			
			//1, **R
			else{
				
				// Else, their switch is on the right
				System.out.println("1, **R");
				
				// If our switch is on the left
				if(gameData.charAt(0) == 'L'){
					
					//1, L*R
					System.out.println("1, L*R");
					
					// If you've not yet moved 168"
					if (distance < 168){
						
						// Move forward
						leftSpeed = -0.5;
						rightSpeed = -0.48;
						
						// Reset ahrs
						ahrs.reset();
						
					}
					
					// You have moved 168"
					else{
						
						// Keep robot from moving forward
						rightSpeed = 0.0;
						leftSpeed = 0.0;
						
						// If robot has not yet turned 90 degrees
						if(ahrs.getAngle() < 60){
							
							// Turn right
							leftSpeed = -0.5;
							rightSpeed = 0.48;
							
							// Reset encoder count and therefore distance
							encode.reset();
							
						}
						
						// Robot has turned 90 degrees
						else{
							
							// Keep robot from turning right 
							rightSpeed = 0.0;
							leftSpeed = 0.0;
							
							// If robot has not yet moved 38.3"
							if(distance < 38.3){
								
								// Move forward
								leftSpeed = -0.5;
								rightSpeed = -0.48;
								
								// Reset ahrs
								ahrs.reset();
							}
							
							// Robot has moved 38.3"
							else{
								
								// Keep robot from moving forward
								rightSpeed = 0.0;
								leftSpeed = 0.0;
								
								// If we haven't yet released the grabber in auto
								if(grabBool == false){
								
									// Set solenoids to go in reverse
									LeftSolenoid.set(DoubleSolenoid.Value.kReverse);
									RightSolenoid.set(DoubleSolenoid.Value.kReverse);
								
									// Set grabBool to true so it only does this once
									grabBool = true;
									
								}
								
								// Grabber has already been released
								else {
									
									// Turn off solenoid values
									LeftSolenoid.set(DoubleSolenoid.Value.kOff);
									RightSolenoid.set(DoubleSolenoid.Value.kOff);
									
								}
								
							}	
						}	
					}
				} 
				
				//1, R*R
				else 
				{
					System.out.println("1, R*R");
					
					
					//If robot has not yet moved 2"
					if (distance < 2) {
						
						// Move forward
						leftSpeed = -0.5;
						rightSpeed = -0.48;
					
						// Reset ahrs
						ahrs.reset();
						
					}
					
					// Robot has moved 2"
					else {
						
						// Keep robot from moving forward
						leftSpeed = 0.0;
						rightSpeed = 0.0;
						
						if(ahrs.getAngle() < 60){
							
							// Turn right
							leftSpeed = -0.5;
							rightSpeed = 0.48;
							
							
							
						}
						
						// Robot has turned 90 degrees
						else {
							
							// Keep robot from turning right 
							rightSpeed = 0.0;
							leftSpeed = 0.0;
							
							// Reset encoder count and therefore distance
							encode.reset();
							
						}
					}
				}
			}
		}				
		
		
		
		
		
		
		// If the robot is at Station 2
		else if(DriverStation.getInstance().getLocation() == 2){
			
			// If their switch is on the left
			if(gameData.charAt(2) == 'L')
			{
				//2, **L
				System.out.println("2, **L");
			} 
			else 
			{
				//2, **R
				System.out.println("2, **R");

			}			
		}
		
		
		
		
		
		
		// If the robot is at Station 3
		else{
			
			// If enemy alliance's switch is on our side
			if(gameData.charAt(2) == 'R'){
				System.out.println("3, **R");

				//3, **R
				
				// If you've not yet moved 386.6" and auto is on step 1
				if(distance < 386.6 && autoStep == 1){
					
					// Move forward
					leftSpeed = -0.5;
					rightSpeed = -0.48;
					
					// Reset ahrs
					ahrs.reset();
					
				}
				
				else{
					
					// Keep robot from moving forward
					rightSpeed = 0.0;
					leftSpeed = 0.0;
					
					// If robot has not yet turned 90 degrees 
					if(ahrs.getAngle() < 60){
						
						// Turn left
						leftSpeed = 0.5;
						rightSpeed = -0.48;
						
						// Set auto step to 2
						autoStep = 2;
						
						// Reset encoder count and therefore distance 
						encode.reset();
						
					}
					else {
						
						// Keep robot from turning left 
						rightSpeed = 0.0;
						leftSpeed = 0.0;
						
						// If you've not yet moved 53" and auto is on step 2
						if (distance < 53 && autoStep == 2) {
							
							// Move forward
							leftSpeed = -0.5;
							rightSpeed = -0.48;
							
							// Reset ahrs
							ahrs.reset();
							
						}
						else{
							
							// Keep robot from moving forward
							rightSpeed = 0.0;
							leftSpeed = 0.0;
							
							// If robot has not yet turned 90 degrees 
							if (ahrs.getAngle() < 60){
								
								// Turn right
								leftSpeed = -0.5;
								rightSpeed = 0.48;
								
							}
							else {
								
								// Keep robot from turning left
								rightSpeed = 0.0;
								leftSpeed = 0.0;
								
								// Reset encoder count and therefore distance
								encode.reset();
								
								// If you've not yet moved 32.8"
								if (distance < 32.8){
									
									// Move forward
									leftSpeed = -0.5;
									rightSpeed = -0.48;
									
									// Reset ahrs
									ahrs.reset();
									
								}
								else{
									
									// Keep robot from moving forward
									rightSpeed = 0.0;
									leftSpeed = 0.0;
									
									// If we haven't yet released the grabber in auto
									if(grabBool == false){
									
										// Set solenoids to go in reverse
										LeftSolenoid.set(DoubleSolenoid.Value.kReverse);
										RightSolenoid.set(DoubleSolenoid.Value.kReverse);
									
										// Set grabBool to true so it only does this once
										grabBool = true;
										
									}
									
									// Grabber has already been released
									else {
										
										// Turn off solenoid values
										LeftSolenoid.set(DoubleSolenoid.Value.kOff);
										RightSolenoid.set(DoubleSolenoid.Value.kOff);
										
									}
									
								}
							}
						}	
					}	
				}
			} 
			
			else {
				
				System.out.println("3, **L");

				//3, **L
				if(gameData.charAt(0) == 'R') {
					
					//3, R*R
					System.out.println("3, R*R");

				} 
				else {
					
					//3, R*L
					System.out.println("3, R*L");

				}	
			}			
		}
		
		// Set drive parameters
		drive.tankDrive(leftSpeed, rightSpeed);
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
							dartActuator.set(0.8);
						
						}
						
						// If third button on red controller is pressed
						else if (redFive.get()){
							
							// Set actuator to go in
							dartActuator.set(-0.8);
						
						}
						
						// If neither the third nor fifth red button are pressed
						else{
							
							// Set climber to be off
							dartActuator.set(0.0);
							
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
		
		if (blackFour.get()){
			
			// Grab
			grab.set(-0.5);
		
		}
		else if (blackSix.get()) {
			
			// Negative grab??
			grab.set(0.5);
			
		}
		else{
			
			// Set Grab to off
			grab.set(0.0);
			
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
