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
        Double target = limelightSubsystem.getAutoAimEncoderTarget();
        if (target != null) EndEffectorSubsystem.lift(target);
    }

    @Override
    public void end(boolean interrupted){
        BlinkinSubsystem.green();
    }

}

