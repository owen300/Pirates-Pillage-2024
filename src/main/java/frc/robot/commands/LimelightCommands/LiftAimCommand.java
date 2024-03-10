package frc.robot.commands.LimelightCommands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.LimelightSubsystem;

public class LiftAimCommand extends Command {

   
    private final LimelightSubsystem limelightSubsystem;
    double startTime;
    double speed; 

    public LiftAimCommand(LimelightSubsystem limelightSubsystem){
        this.limelightSubsystem = limelightSubsystem; 
        addRequirements(limelightSubsystem);
    }

   
    @Override 
    public void execute(){
     limelightSubsystem.setShooterAngle();
    }


}

