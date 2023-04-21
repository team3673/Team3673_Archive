// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

// Import libraries (usually auto-populated when clicking the light bulb and choosing to Add import from a wpilibj source)

package frc.robot;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
//import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;
//import edu.wpi.first.wpilibj.SerialPort;
//import com.kauailabs.navx.frc.AHRS;
// If the AHRS import above is red, try uninstalling the Kauai vendor library
// and re-install it online using the instructions below
// - Connect to the internet
// - In the top right, click on the red circle with a white "W"
// - Choose Manage Vendor Libraries | Manage Current Libraries
// - Check "KauaiLabs_navX_FRC" and click "OK"
// - Choose the red circle with a white "W" again
// - Choose Manage Vendor Libraries | 
// - Choose "Install new libraries (online)"
// - Paste the link https://dev.studica.com/releases/2023/NavX.json
// - Press <Enter>

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  // Auto-generated code for populating dashboard
  // Add items to this to create a selector for which Autonomous program to run
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

// Declarations for global variables / objects

  // Dashboard objects
  //private ShuffleboardTab tab;
  //private GenericEntry dashboardLeftDistance;
  //private GenericEntry dashboardRightDistance;
  //private GenericEntry dashboardShoulderAngle;
  //private GenericEntry dashboardElbow1Angle;
  //private GenericEntry dashboardElbow2Angle;
  //private GenericEntry dashboardHandPosition;
//  private GenericEntry dashboardPitch;
//  private GenericEntry dashboardRoll;
//  private GenericEntry dashboardYaw;

  // Drive train motor controllers
  private Spark leftDriveMotor;
  private Spark rightDriveMotor;

  // Drive train
  private DifferentialDrive driveTrain;

  // Controllers
  private Joystick leftJoystick;
  private Joystick rightJoystick;
  //private Joystick customController;

  // Motor controllers for manipulator
  private TalonFX shoulderMotor;
//  private TalonFXConfiguration shoulderMotorAllConfigs;
  private Spark elbowMotor;
  private Spark handMotor;

  // Controller buttons
  private JoystickButton shoulderUpButton;
  private JoystickButton shoulderDownButton;
  private JoystickButton elbowExtendButton;
  private JoystickButton elbowRetractButton;
  private JoystickButton handOpenButton;
  private JoystickButton handCloseButton;
  private JoystickButton assistLevel1Button;
  private JoystickButton assistLevel2Button;
  private JoystickButton assistLevel3Button;
  private JoystickButton assistRestingButton;
//  private JoystickButton teeterTotterModeButton;

  private int autoPhase;
  private int assistMode;

  // Isaiah's custom controller buttons for WASD driving
  //private JoystickButton buttonW;
  //private JoystickButton buttonA;
  //private JoystickButton buttonS;
  //private JoystickButton buttonD;
  //private JoystickButton buttonTurbo;

  // Kauai Labs NavX MXP add-on board - 3-axis accelerometer, gyroscope and magnetometer
//  private AHRS navX;

  // Transmission encoder and wheel constants
  public static double kDriveTrainWheelDiameter = 6.0;
  public static double kDriveTrainEncoderPulsesPerRotation = 360.0;

  // Neverest orbital gear motor contants
  public static double kGearMotorRatio = 263.7;
  public static double kGearMotorEncoderPulsesPerRotation = 7.0;

  // Falcon 500 / TalonFX integrated encoder constants
  public static double kFalconTicksPerDegree = 700.0;

  // navX-MXP constants
  //public static double kPitchLevel = 1.68;

  // Rotation sensors for each motor
  private Encoder leftDriveEncoder;
  private Encoder rightDriveEncoder;
  private Encoder elbowEncoder1;
  private Encoder elbowEncoder2;
  private Encoder handEncoder;

