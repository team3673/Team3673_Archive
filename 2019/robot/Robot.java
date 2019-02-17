/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

// This code demonstrates the Rev Robotics Digit board.  It is an enhancement on the work published
// by FRC teams 1493 and 1504.

// Autonomous mode cycles through the character set, sending 4 letters at a time to the 4-digit display.
// The current state of the Potentiometer and the two Buttons are displayed on the SmartDashboard.

// Teleop mode animates four independent "snakes" of varying length, one on each digit.
// Button A changes which snake is currently selected (as identified by the decimal point).
// Button B increases the length of the snake, rolling back to one when the maximum value is passed.
// The Potentiometer changes the movement speed of the currently selected snake.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.AnalogInput;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

//I2C address of the digit board is 0x70
private I2C i2c = new I2C(Port.kMXP, 0x70);
	
// Buttons A and B are keyed to dgital inputs 19 and 20	
	private DigitalInput buttonA = new DigitalInput(19);
	private DigitalInput buttonB = new DigitalInput(20);
	
	private Toggle toggleA = new Toggle();
	private Toggle toggleB = new Toggle();
	
// The potentiometer is keyed to AI 3	
	private AnalogInput pot = new AnalogInput(3);
	
	private Snake snakes[] = {
		new Snake(0),
		new Snake(1),
		new Snake(2),
		new Snake(3)
	};
	
	private int selector = snakes.length - 1;
  private static final int potMax = 218;
  private static final int potMin = 199;
	private static final int speedMax = 100;
	private int speedIdle;
	
	private int countDown;
	private static final int delayTicks = 25;
	private int i;
  private int c;
	
  private byte[] byte1 = new byte[10];
  ScrollText scrollText;
	
  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);

 // set up the board - turn on, set blinking and brightness   
    byte[] osc = new byte[1];
    byte[] blink = new byte[1];
    byte[] bright = new byte[1];
    osc[0] = (byte)0x21;
    blink[0] = (byte)0x81;
    bright[0] = (byte)0xEF;

    i2c.writeBulk(osc);
    Timer.delay(.01);
    i2c.writeBulk(bright);
    Timer.delay(.01);
    i2c.writeBulk(blink);
    Timer.delay(.01);

    scrollText = new ScrollText("Team 3673 Seaside C.Y.B.O.R.G. Seagulls");
  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
  }

  @Override
  public void disabledInit() {
    countDown = delayTicks;
  }

  @Override
  public void disabledPeriodic() {
    countDown--;
    if(countDown < 0) {
      scrollText.update(i2c);
      countDown = delayTicks;
    }
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to
   * the switch structure below with additional strings. If using the
   * SendableChooser make sure to add them to the chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
    c = 0;

    countDown = delayTicks;
    i = 0;
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    switch (m_autoSelected) {
      case kCustomAuto:
        // Put custom auto code here
        break;
      case kDefaultAuto:
      default:
        // Put default auto code here
        break;
    }

		countDown--;
		if(countDown < 0) {
// store the desired characters in a byte array, then write array to the board

// first reset the array to zeros
			for(int i = 0; c < 10; c++) {
				byte1[i] = (byte)(0b00000000) & 0xFF;
			}

// put the character data in the array    	
//			for(int c = 0; c < sizeof(charreg)/sizeof(charreg[0]); c++) {
				byte1[0] = (byte)(0b0000111100001111);
				byte1[2] = Digit.CharReg[c+3][0];
				byte1[3] = Digit.CharReg[c+3][1];
				byte1[4] = Digit.CharReg[c+2][0];
				byte1[5] = Digit.CharReg[c+2][1];
				byte1[6] = Digit.CharReg[c+1][0];
				byte1[7] = Digit.CharReg[c+1][1];
				byte1[8] = Digit.CharReg[c][0];
				byte1[9] = Digit.CharReg[c][1];
// send the array to the board
				i2c.writeBulk(byte1);
//    			Timer.delay(3);
//			}
			c++;
			if(c >= (Digit.CharReg.length - 3)) {
				c = 0;
			}
			countDown = delayTicks;
		}
        
    	SmartDashboard.putString("DB/String 1", "ButtonA = " + buttonA.get());
    	SmartDashboard.putString("DB/String 2", "ButtonB = " + buttonB.get());
    	SmartDashboard.putString("DB/String 3", "Analog = " + pot.getAverageValue());
//    	Timer.delay(.01);

// The following line is for using the vendor's library to access the board instead of direct writes to the I2C port
//       MXPDigitBoard.directWrite(data);
  }
	
	public void teleopInit() {
    byte1[0] = 0b00001111;
    byte1[1] = 0b00001111;

    countDown = delayTicks;
    i = 0;
}

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
		if(toggleA.isToggle(buttonA.get()) && buttonA.get()) {
			selector--;
			if(selector < 0) {
				selector = snakes.length - 1;
			}
			speedIdle = potScale();
		}
		
		if(toggleB.isToggle(buttonB.get()) && buttonB.get()) {
			snakes[selector].incLength();
		}

		if(potScale() != speedIdle) {
			snakes[selector].setSpeed(potScale());
		}
		
		for(Snake snake : snakes) {
			snake.moveSnake();
			byte1[(snake.getID()+1)*2] = snake.getByteL(selector);
			byte1[(snake.getID()+1)*2+1] = snake.getByteH(selector);
		}
   	i2c.writeBulk(byte1);
	}
	
	private int potScale() {
		return (int) ((double) (pot.getAverageValue()-potMin) / (potMax-potMin) * speedMax);
  }
  
  @Override
  public void testInit() {
    countDown = delayTicks;
    i = 0;
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
    countDown--;
    if(countDown < 0) {
      byte1[2] = Digit.Segments[i][0];
      byte1[3] = Digit.Segments[i][1];
      byte1[4] = 0b00000000;
      byte1[5] = 0b00000000;
      byte1[6] = Digit.CharReg[i%10+16][0];
      byte1[7] = Digit.CharReg[i%10+16][1];
      byte1[8] = Digit.CharReg[i/10+16][0];
      byte1[9] = Digit.CharReg[i/10+16][1];
      i2c.writeBulk(byte1);

      i++;
      if(i >= 15) {
        i = 0;
      }
      countDown = delayTicks;
    }
  }
}
