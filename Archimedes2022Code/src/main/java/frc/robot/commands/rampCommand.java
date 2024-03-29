package frc.robot.commands;
import java.util.function.Supplier;


import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Ramp;

public class rampCommand extends CommandBase {
        private final Ramp m_Ramp;
        private final Double rampSpeed;


    public rampCommand(Ramp subsystem, Double rampSpeed){
        this.rampSpeed = rampSpeed;

        

        m_Ramp = subsystem;
        addRequirements(m_Ramp);
    }



    @Override
    public void initialize(){
        System.out.println("Ramp starting");
    }
    @Override
    public void execute(){

        System.out.println(rampSpeed);

        m_Ramp.convey(rampSpeed);
        
    }
     // Called once the command ends or is interrupted.
     @Override
     public void end(boolean interrupted) {
         System.out.println("You've reached the end of the ramp!");
         m_Ramp.convey(0.0);
 
     }
 
     // Returns true when the command should end.
     @Override
     public boolean isFinished() {
         return false;
     }
 
     @Override
     public boolean runsWhenDisabled() {
         // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DISABLED
         return false;
 
     // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DISABLED
     }
 
 

}
