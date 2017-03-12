package org.usfirst.frc.team3673.AlexiaW2018.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.cscore.AxisCamera;
import edu.wpi.first.wpilibj.CameraServer;

/**
 *
 */
public class GearCamera extends Command {
		AxisCamera gearCamera;
	
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	
    public void gearCamera() {
    		gearCamera = CameraServer.getInstance().addAxisCamera("gearCamera", "10.36.73.47");
    		CameraServer.getInstance().getVideo(gearCamera);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
