package frc.robot.commands.ScoreCommands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.BlinkinSubsystem;
import frc.robot.subsystems.EndEffectorSubsystem;
import frc.robot.subsystems.LimelightSubsystem;

public class AutoAimCommand extends Command {

   
    private final LimelightSubsystem limelightSubsystem;
    
    public AutoAimCommand(LimelightSubsystem limelightSubsystem){
        this.limelightSubsystem = limelightSubsystem;
        //addRequirements(limelightSubsystem);
    }


    @Override 
    public void execute(){
        EndEffectorSubsystem.lift(limelightSubsystem.getAutoAimEncoderTarget());
    }

    @Override
    public void end(boolean interrupted){
        BlinkinSubsystem.green();
    }

}

