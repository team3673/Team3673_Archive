package frc.robot.subsystems;

import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase {
private Spark intakeMotor;

    public Intake(){
        intakeMotor = new Spark(5); // 5 and 6 is what these were before my dum dum changes
       


        
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run

    }

    @Override
    public void simulationPeriodic() {
        // This method will be called once per scheduler run when in simulation

    }
    public void spin(double speed) {
        intakeMotor.set(speed); 
        
    }
    public void stop(){
        intakeMotor.set(0.0);
        
        
    }
}