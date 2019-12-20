/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
//test
package frc.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.CameraServer;


/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
  // For example to map the left and right motors, you could define the
  // following variables to use with your drivetrain subsystem.
  // public static int leftMotor = 1;
  // public static int rightMotor = 2;

  // If you are using multiple modules, make sure to define both the port
  // number and the module. For example you with a rangefinder:
  // public static int rangefinderPort = 1;
  // public static int rangefinderModule = 1;
  public static Spark  mtrLeftFront = new Spark(3);
  public static Spark mtrLeftBack = new Spark(1);
  public static Spark mtrRightFront = new Spark(2);
  public static Spark mtrRightBack = new Spark(0);

  public static SpeedControllerGroup mtrLeft = new SpeedControllerGroup(mtrLeftFront, mtrLeftBack);
  public static SpeedControllerGroup mtrRight = new SpeedControllerGroup(mtrRightFront, mtrRightBack);

  public static DifferentialDrive robotDriveObject = new DifferentialDrive(mtrRight, mtrLeft);

  public static int doubleSolLeftFirstValve = 2;
  public static int doubleSolLeftSecondValve = 3;
  public static int doubleSolRightFirstValve = 0;
  public static int doubleSolRightSecondValve = 1;

  public static DoubleSolenoid doubleSolRight = new DoubleSolenoid(doubleSolRightFirstValve, doubleSolRightSecondValve);
  public static DoubleSolenoid doubleSolLeft = new DoubleSolenoid(doubleSolLeftFirstValve, doubleSolLeftSecondValve);

  public static boolean boolSolToggle;
  public static boolean boolIsExtended;
  public static Compressor compressor = new Compressor(0);
  public static boolean pressureSwitch = compressor.getPressureSwitchValue();
  public static double compressorCurrent = compressor.getCompressorCurrent();

}
