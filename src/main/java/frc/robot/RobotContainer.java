package frc.robot;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants.OIConstants;
import frc.robot.subsystems.SwerveDriveSubsystem;

public class RobotContainer {

  CommandXboxController driverController= new CommandXboxController(0); 
  CommandXboxController coDriverController= new CommandXboxController(1); 

  SendableChooser<Command> AutoChooser = new SendableChooser<>();

  //Subsystem
  SwerveDriveSubsystem swerveDriveSubsystem = new SwerveDriveSubsystem();

  //Commands


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

  private void configureBindings() {}

}
