// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class SwerveDriveTrain extends SubsystemBase {
  /** Creates a new SwerveDriveTrain. */
  AHRS gyro;
  SwerveModulePosition[] modulePositions = {
    new SwerveModulePosition(),
    new SwerveModulePosition(),
    new SwerveModulePosition(),
    new SwerveModulePosition()
  };
  SwerveModuleState[] states = {
    new SwerveModuleState(),
    new SwerveModuleState(),
    new SwerveModuleState(),
    new SwerveModuleState()
  };
  SwerveDriveKinematics kinematics;
  SwerveDriveOdometry odometry;
  ChassisSpeeds speeds;
  double maxSpeed = 3.0;
  
  public SwerveDriveTrain() {
    double wheelBase = 23.75; // length between where the weels touch the ground
    double trackWidth = 23.75; // the distance between the weels with wise
    Translation2d locationFL = new Translation2d(wheelBase / 2, trackWidth / 2);
    Translation2d locationFR = new Translation2d(wheelBase / 2, -trackWidth / 2);
    Translation2d locationBL = new Translation2d(-wheelBase / 2, trackWidth / 2);
    Translation2d locationBR = new Translation2d(-wheelBase / 2, -trackWidth / 2);

    gyro = new AHRS(SPI.Port.kMXP);
    kinematics = new SwerveDriveKinematics(locationFL, locationFR, locationBL, locationBR);
    odometry = new SwerveDriveOdometry(kinematics, new Rotation2d(gyro.getCompassHeading()), modulePositions);
  // tells the robot to move a diretion based on witch way you move the joystick
  // forward and strafe should be maximum desierd speed in M/s multiplied by joystick value
  // rotaion is in radians per second
   speeds = ChassisSpeeds.fromFieldRelativeSpeeds(forward, strafe, rotation, Rotation2d.fromDegrees(getHeading()));
   SwerveDriveKinematics.normalizeWheelSpeeds(states, maxSpeed);
   moduleFL.move(states[0].speedMetersPerSecond / maxSpeed, states[0].angle.getRadians() / (2 * Math.PI));
   moduleFR.move(states[1].speedMetersPerSecond / maxSpeed, states[1].angle.getRadians() / (2 * Math.PI));
   moduleBL.move(states[2].speedMetersPerSecond / maxSpeed, states[2].angle.getRadians() / (2 * Math.PI));
   moduleBR.move(states[3].speedMetersPerSecond / maxSpeed, states[3].angle.getRadians() / (2 * Math.PI));
  }

  public double getHeading() {
    return gyro.getAngle();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
