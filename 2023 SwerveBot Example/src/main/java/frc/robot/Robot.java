// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

public class Robot extends TimedRobot {
  private final XboxController m_controller = new XboxController(0);
  private final Drivetrain m_swerve = new Drivetrain();

  // Slew rate limiters to make joystick inputs more gentle; 1/3 sec from 0 to 1.
  private final SlewRateLimiter m_xspeedLimiter = new SlewRateLimiter(3);
  private final SlewRateLimiter m_yspeedLimiter = new SlewRateLimiter(3);
  private final SlewRateLimiter m_rotLimiter = new SlewRateLimiter(3);

  private double encValDriveFL;
  private double encValDriveFR;
  private double encValDriveBL;
  private double encValDriveBR;
  private double encValTurnFL;
  private double encValTurnFR;
  private double encValTurnBL;
  private double encValTurnBR;

  public void dashboardUpdate(int testSelected) {  
    
    encValDriveFL = m_swerve.getFrontLeft().getDriveEncoder().getPosition();
    encValTurnFL = m_swerve.getFrontLeft().getTurningEncoder().getPosition();
    
    encValDriveFR = m_swerve.getFrontRight().getDriveEncoder().getPosition();
    encValTurnFR = m_swerve.getFrontRight().getTurningEncoder().getPosition();
    
    encValDriveBL = m_swerve.getBackLeft().getDriveEncoder().getPosition();
    encValTurnBL = m_swerve.getBackLeft().getTurningEncoder().getPosition();
    
    encValDriveBR = m_swerve.getBackRight().getDriveEncoder().getPosition();
    encValTurnBR = m_swerve.getBackRight().getTurningEncoder().getPosition();
    SmartDashboard.updateValues();
    switch(testSelected) {
      case 1:
        System.out.println("encValDriveFR = " + encValDriveFR + "    encValTurnFR = " + encValTurnFR);
        break;
      case 2:  
        System.out.println("encValDriveFL = " + encValDriveFL + "    encValTurnFL = " + encValTurnFL);
        break;
      case 3:  
        System.out.println("encValDriveBL = " + encValDriveBL + "    encValTurnBL = " + encValTurnBL);
        break;
      case 4:  
        System.out.println("encValDriveBR = " + encValDriveBR + "    encValTurnBR = " + encValTurnBR);
        break;
    }
  }

  public void robotInit() {  
  //  SmartDashboard.putBoolean("FL Selected", selected[1]);
    SmartDashboard.putNumber("FL Drive", encValDriveFL);
    SmartDashboard.putNumber("FL Turning", encValTurnFL);
  //  SmartDashboard.putBoolean("FR Selected", selected[2]);
    SmartDashboard.putNumber("FR Drive", encValDriveFR);
    SmartDashboard.putNumber("FR Turning", encValTurnFR);
  //  SmartDashboard.putBoolean("BL Selected", selected[3]);
    SmartDashboard.putNumber("BL Drive", encValDriveBL);
    SmartDashboard.putNumber("BL Turning", encValTurnBL);
  //  SmartDashboard.putBoolean("BR Selected", selected[4]);
    SmartDashboard.putNumber("BR Drive", encValDriveBR);
    SmartDashboard.putNumber("BR Turning", encValTurnBR);
  }

  public void robotPeriodic() {
    
  }

  @Override
  public void autonomousPeriodic() {
    driveWithJoystick(false);
    m_swerve.updateOdometry();
    dashboardUpdate(0);
  }

  @Override
  public void teleopPeriodic() {
    driveWithJoystick(true);
    //System.out.println ("Gyro = " + m_swerve.getGyro().getRotation2d().getDegrees());
    dashboardUpdate(0);
  }

