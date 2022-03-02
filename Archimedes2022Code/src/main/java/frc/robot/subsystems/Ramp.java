package frc.robot.subsystems;

import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Ramp extends SubsystemBase {
private Spark rampMotor;

    public Ramp(){
        rampMotor = new Spark(4);


        
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run

    }

    @Override
    public void simulationPeriodic() {
        // This method will be called once per scheduler run when in simulation

    }
    public void convey(double speed) {
        rampMotor.set(speed);
        
    }
    public void stop(){
        rampMotor.set(0.0);
        
    }
}