//  private double targetHeading;

  public double avg(double a, double b) {
    return (a + b) / 2;
  }

  public double min(double a, double b) {
    return (a < b) ? a : b;
  }

  public double max(double a, double b) {
    return (a > b) ? a : b;
  }

  public double avgDriveEncoder() {
    return avg(leftDriveEncoder.getDistance(), rightDriveEncoder.getDistance());
  }

  public double avgElbowEncoder() {
    return avg(elbowEncoder1.getDistance(), elbowEncoder2.getDistance());
  }

  public double minDriveEncoder() {
    return min(leftDriveEncoder.getDistance(), rightDriveEncoder.getDistance());
  }

  public double minElbowEncoder() {
    return min(elbowEncoder1.getDistance(), elbowEncoder2.getDistance());
  }

  public double maxDriveEncoder() {
    return max(leftDriveEncoder.getDistance(), rightDriveEncoder.getDistance());
  }

  public double maxElbowEncoder() {
    return max(elbowEncoder1.getDistance(), elbowEncoder2.getDistance());
  }

  public double shoulderAngle() {
    return shoulderMotor.getSelectedSensorPosition() / kFalconTicksPerDegree;
  }


  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    // Auto-generated code for populating dashboard
    // Add items to this to create a selector for which Autonomous program to run
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);

    // Publish values to the dashboard
   // tab = Shuffleboard.getTab("SmartDashboard");
    //dashboardLeftDistance = tab.add("Left Distance", 0).getEntry();
    //dashboardRightDistance = tab.add("Right Distance", 0).getEntry();
    //dashboardShoulderAngle = tab.add("Shoulder Angle", 0).getEntry();
    //dashboardElbow1Angle = tab.add("Elbow 1 Angle", 0).getEntry();
    //dashboardElbow2Angle = tab.add("Elbow 2 Angle", 0).getEntry();
    //dashboardHandPosition = tab.add("Hand Position", 0).getEntry();
//    dashboardPitch = tab.add("Pitch", 0).getEntry();
//    dashboardRoll = tab.add("Roll", 0).getEntry();
//    dashboardYaw = tab.add("Yaw", 0).getEntry();
    

    // Publish cameras to dashboard
    CameraServer.startAutomaticCapture(); // USB Camera 0
    // Identical line run again starts another camera
    CameraServer.startAutomaticCapture(); // USB Camera 1

// Allocate Objects
    // Drive train motor controllers
    leftDriveMotor = new Spark(0); // PWM Y-cable to synchronize two separate Spark motor controllers
    leftDriveMotor.setInverted(true);
    rightDriveMotor = new Spark(2); // PWM Y-cable to synchronize two separate Spark motor controllers

    driveTrain = new DifferentialDrive(leftDriveMotor, rightDriveMotor);

    leftJoystick = new Joystick(0);
    rightJoystick = new Joystick(1);
    //customController = new Joystick(2);

    shoulderMotor = new TalonFX(1); // The TalonFX running the Falcon 500 is on the CANbus (not a PWM port)
    shoulderMotor.setInverted(true);
//    shoulderMotorAllConfigs = new TalonFXConfiguration();
//    shoulderMotor.getAllConfigs(shoulderMotorAllConfigs);
//    System.out.println("shoulderMotor pulseWidthPeriod = " + shoulderMotorAllConfigs.pulseWidthPeriod_EdgesPerRot);
    elbowMotor = new Spark(5); // PWM Y-cable to synchronize two separate Spark motor controllers
    elbowMotor.setInverted(true);
    handMotor = new Spark(6);
    handMotor.setInverted(true);

    //shoulderUpButton = new JoystickButton(customController,7);
    //shoulderDownButton = new JoystickButton(customController, 8);
    shoulderUpButton = new JoystickButton(leftJoystick,7);
    shoulderDownButton = new JoystickButton(leftJoystick, 8);
    elbowExtendButton = new JoystickButton(leftJoystick, 6);
    elbowRetractButton = new JoystickButton(leftJoystick, 9);
    handOpenButton = new JoystickButton(leftJoystick, 5);
    handCloseButton = new JoystickButton(leftJoystick,10);
    assistLevel1Button = new JoystickButton(leftJoystick, 11);
    assistLevel2Button = new JoystickButton(leftJoystick, 12);
    assistLevel3Button = new JoystickButton(leftJoystick, 13);
    assistRestingButton = new JoystickButton(leftJoystick, 15);
//    teeterTotterModeButton = new JoystickButton(leftJoystick, 14);

    //buttonW = new JoystickButton(customController, 1);
    //buttonA = new JoystickButton(customController, 2);
    //buttonS = new JoystickButton(customController, 3);
    //buttonD = new JoystickButton(customController, 4);
    //buttonTurbo = new JoystickButton(customController, 5);

