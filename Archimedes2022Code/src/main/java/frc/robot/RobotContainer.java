// RobotBuilder Version: 4.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.

// ROBOTBUILDER TYPE: RobotContainer.

package frc.robot;

import frc.robot.Constants.DriveConstants;
import frc.robot.Constants.RampConstants;
import frc.robot.commands.*;
import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj.Joystick;
import frc.robot.subsystems.*;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS


/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {

  //private static RobotContainer m_robotContainer = RobotContainer.getInstance();
  private RobotContainer m_robotContainer = this;

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
// The robot's subsystems
  public final DriveTrain m_driveTrain = new DriveTrain();
  public final Ramp m_ramp = new Ramp();
  public final Launcher m_launcher = new Launcher();
  public final Intake m_Intake = new Intake();
  public final Elevator m_Elevator = new Elevator();
  private int autoMode = 0;
  private SendableChooser<Integer>autoCommand;
// Joysticks

  private final Joystick leftStick = new Joystick(0);
  private final Joystick rightStick = new Joystick(1);

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

  
  // A chooser for autonomous commands
 // SendableChooser<Command> m_chooser = new SendableChooser<>();

  /**
  * The container for the robot.  Contains subsystems, OI devices, and commands.
  */
  public RobotContainer() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=SMARTDASHBOARD
    // Smartdashboard Subsystems

    System.out.println(leftStick.getName());
    System.out.println(rightStick.getName());

    // SmartDashboard Buttons
    autoCommand = new SendableChooser<>();
    autoCommand.setDefaultOption("Shoot low and Move", 1);
    autoCommand.addOption("Move out of tarmac", 2);
    autoCommand.addOption("No auto", 3);

    SmartDashboard.putData("Auto Choices", autoCommand);

   // SmartDashboard.putData("Autonomous Command", new AutonomousCommand());
    /*SmartDashboard.putData("Tankdrive", new Tankdrive( m_driveTrain,
    () -> leftStick.getY(),
     () -> rightStick.getY()));
    
*/
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=SMARTDASHBOARD
    // Configure the button bindings
    configureButtonBindings();

    // Configure default commands
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=SUBSYSTEM_DEFAULT_COMMAND
    m_driveTrain.setDefaultCommand(new Tankdrive( m_driveTrain,
     () -> leftStick.getY(),
     () -> rightStick.getY(),
     this) ) ;


    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=SUBSYSTEM_DEFAULT_COMMAND

    // Configure autonomous sendable chooser
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=AUTONOMOUS

  //  m_chooser.setDefaultOption("Autonomous Command", new AutonomousCommand());

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=AUTONOMOUS
    //SmartDashboard.putNumber("Ultrasonic", m_launcher.getUltrasonic());
    //SmartDashboard.putData("Auto Mode", m_chooser);
    SmartDashboard.putNumber("Right Encoder", m_driveTrain.getRightEncoder());
    SmartDashboard.putNumber("Left Encoder", m_driveTrain.getLeftEncoder());
  }

  public RobotContainer getInstance() {
    return m_robotContainer;
  
  }

  /**
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=BUTTONS
// Create some buttons


    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=BUTTONS
    new JoystickButton(rightStick, 3 )
    .whileActiveOnce(new rampCommand(m_ramp, Constants.RampConstants.kRampSpeedUp));
    new JoystickButton(rightStick, 4 )
    .whileActiveOnce(new rampCommand(m_ramp, Constants.RampConstants.kRampSpeedDown));
    new JoystickButton(rightStick, 2 )
    .whileActiveOnce(new rampCommand(m_ramp, Constants.RampConstants.kRampSpeedStop));

 
    //Launcher Buttons
    new JoystickButton(leftStick, 4 )
    .whenActive(new LauncherCmd(m_launcher, Constants.LauncherConstants.kLauncherSpeedUpSlow));
    new JoystickButton(leftStick, 3 )
    .whenActive(new LauncherCmd(m_launcher, Constants.LauncherConstants.kLauncherSpeedUp));
    new JoystickButton(leftStick, 2 )
    .whenActive(new LauncherCmd(m_launcher, Constants.LauncherConstants.kLauncherSpeedStop));
    new JoystickButton(leftStick, 7 )
    .whenActive(new LauncherCmd(m_launcher, Constants.LauncherConstants.kLauncherSpeedDown));
    //Intake (Same buttons as launcher)
    
    new JoystickButton(rightStick, 1 )
    .whileActiveOnce(new IntakeCmd(m_Intake, Constants.IntakeConstants.kIntakeSpeedUp));
    new JoystickButton(leftStick, 1 )
    .whileActiveOnce(new IntakeCmd(m_Intake, Constants.IntakeConstants.kIntakeSpeedDown));
    new JoystickButton(leftStick, 2 )
    .whileActiveOnce(new IntakeCmd(m_Intake, Constants.IntakeConstants.kIntakeSpeedStop));

    //Elevator Buttons
    
    new JoystickButton(rightStick, 7)
    .whenHeld(new ElevatorCmd(m_Elevator, Constants.ElevatorConstants.kElevatorUp));
    new JoystickButton(rightStick, 8)
    .whenHeld(new ElevatorCmd(m_Elevator, Constants.ElevatorConstants.kElevatorDown));
/*  
    new JoystickButton(rightStick, 13 )
    .whenActive(new Tankdrive(m_driveTrain,   () -> leftStick.getY(),
    () -> rightStick.getY()) );
    new JoystickButton(rightStick, 14 )
    .whenActive(new Tankdrive(m_driveTrain,
    //() -> leftStick.getY(),   () -> rightStick.getY(), true) );
    () -> -rightStick.getY(),   () -> -leftStick.getY()) );
*/
    
  }

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=FUNCTIONS
public Joystick getRightStick() {
        return rightStick;
    }

public Joystick getLeftStick() {
        return leftStick;
    }



    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=FUNCTIONS

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
  */
  public Command getAutonomousCommand() {
    
    // The selected command will be run in autonomous
    //return m_chooser.getSelected();
    autoMode = autoCommand.getSelected();
    System.out.println("autoMode = " + autoMode);
    switch(autoMode) {

      case 1 :
      System.out.println("Auto: Shoot low and drive");
    return new SequentialCommandGroup( //
      new ParallelCommandGroup(
        new AutoLauncherCmd(m_launcher, Constants.LauncherConstants.kLauncherSpeedUpSlow),
        new AutoRampCmd(m_ramp, RampConstants.kRampSpeedUp)
        ),
      new DriveForwardCmd(m_driveTrain, DriveConstants.kAutoForwardDistance));
      

      case 2 :
      System.out.println("Auto: Drive 3.5 feet");
       
      new DriveForwardCmd(m_driveTrain, 3.5)
      ;
        
      

    
      case 3 :
      System.out.println("Auto: nothing");
       return (
         new DriveForwardCmd(m_driveTrain, 0.0)
       );

    }
    return (
      new DriveForwardCmd(m_driveTrain, 0.0));
    
    


  }
  

}

