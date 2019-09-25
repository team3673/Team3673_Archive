/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.Robot;
import frc.robot.RobotMap;

/**
 * An example command.  You can replace me with your own command.
 */
public class ArmGrabToggle extends Command {
  public ArmGrabToggle() {
    // Use requires() here to declare subsystem dependencies
    requires(Robot.m_subsystem);
    
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    if(OI.xcontRightTrigger.get() && !RobotMap.boolSolToggle && !RobotMap.boolIsExtended){
        // Turn on pneumatics
       RobotMap.doubleSolLeft.set(Value.kForward);
       RobotMap.doubleSolRight.set(Value.kForward);
       RobotMap.boolIsExtended = true;
       isFinished();
    }
    else if(OI.xcontRightTrigger.get() && !RobotMap.boolSolToggle && RobotMap.boolIsExtended){
        //Set solenoids to reverse
        RobotMap.doubleSolLeft.set(Value.kReverse);
        RobotMap.doubleSolRight.set(Value.kReverse);
        RobotMap.boolIsExtended = false;
        isFinished();
    }
    else{
        RobotMap.doubleSolLeft.set(Value.kOff);
        RobotMap.doubleSolRight.set(Value.kOff);
        isFinished();
    }
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
   
      
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
     
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
