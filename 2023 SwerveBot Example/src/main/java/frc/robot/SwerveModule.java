// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;
//  Install ctre librarys here https://maven.ctr-electronics.com/release/com/ctre/phoenix/Phoenix5-frc2023-latest.json
import com.ctre.phoenix.sensors.CANCoder;
// Install the REV libraries from https://software-metadata.revrobotics.com/REVLib-2023.json
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxRelativeEncoder;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;

public class SwerveModule {
//  private static final double kWheelRadius = 0.0508;
  private static final double kWheelRadius = 0.0505;
  private static final int kEncoderResolution = 4096;

  private static final double kModuleMaxAngularVelocity = Drivetrain.kMaxAngularSpeed;
  private static final double kModuleMaxAngularAcceleration =
      2 * Math.PI; // radians per second squared

  private final MotorController m_driveMotor;
  private final MotorController m_turningMotor;

  private final RelativeEncoder m_driveEncoder;
//  private final Encoder m_turningEncoder;
  private final CANCoder m_turningEncoder;
  // Gains are for example purposes only - must be determined for your own robot!
  private final PIDController m_drivePIDController = new PIDController(1, 0, 0);

  // Gains are for example purposes only - must be determined for your own robot!
  private final ProfiledPIDController m_turningPIDController =
      new ProfiledPIDController(
          1,
          0,
          0,
          new TrapezoidProfile.Constraints(
              kModuleMaxAngularVelocity, kModuleMaxAngularAcceleration));

  // Gains are for example purposes only - must be determined for your own robot!
  private final SimpleMotorFeedforward m_driveFeedforward = new SimpleMotorFeedforward(1, 3);
  private final SimpleMotorFeedforward m_turnFeedforward = new SimpleMotorFeedforward(1, 0.5);

  /**
   * Constructs a SwerveModule with a drive motor, turning motor, drive encoder and turning encoder.
   *
   * @param driveMotorChannel CAN output for the drive motor.
   * @param turningMotorChannel CAN output for the turning motor.
   * @param driveEncoderChannelA DIO input for the drive encoder channel A
   * @param driveEncoderChannelB DIO input for the drive encoder channel B
   * @param turningEncoderChannelA DIO input for the turning encoder channel A
   * @param turningEncoderChannelB DIO input for the turning encoder channel B
   */
  public SwerveModule(
      int driveMotorChannel,
      int turningMotorChannel,
      // driveEncoder DIO channels ignored, using SparkMax built-in encoder
//      int driveEncoderChannelA,
//      int driveEncoderChannelB,
      // turningEncoder DIO channels ignored, using CANcoder instead
//      int turningEncoderChannelA,
//      int turningEncoderChannelB
        int turningCANCoderChannel) {
//    m_driveMotor = new PWMSparkMax(driveMotorChannel);
//    m_turningMotor = new PWMSparkMax(turningMotorChannel);
      m_driveMotor = new CANSparkMax(driveMotorChannel, CANSparkMaxLowLevel.MotorType.kBrushless);
      m_turningMotor = new CANSparkMax(turningMotorChannel, CANSparkMaxLowLevel.MotorType.kBrushless);

//    m_driveEncoder = new Encoder(driveEncoderChannelA, driveEncoderChannelB);
//    m_turningEncoder = new Encoder(turningEncoderChannelA, turningEncoderChannelB);
    m_driveEncoder = ((CANSparkMax) m_driveMotor).getEncoder(SparkMaxRelativeEncoder.Type.kHallSensor, 42);
    m_driveEncoder.setPositionConversionFactor(4.0 * Math.PI);
 //   m_turningEncoder = new Encoder(turningEncoderChannelA, turningEncoderChannelB);
    m_turningEncoder = new CANCoder(turningCANCoderChannel);

    // Set the distance per pulse for the drive encoder. We can simply use the
    // distance traveled for one rotation of the wheel divided by the encoder
    // resolution.
//    m_driveEncoder.setDistancePerPulse(2 * Math.PI * kWheelRadius / kEncoderResolution);

    // Set the distance (in this case, angle) in radians per pulse for the turning encoder.
    // This is the the angle through an entire rotation (2 * pi) divided by the
    // encoder resolution.
//    m_turningEncoder.setDistancePerPulse(2 * Math.PI / kEncoderResolution);

    // Limit the PID Controller's input range between -pi and pi and set the input
    // to be continuous.
    m_turningPIDController.enableContinuousInput(-Math.PI, Math.PI);
  }

  public MotorController getDriveMotor() {
    return m_driveMotor;
}

public MotorController getTurningMotor() {
    return m_turningMotor;
}

public RelativeEncoder getDriveEncoder() {
    return m_driveEncoder;
}

public CANCoder getTurningEncoder() {
    return m_turningEncoder;
}

  /**
   * Returns the current state of the module.
   *
   * @return The current state of the module.
   */
  public SwerveModuleState getState() {
    return new SwerveModuleState(
//        m_driveEncoder.getRate(), new Rotation2d(m_turningEncoder.getDistance()));
        m_driveEncoder.getVelocity(), new Rotation2d(m_turningEncoder.getPosition()));
}

  /**
   * Returns the current position of the module.
   *
   * @return The current position of the module.
   */
  public SwerveModulePosition getPosition() {
    return new SwerveModulePosition(
//        m_driveEncoder.getDistance(), new Rotation2d(m_turningEncoder.getDistance()));
        m_driveEncoder.getPosition(), new Rotation2d(m_turningEncoder.getPosition()));
  }

  /**
   * Sets the desired state for the module.
   *
   * @param desiredState Desired state with speed and angle.
   */
  public void setDesiredState(SwerveModuleState desiredState) {
    // Optimize the reference state to avoid spinning further than 90 degrees
    SwerveModuleState state =
//        SwerveModuleState.optimize(desiredState, new Rotation2d(m_turningEncoder.getDistance()));
        SwerveModuleState.optimize(desiredState, new Rotation2d(m_turningEncoder.getPosition()));

    // Calculate the drive output from the drive PID controller.
    final double driveOutput =
        m_drivePIDController.calculate(m_driveEncoder.getVelocity(), state.speedMetersPerSecond);

    final double driveFeedforward = m_driveFeedforward.calculate(state.speedMetersPerSecond);

    // Calculate the turning motor output from the turning PID controller.
    final double turnOutput =
//        m_turningPIDController.calculate(m_turningEncoder.getDistance(), state.angle.getRadians());
        m_turningPIDController.calculate(m_turningEncoder.getPosition(), state.angle.getRadians());

    final double turnFeedforward =
        m_turnFeedforward.calculate(m_turningPIDController.getSetpoint().velocity);

    m_driveMotor.setVoltage(driveOutput + driveFeedforward);
    m_turningMotor.setVoltage(turnOutput + turnFeedforward);
  }
}
