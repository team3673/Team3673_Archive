// RobotBuilder Version: 4.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.

// ROBOTBUILDER TYPE: Command.

package frc.robot.commands;
import frc.robot.Constants.DriveConstants;
import frc.robot.subsystems.DriveTrain;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class DriveForwardCmd extends CommandBase {
    private final DriveTrain driveTrain;
    private final double distance;
    
    

    public DriveForwardCmd(DriveTrain driveTrain, double distance) {
        this.driveTrain = driveTrain;
        this.distance = driveTrain.getEncoderAverage() + distance;
        addRequirements(driveTrain);
        
    }

    @Override
    public void initialize() {
        System.out.println("DriveForwardCmd started!");
    }

    @Override
    public void execute() {
       System.out.println(driveTrain.getLeftEncoder()+ "\t\t"+driveTrain.getRightEncoder() + "\t\t" + driveTrain.getEncoderAverage() );
    

        driveTrain.setMotors(DriveConstants.kAutoForwardSpeed, DriveConstants.kAutoForwardSpeed);
    }

    @Override
    public void end(boolean interrupted) {
        driveTrain.setMotors(0, 0);
        System.out.println("DriveForwardCmd ended!");
    }

    @Override
    public boolean isFinished() {
        if (driveTrain.getEncoderAverage() > distance)
            return true;
        else
            return false;
    }
}