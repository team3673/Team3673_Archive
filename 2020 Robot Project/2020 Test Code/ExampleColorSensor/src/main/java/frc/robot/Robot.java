/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;


import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.buttons.Button;

/**
 * This is a demo program showing the use of the RobotDrive class, specifically
 * it contains the code necessary to operate a robot with tank drive.
 */
public class Robot extends TimedRobot {
  private DifferentialDrive m_myRobot;
  private Joystick m_leftStick;
  private Joystick m_rightStick;

  private final I2C.Port i2cPort = I2C.Port.kOnboard;

  private final ColorSensorV3 m_colorSensor = new ColorSensorV3(i2cPort);

  private final ColorMatch m_colorMatcher = new ColorMatch();

  public Spark colorWheel = new Spark(2);

  private final Color kBlueTarget = ColorMatch.makeColor(0.143, 0.427, 0.429);
  private final Color kGreenTarget = ColorMatch.makeColor(0.197, 0.561, 0.240);
  private final Color kRedTarget = ColorMatch.makeColor(0.561, 0.232, 0.114);
  private final Color kYellowTarget = ColorMatch.makeColor(0.361, 0.524, 0.113);


  public static XboxController xController = new XboxController(0);
  public static Button xContRightTrigger = new JoystickButton(xController, 8);
  public static Button xContLeftTrigger = new JoystickButton(xController, 7);

  boolean XboxRightPressed;

  boolean XboxLeftPressed; 

  public Encoder leftEncoder = new Encoder(6, 7, false, Encoder.EncodingType.k4X);
  public Encoder rightEncoder = new Encoder(8, 9, false, Encoder.EncodingType.k4X);
  
  public double wheelDiameter = 5.98;


  @Override
  public void robotInit() {

    m_leftStick = new Joystick(1);
    m_rightStick = new Joystick(2);



    final Spark leftDrive = new Spark(0);
    leftDrive.setInverted(true);

    final Spark rightDrive = new Spark(1);
    rightDrive.setInverted(true);

    

    /*
    final Spark colorWheel = new Spark(3);

    final Spark colorWheel = new Spark(4);

    final Spark colorWheel = new Spark(5);

    final Spark colorWheel = new Spark(6);
    */

    m_myRobot = new DifferentialDrive(leftDrive, rightDrive);

    m_colorMatcher.addColorMatch(kBlueTarget);
    m_colorMatcher.addColorMatch(kGreenTarget);
    m_colorMatcher.addColorMatch(kRedTarget);
    m_colorMatcher.addColorMatch(kYellowTarget); 


  }

  public void autonomousInit() {
    
  }
  public void autonomousPeriodic() {

  }
  @Override
  public void teleopPeriodic() {
    m_myRobot.tankDrive(m_leftStick.getY(), m_rightStick.getY());

    final Color detectedColor = m_colorSensor.getColor();

    final double IR = m_colorSensor.getIR();

    SmartDashboard.putNumber("POV", xController.getPOV());

    SmartDashboard.putNumber("Red", detectedColor.red);
    SmartDashboard.putNumber("Green", detectedColor.green);
    SmartDashboard.putNumber("Blue", detectedColor.blue);
    SmartDashboard.putNumber("IR", IR);

    final int proximity = m_colorSensor.getProximity();

    SmartDashboard.putNumber("Proximity", proximity);

    final String colorString;
    final ColorMatchResult match = m_colorMatcher.matchClosestColor(detectedColor);

    boolean booleanRed = false;
    boolean booleanGreen = false;
    boolean booleanBlue = false;
    boolean booleanYellow = false;
    
    XboxRightPressed = xContRightTrigger.get();
    XboxLeftPressed = xContLeftTrigger.get();
    
    if (XboxRightPressed == true && XboxLeftPressed == true) {
      colorWheel.set(0.0);
    } else if (XboxLeftPressed == true) {
      colorWheel.set(-0.5);
    } else if (XboxRightPressed == true) {
      colorWheel.set(0.5);
    }else {
      colorWheel.set(0.0);
    }

    
    if (match.color == kBlueTarget) {
      colorString = "Blue";
      booleanBlue = true;
    } else if (match.color == kRedTarget) {
      colorString = "Red";
      booleanRed = true;
    } else if (match.color == kGreenTarget) {
      colorString = "Green";
      booleanGreen = true;
    } else if (match.color == kYellowTarget) {
      colorString = "Yellow";
      booleanYellow = true;
    } else {
      colorString = "Unknown";
    }
    
    SmartDashboard.putBoolean("isRed", booleanRed);
    SmartDashboard.putBoolean("isYellow", booleanYellow);
    SmartDashboard.putBoolean("isBlue", booleanBlue);
    SmartDashboard.putBoolean("isGreen", booleanGreen);
   /* SmartDashboard.putBoolean("XboxRightTrigger", XboxRightPressed);
    SmartDashboard.putBoolean("XboxLeftTrigger", XboxLeftPressed);*/


  
    /*
    Shuffleboard.getTab("Colors")
  .addBoolean("isRed", () -> match.color == kRedTarget)
  .withProperties(Map.of("colorWhenTrue", "red"));
Shuffleboard.getTab("Colors")
  .addBoolean("isYellow", () -> match.color == kYellowTarget)
  .withProperties(Map.of("colorWhenTrue", "yellow"));
Shuffleboard.getTab("Colors")
  .addBoolean("isBlue", () -> match.color == kBlueTarget)
  .withProperties(Map.of("colorWhenTrue", "blue"));
Shuffleboard.getTab("Colors")
  .addBoolean("isGreen", () -> match.color == kGreenTarget)
  .withProperties(Map.of("colorWhenTrue", "green"));
  */
  //automatically changes properties for color block
    
    SmartDashboard.putNumber("Red", detectedColor.red);
    SmartDashboard.putNumber("Green", detectedColor.green);
    SmartDashboard.putNumber("Blue", detectedColor.blue);
    SmartDashboard.putNumber("Confidence", match.confidence);
    SmartDashboard.putString("Detected Color", colorString);
    
    
  }
}
