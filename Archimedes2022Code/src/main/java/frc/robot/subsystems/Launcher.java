package frc.robot.subsystems;

import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Launcher extends SubsystemBase {
private Spark launcherMotorR;
private Spark launcherMotorL;

    public Launcher(){
        launcherMotorR = new Spark(5);
        launcherMotorL = new Spark(6);


        
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
        launcherMotorR.set(speed*0.55); // 1 hits antifloor, 0.8 just barely misses ceiling, 0.45\ just beyond white box
        launcherMotorL.set(-speed*0.55);
        
    }
    public void stop(){
        launcherMotorR.set(0.0);
        launcherMotorL.set(0.0);
        
    }
}