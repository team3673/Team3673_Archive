package frc.robot.commands;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class ArmGrabToggle extends Command {
    public ArmGrabToggle(){
     requires(Robot.m_subsystem);
    }
    @Override
    protected void initialize(){
        if(OI.joyleftbtngrab.get() && !RobotMap.boolSolToggle && !RobotMap.boolIsExtended){
            RobotMap.doubleSolLeft.set(Value.kForward);
            RobotMap.doubleSolRight.set(Value.kForward);
            RobotMap.boolIsExtended = true;
            isFinished();

      
        }
        else if (OI.joyleftbtngrab.get() && !RobotMap.boolSolToggle && RobotMap.boolIsExtended){
            RobotMap.doubleSolLeft.set(Value.kReverse);
            RobotMap.doubleSolRight.set(Value.kReverse);
            RobotMap.boolIsExtended = false;
            isFinished();


        }
        else {
            RobotMap.doubleSolLeft.set(Value.kOff);
            RobotMap.doubleSolRight.set(Value.kOff);
            isFinished();
        }
    }
@Override
protected void execute(){

}
@Override
protected boolean isFinished(){
    return false;
}
@Override
protected void end(){

}
@Override
protected void interrupted(){

}
}