//    navX = new AHRS(SerialPort.Port.kMXP);

    // Distance in inches
    leftDriveEncoder = new Encoder(0, 1);
    leftDriveEncoder.setDistancePerPulse(-kDriveTrainWheelDiameter*Math.PI/kDriveTrainEncoderPulsesPerRotation);
    rightDriveEncoder = new Encoder(2, 3);
    rightDriveEncoder.setDistancePerPulse(kDriveTrainWheelDiameter*Math.PI/kDriveTrainEncoderPulsesPerRotation);
    // Rotation in degrees
    // The Shoulder motor controller (TalonFX on the Falcon 500) has a built-in encoder accessed via the motor object
    elbowEncoder1 = new Encoder(4, 5);
    elbowEncoder1.setDistancePerPulse(-360.0/(kGearMotorEncoderPulsesPerRotation*kGearMotorRatio));
    elbowEncoder2 = new Encoder(6, 7);
    elbowEncoder2.setDistancePerPulse(-360.0/(kGearMotorEncoderPulsesPerRotation*kGearMotorRatio));
    // Hand width in inches
    handEncoder = new Encoder(8, 9);
    handEncoder.setReverseDirection(true);
    handEncoder.setDistancePerPulse(11.0/1830.0); // -1830 ticks from close to open (11 inches of travel)


  }

  /**
   * This function is called every 20 ms, no matter the mode. Use this for items like diagnostics
   * that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {}

  /**
   * This autonomous (along with the chooser code above) shows how to select between different
   * autonomous modes using the dashboard. The sendable chooser code works with the Java
   * SmartDashboard. If you prefer the LabVIEW Dashboard, remove all of the chooser code and
   * uncomment the getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to the switch structure
   * below with additional strings. If using the SendableChooser make sure to add them to the
   * chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
//    m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);

    //autoPhase = 1;
    autoPhase = 4; // Skipping Shoulder, Elbow, Hand due to encoder/motor issues
  }

  public int compare(double low, double test, double high) {
    // Compares two values, returning:
    //     -1 if less than the low threshold
    //      0 if between the low and high thresholds
    //      1 if above the high threshold
    if(test < low) {
      return -1;
    } else if(test > high) {
      return 1;
    } else {
      return 0;
    }
  }

  @Override
  public void autonomousPeriodic() {
    //System.out.println("m_autoSelected = " + m_autoSelected + "    kCustomAuto = " + kCustomAuto);
    System.out.println("autoPhase = " + autoPhase);
    switch (m_autoSelected) {
      case kCustomAuto:
        // Autonomous Option "My Auto"
        // Performs four separate steps sequentially:
        //    Phase 1 - raise shoulder for high post
        //    Phase 2 - extend elbow for high post
        //    Phase 3 - hand open to drop cone
        //    Phase 4 - drive backwards to exit community
        //    Phase 5 - rotate 180 degrees to position for pick-up
        //    Phase 6 - retract elbow to position for pick-up
        //    Phase 7 - lower shoulder to position for pick-up
        
        if (autoPhase == 1) {
          double speed = 0.3 * compare(59.0, shoulderAngle(),61.0);
          // Raise Shoulder
          shoulderMotor.set(ControlMode.PercentOutput, speed); // TalonFX on Falcon 500 has an extra parameter for Control Mode
          if(speed == 0.0) {
            autoPhase++;
          }
        } else if(autoPhase == 2) {
          // Extend Elbow
          double speed = 0.3 * compare(179.0, maxElbowEncoder(),181.0);
          elbowMotor.set(speed);
          if(speed == 0.0) {
            autoPhase++;
          }
        } else if(autoPhase == 3) {
          // Open Hand
          double speed = 0.3 * compare(0.9, handEncoder.getDistance(),1.0);
          handMotor.set(speed);
          if(speed == 0.0) {
            autoPhase++;
          }
        } else if(autoPhase == 4) {
          // Drive backwards 17 feet
          double speed = 0.6 * compare(-18.0*12.0, avgDriveEncoder(), -17.0*12.0);
          //System.out.println(speed);
          driveTrain.arcadeDrive(speed, 0.23);
          if(speed == 0.0) {
            //autoPhase++;
            autoPhase = 11;
//            autoPhase=99; // Skip rotating and positioning shoulder and elbow for driver to pickup field element
//            targetHeading = navX.getCompassHeading()+180.0; // Can't get heading without navX or other Mag or Gyro
          }
        } else if(autoPhase == 5) {
          // Rotate 180 degrees
          // Can't work without the navX or other decent Gyro or Mag heading
/*          if (navX.getCompassHeading()<targetHeading) {
            driveTrain.arcadeDrive(0.0, 0.25);
          } else{
            driveTrain.arcadeDrive(0.0, 0.0);
            autoPhase++;
          }
*/
        } else if(autoPhase == 6) {
          // Retract Elbow
          double speed = 0.3 * compare(119.0, maxElbowEncoder(),121.0);
          elbowMotor.set(speed);
          if(speed == 0.0) {
            autoPhase++;
          }
        } else if(autoPhase == 7) {
          // Lower Shoulder
          double speed = 0.3 * compare(0.0, shoulderAngle(),2.0);
          shoulderMotor.set(ControlMode.PercentOutput, speed);
          if(speed == 0.0) {
            //autoPhase++;
            autoPhase=99; // Skip any following phases
          }
        } else if(autoPhase == 8) {
          // Retract Elbow
          double speed = -0.25 * compare(32.0, maxElbowEncoder(),34.0);
          elbowMotor.set(speed);
          if(speed == 0.0) {
            autoPhase++;
          }
        } else if(autoPhase == 9) {
         double speed = -0.1 * compare(10.0,handEncoder.getDistance(),12.0);
         elbowMotor.set(speed);
         if(speed == 0.0)
         autoPhase++;

        } else if(autoPhase == 10) {
          double speed = -0.1 * compare(159.0, maxElbowEncoder(),161.0);
          elbowMotor.set(speed);
          if(speed == 0.0)
          autoPhase++;

        } else if(autoPhase == 11) {
          double speed = 0.6 * compare(-1.0*12.0, avgDriveEncoder(), 0.0*12.0);
          driveTrain.arcadeDrive(speed, -0.20);
          if(speed == 0.0) {
            autoPhase++;
          }
          // Lower elbow and open hand, then bring elbow back to resting point
          // elbow Needs to extend 45 degrees from resting assuming resting is 0. Try 33 , holding cube 14 inch , drop cube 16 & 3 3/4
          // Hand:-1830 ticks from close to open (11 inches of travel)
          // 160 degrees for elbow to be at resting  
        }
        break;
      case kDefaultAuto:
      default:
        // Put default auto code here
        // For safety, do nothing if an Autonomous program wasn't explicitly selected
        break;
    }

    SmartDashboard.putNumber("Shoulder", shoulderAngle());
    SmartDashboard.putNumber("Elbow", elbowEncoder1.getDistance());
    SmartDashboard.putNumber("Hand", handEncoder.getDistance());
    SmartDashboard.putNumber("Left Distance", leftDriveEncoder.getDistance());
    SmartDashboard.putNumber("Right Distance" , rightDriveEncoder.getDistance());

    //Shuffleboard.update();
    SmartDashboard.updateValues();
  }

  /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {
    assistMode = 0;
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
    // Manual operation drive code

    // Tank drive
    //double leftSpeed = leftJoystick.getY();
    //double rightSpeed = rightJoystick.getY();
    //drive.tankDrive(leftSpeed, rightSpeed);
    
    // WASD drive on custom controller
    //double speed = 0.0;
    //if(buttonW.getAsBoolean() && !buttonA.getAsBoolean()) {
      //speed = 0.5;
    //} else if(buttonA.getAsBoolean() && !buttonW.getAsBoolean()) {
      //speed = -0.5;
    //}
    //double rotation = 0.0;
    //if(buttonS.getAsBoolean() && !buttonD.getAsBoolean()) {
      //rotation = 0.5;
    //} else if(buttonD.getAsBoolean() && !buttonS.getAsBoolean()) {
      //rotation = -0.5;
    //}
    //if(buttonTurbo.getAsBoolean()) {
      //speed *= 2.0;
      //rotation *= 2.0;
    //}

    // Arcade Drive 
    double speed = rightJoystick.getY();
    double rotation = rightJoystick.getX();

    // Balance on Charging Platform
    // Won't work without the navX or other good pitch-measuring Gyro
/*    if (teeterTotterModeButton.getAsBoolean()) {
      rotation = 0.0;
      double pitch = navX.getRoll(); // navX-MXP mounted sideways, so swap pitch and roll
      if (pitch > kPitchLevel+2.5) {
        speed = 0.25;
      } else if (pitch < kPitchLevel-2.5) {
        speed = -0.25;
      } else {
        speed = 0.0;
      }
    }
*/
    driveTrain.arcadeDrive(speed, rotation);

    // Driver assist requests
    if (assistRestingButton.getAsBoolean()) {
      assistMode = 4;
    }
    if(assistLevel1Button.getAsBoolean()) {
      assistMode = 1;
    }
    if(assistLevel2Button.getAsBoolean()) {
      assistMode = 2;
    }
    if(assistLevel3Button.getAsBoolean()) {
      assistMode = 3;
    }

    assistMode = 0; // Cancel out the requested assist until Encoders and motors are behaving properly

    // Move Shoulder
    if(shoulderUpButton.getAsBoolean() && !shoulderDownButton.getAsBoolean()) {
      shoulderMotor.set(ControlMode.PercentOutput, 0.2);
      assistMode = 0;
    } else if(shoulderDownButton.getAsBoolean() && !shoulderUpButton.getAsBoolean()) {
      shoulderMotor.set(ControlMode.PercentOutput, -0.1);
      assistMode = 0;
    } else if(assistMode==0) {
      shoulderMotor.set(ControlMode.PercentOutput, 0.0);
    }

    // Move Elbow
    if(elbowExtendButton.getAsBoolean() && !elbowRetractButton.getAsBoolean()) {
      elbowMotor.set(0.4);
      assistMode = 0;
    } else if(elbowRetractButton.getAsBoolean() && !elbowExtendButton.getAsBoolean()) {
      elbowMotor.set(-0.6);
      assistMode = 0;
    } else if(assistMode==0) {
      elbowMotor.set(0.0);
    } 

    // Move Hand
    if(handOpenButton.getAsBoolean() && !handCloseButton.getAsBoolean()) {
      handMotor.set(0.9);
    } else if(handCloseButton.getAsBoolean() && !handOpenButton.getAsBoolean()) {
      handMotor.set(-0.9);
    } else {
      handMotor.set(0.0);
    }

    // Perform driver assists
    if(assistMode==1) {
      double shoulderSpeed = 0.35 * -compare(0.0, shoulderAngle(), 5.0);
      shoulderMotor.set(ControlMode.PercentOutput, shoulderSpeed);
      double elbowSpeed = 0.35 * -compare(115.0, maxElbowEncoder(), 125.0);
      elbowMotor.set(elbowSpeed);
    }
    if(assistMode==2) {
      double shoulderSpeed = 0.35 * -compare(25.0, shoulderAngle(), 35.0);
      shoulderMotor.set(ControlMode.PercentOutput, shoulderSpeed);
      double elbowSpeed = 0.35 * -compare(145.0, maxElbowEncoder(), 155.0);
      elbowMotor.set(elbowSpeed);
    }
    if(assistMode==3) {
      double shoulderSpeed = 0.35 * -compare(55.0, shoulderAngle(), 65.0);
      shoulderMotor.set(ControlMode.PercentOutput, shoulderSpeed);
      double elbowSpeed = 0.35 * -compare(175.0, maxElbowEncoder(), 185.0);
      elbowMotor.set(elbowSpeed);
    }
    if(assistMode==4) {
      double shoulderSpeed = 0.35 * -compare(0.0, shoulderAngle(), 5.0);
      shoulderMotor.set(ControlMode.PercentOutput, shoulderSpeed);
      double elbowSpeed = 0.35 * -compare(0.0, maxElbowEncoder(), 5.0);
      elbowMotor.set(elbowSpeed);
    }

    // Diagnostic data
    //dashboardLeftDistance.setDouble(leftDriveEncoder.getDistance());
    //dashboardRightDistance.setDouble(rightDriveEncoder.getDistance());
    //dashboardShoulderAngle.setDouble(shoulderAngle());
    //dashboardElbow1Angle.setDouble(elbowEncoder1.getDistance());
    //dashboardElbow2Angle.setDouble(elbowEncoder2.getDistance());
    //dashboardHandPosition.setDouble(handEncoder.getDistance() + 8.0);
//    dashboardPitch.setDouble(navX.getRoll());
//    dashboardRoll.setDouble(navX.getPitch());
//    dashboardYaw.setDouble(navX.getYaw());

    SmartDashboard.putNumber("Shoulder", shoulderAngle());
    SmartDashboard.putNumber("Elbow", elbowEncoder1.getDistance());
    SmartDashboard.putNumber("Hand", handEncoder.getDistance());
    SmartDashboard.putNumber("Left Distance", leftDriveEncoder.getDistance());
    SmartDashboard.putNumber("Right Distance" , rightDriveEncoder.getDistance());
    SmartDashboard.putNumber("Left Stick X", leftJoystick.getX());
    SmartDashboard.putNumber("Left Stick Y", leftJoystick.getY());
    SmartDashboard.putNumber("Right Stick X", rightJoystick.getX());
    SmartDashboard.putNumber("Right Stick Y", rightJoystick.getY());

    //Shuffleboard.update();
    SmartDashboard.updateValues();
  }

  /** This function is called once when the robot is disabled. */
  @Override
  public void disabledInit() {}

  /** This function is called periodically when disabled. */
  @Override
  public void disabledPeriodic() {}

  /** This function is called once when test mode is enabled. */
  @Override
  public void testInit() {}

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}

  /** This function is called once when the robot is first started up. */
  @Override
  public void simulationInit() {}

  /** This function is called periodically whilst in simulation. */
  @Override
  public void simulationPeriodic() {}
}
