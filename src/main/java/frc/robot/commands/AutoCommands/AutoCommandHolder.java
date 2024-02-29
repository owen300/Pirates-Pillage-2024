package frc.robot.commands.AutoCommands;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.path.PathPlannerPath;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.ScoreCommands.ScoreCommandHolder;
import frc.robot.subsystems.EndEffectorSubsystem;

public class AutoCommandHolder extends Command{
    
    EndEffectorSubsystem endEffectorSubsystem;
    ScoreCommandHolder scoreCommandHolder;

    public AutoCommandHolder(EndEffectorSubsystem endEffectorSubsystem, ScoreCommandHolder scoreCommandHolder){
        this.endEffectorSubsystem = endEffectorSubsystem; 
        this.scoreCommandHolder = scoreCommandHolder;   
    }
    

    public SequentialCommandGroup driveBack(AutoBuilder autoBuilder){
       PathPlannerPath getNote = PathPlannerPath.fromPathFile("goBack");
       return new SequentialCommandGroup( 
           AutoBuilder.followPath(getNote)); 
    }







}




