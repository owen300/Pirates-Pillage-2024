package frc.robot.commands;

import frc.robot.subsystems.LimelightSubsystem;
import edu.wpi.first.wpilibj2.command.Command;


public class LimelightCommand extends Command {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final LimelightSubsystem limelightSubsystem;

  
  public LimelightCommand(LimelightSubsystem limelightSubsystem) {
    this.limelightSubsystem = limelightSubsystem;
    addRequirements(limelightSubsystem);
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    limelightSubsystem.driveToTarget();
  }

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return false;
  }
}
