// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

// Install navX library from https://dev.studica.com/releases/2023/NavX.json
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
//import edu.wpi.first.wpilibj.AnalogGyro;

/** Represents a swerve drive style drivetrain. */
public class Drivetrain {
  public static final double kMaxSpeed = 3.0; // 3 meters per second
  public static final double kMaxAngularSpeed = Math.PI; // 1/2 rotation per second

//  private final Translation2d m_frontLeftLocation = new Translation2d(0.381, 0.381); // 15 in offset
//  private final Translation2d m_frontRightLocation = new Translation2d(0.381, -0.381);
//  private final Translation2d m_backLeftLocation = new Translation2d(-0.381, 0.381);
//  private final Translation2d m_backRightLocation = new Translation2d(-0.381, -0.381);
//  private final Translation2d m_frontLeftLocation = new Translation2d(0.3048, 0.3048); // 12 in offset
//  private final Translation2d m_frontRightLocation = new Translation2d(0.3048, -0.3048);
//  private final Translation2d m_backLeftLocation = new Translation2d(-0.3048, 0.3048);
//  private final Translation2d m_backRightLocation = new Translation2d(-0.3048, -0.3048);
  private final Translation2d m_frontRightLocation = new Translation2d(0.2691, 0.2691); // 21-3/16 in track width and wheelbase
  private final Translation2d m_frontLeftLocation = new Translation2d(-0.2691, 0.2691); // divide by two to get the offset in inches
  private final Translation2d m_backLeftLocation = new Translation2d(-0.2691, -0.2691);   // and multiply by 25.4 to get millimeters
  private final Translation2d m_backRightLocation = new Translation2d(0.2691, -0.2691);    // then divide by 1000 to get meters

  private final SwerveModule m_frontRight = new SwerveModule(1, 2, 9);
  private final SwerveModule m_frontLeft = new SwerveModule(3, 4, 10);
  private final SwerveModule m_backLeft = new SwerveModule(5, 6, 11);
  private final SwerveModule m_backRight = new SwerveModule(7, 8, 12);

//  private final AnalogGyro m_gyro = new AnalogGyro(0);
  private final AHRS m_gyro = new AHRS();

  private final SwerveDriveKinematics m_kinematics =
      new SwerveDriveKinematics(
          m_frontLeftLocation, m_frontRightLocation, m_backLeftLocation, m_backRightLocation);

  private final SwerveDriveOdometry m_odometry =
      new SwerveDriveOdometry(
          m_kinematics,
          m_gyro.getRotation2d(),
          new SwerveModulePosition[] {
            m_frontLeft.getPosition(),
            m_frontRight.getPosition(),
            m_backLeft.getPosition(),
            m_backRight.getPosition()
          });

  public SwerveModule getFrontLeft() {
    return m_frontLeft;
  }
  
  public SwerveModule getFrontRight() {
    return m_frontRight;
  }
  
  public SwerveModule getBackRight() {
    return m_backRight;
  }
  
  public SwerveModule getBackLeft() {
    return m_backLeft;
  }
  
  public Drivetrain() {
    m_gyro.reset();
  }

  /**
   * Method to drive the robot using joystick info.
   *
   * @param xSpeed Speed of the robot in the x direction (forward).
   * @param ySpeed Speed of the robot in the y direction (sideways).
   * @param rot Angular rate of the robot.
   * @param fieldRelative Whether the provided x and y speeds are relative to the field.
   */
  public void drive(double xSpeed, double ySpeed, double rot, boolean fieldRelative) {
    var swerveModuleStates =
        m_kinematics.toSwerveModuleStates(
            fieldRelative
                ? ChassisSpeeds.fromFieldRelativeSpeeds(xSpeed, ySpeed, rot, m_gyro.getRotation2d())
                : new ChassisSpeeds(xSpeed, ySpeed, rot));
    SwerveDriveKinematics.desaturateWheelSpeeds(swerveModuleStates, kMaxSpeed);
    m_frontLeft.setDesiredState(swerveModuleStates[0]);
    m_frontRight.setDesiredState(swerveModuleStates[1]);
    m_backLeft.setDesiredState(swerveModuleStates[2]);
    m_backRight.setDesiredState(swerveModuleStates[3]);
  }

  /** Updates the field relative position of the robot. */
  public void updateOdometry() {
    m_odometry.update(
        m_gyro.getRotation2d(),
        new SwerveModulePosition[] {
          m_frontLeft.getPosition(),
          m_frontRight.getPosition(),
          m_backLeft.getPosition(),
          m_backRight.getPosition()
        });
  }

  public AHRS getGyro() {
    return m_gyro;
  }
}