  private void driveWithJoystick(boolean fieldRelative) {
    // Get the x speed. We are inverting this because Xbox controllers return
    // negative values when we push forward.
    final var xSpeed =
        -m_xspeedLimiter.calculate(MathUtil.applyDeadband(m_controller.getLeftY(), 0.05))
            * Drivetrain.kMaxSpeed;

    // Get the y speed or sideways/strafe speed. We are inverting this because
    // we want a positive value when we pull to the left. Xbox controllers
    // return positive values when you pull to the right by default.
    final var ySpeed =
        -m_yspeedLimiter.calculate(MathUtil.applyDeadband(m_controller.getLeftX(), 0.05))
            * Drivetrain.kMaxSpeed;

    // Get the rate of angular rotation. We are inverting this because we want a
    // positive value when we pull to the left (remember, CCW is positive in
    // mathematics). Xbox controllers return positive values when you pull to
    // the right by default.
    final var rot =
        -m_rotLimiter.calculate(MathUtil.applyDeadband(m_controller.getRightX(), 0.05))
            * Drivetrain.kMaxAngularSpeed;

    m_swerve.drive(xSpeed, ySpeed, rot, fieldRelative);
  }
  
  int testSelector;
  boolean selected[] = { false, false, false, false, false };
  boolean buttonTestNextServiced;
  boolean buttonTestPrevServiced;
  JoystickButton buttonTestNext;
  JoystickButton buttonTestPrev;
  JoystickButton buttonTestZeroEncoder;


  public void testInit() {
    testSelector = 1;
    selected[testSelector] = true;
    buttonTestNext = new JoystickButton(m_controller, 3);
    buttonTestPrev = new JoystickButton(m_controller, 1);
    buttonTestZeroEncoder = new JoystickButton(m_controller, 2);
    
  }
  

  public void testPeriodic() {
    //System.out.println("leftX = " + m_controller.getLeftX() + "    leftY = " + m_controller.getLeftY());
    if(buttonTestNext.getAsBoolean()) {
      if(!buttonTestNextServiced) {
        selected[testSelector] = false;
        testSelector++;
        if(testSelector > 4) {
          testSelector = 1;
        }
        selected[testSelector] = true;
        buttonTestNextServiced = true;
      }
    } else {
      buttonTestNextServiced = false;
    }
  
    if(buttonTestPrev.getAsBoolean()) {
      if(!buttonTestPrevServiced) {
        selected[testSelector] = false;
        testSelector--;
        if(testSelector < 1) {
          testSelector = 4;
        }
        selected[testSelector] = true;
        buttonTestPrevServiced = true;
      }
    } else {
      buttonTestPrevServiced = false;
    }

    if(buttonTestZeroEncoder.getAsBoolean()) {
      SwerveModule swerveModule;
      switch (testSelector) {
        case 1:
          swerveModule = m_swerve.getFrontRight();
          break;
        case 2:
          swerveModule = m_swerve.getFrontLeft();
          break;
        case 3:
          swerveModule = m_swerve.getBackRight();
          break;
        default:
          swerveModule = m_swerve.getBackLeft();
          break;    
      }
      swerveModule.getTurningEncoder().setPositionToAbsolute();
    }

    double driveSpeed = m_controller.getLeftY();
    double turnSpeed = m_controller.getLeftX();
  
    switch(testSelector) {
      case 1:
        m_swerve.getFrontRight().getDriveMotor().set(driveSpeed);
        m_swerve.getFrontRight().getTurningMotor().set(turnSpeed);
//        System.out.println("testSelector = FL");
        break;
      case 2:
        m_swerve.getFrontLeft().getDriveMotor().set(driveSpeed);
        m_swerve.getFrontLeft().getTurningMotor().set(turnSpeed);
//        System.out.println("testSelector = FR");
        break;
      case 3:
        m_swerve.getBackLeft().getDriveMotor().set(driveSpeed);
        m_swerve.getBackLeft().getTurningMotor().set(turnSpeed);
//        System.out.println("testSelector = BL");
        break;
      case 4:
        m_swerve.getBackRight().getDriveMotor().set(driveSpeed);
        m_swerve.getBackRight().getTurningMotor().set(turnSpeed);
//        System.out.println("testSelector = BR");
        break;
    }
    
    dashboardUpdate(testSelector);
  }
}
