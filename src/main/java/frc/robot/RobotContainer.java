package frc.robot;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants.OIConstants;
import frc.robot.subsystems.SwerveDriveSubsystem;

public class RobotContainer {

  CommandXboxController driverController= new CommandXboxController(0); 
  CommandXboxController coDriverController= new CommandXboxController(1); 

  SendableChooser<Command> AutoChooser = new SendableChooser<>();

  //Subsystem
  SwerveDriveSubsystem swerveDriveSubsystem = new SwerveDriveSubsystem();

  //Commands

  //Triggers 
  Trigger yButton = coDriverController.y(); 
  Trigger xButton = coDriverController.x(); 


  public RobotContainer() {
    configureBindings();
     swerveDriveSubsystem.setDefaultCommand(
        new RunCommand(
            () -> swerveDriveSubsystem.drive(
                -MathUtil.applyDeadband(driverController.getLeftY(), OIConstants.kDriveDeadband),
                -MathUtil.applyDeadband(driverController.getLeftX(), OIConstants.kDriveDeadband),
                -MathUtil.applyDeadband(driverController.getRightX(), OIConstants.kDriveDeadband),
                true, true),
            swerveDriveSubsystem));
  }

  private void configureBindings() {

    //Co-Driver Controls
    xButton.onTrue(new RunCommand(() -> swerveDriveSubsystem.setX(), swerveDriveSubsystem));
    yButton.onTrue(new InstantCommand(swerveDriveSubsystem::zeroHeading));




  }

}
