package frc.robot.subsystems;

import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Elevator extends SubsystemBase {
private Spark elevatorMotorR;
private Spark elevatorMotorL;

    public Elevator(){
        elevatorMotorR = new Spark(6);
        elevatorMotorL = new Spark(7);


        
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run

    }

    @Override
    public void simulationPeriodic() {
        // This method will be called once per scheduler run when in simulation

    }
    public void left(double speed) {
        elevatorMotorR.set(speed*0.55); // 1 hits antifloor, 0.8 just barely misses ceiling, 0.45\ just beyond white box
        elevatorMotorL.set(speed*0.65);
        
    }
    public void stop(){
        elevatorMotorR.set(0.0);
        elevatorMotorL.set(0.0);
        
    }
}