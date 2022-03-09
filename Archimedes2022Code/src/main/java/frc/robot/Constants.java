// RobotBuilder Version: 4.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants.  This class should not be used for any other purpose.  All constants should be
 * declared globally (i.e. public static).  Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public class Constants {

    public static final class RampConstants {
      // speed under 0.5 will cause ramp not to work (will stutter)
      public static final double kRampSpeedUp = 1.0;
      public static final double kRampSpeedDown = -1.0;
      public static final double kRampSpeedStop = 0.0;
    } 
    public static final class LauncherConstants {
      public static final double kLauncherSpeedUp = 1.0;
      public static final double kLauncherSpeedDown = -1.0;
      public static final double kLauncherSpeedStop = 0.0;
      
    }
    public static final class IntakeConstants {
      public static final double kIntakeSpeedUp = 0.90;
      public static final double kIntakeSpeedDown = -0.70;
      public static final double kIntakeSpeedStop = 0.0;
      
    }
     public static final class DriveConstants {
      //public static final double kEncoderTick2Feet = 1.0 / 2048.0 * 0.5 * Math.PI;
      public static final double kEncoderDistancePerPulse = 0.5 * Math.PI / 2048;
      public static final double kAutoForwardSpeed = 0.4;
      public static final double kAutoForwardDistance = 7.5;
       /*
       public static final int kLeftMotor1Port = 0;
       public static final int kLeftMotor2Port = 1;
       public static final int kRightMotor1Port = 2;
       public static final int kRightMotor2Port = 3;
       */ 
     }
     public static final class ElevatorConstants {
      public static final double kElevatorUp = 1;
      public static final double kElevatorDown = -1;

     }
     public static final class PIDConstants {
      public static final double kP = 0.03;
      public static final double kI = 0.0;
      public static final double kD = 0.0;
      public static final double kToleranceDegrees = 2.0f;


     }
}

