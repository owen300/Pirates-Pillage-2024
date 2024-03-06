package frc.robot.commands.AutoCommands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.ScoreCommands.ScoreCommandHolder;
import frc.robot.subsystems.EndEffectorSubsystem;
import frc.robot.subsystems.SwerveDriveSubsystem;

public class AutoCommandHolder extends Command{
    
    EndEffectorSubsystem endEffectorSubsystem;
    ScoreCommandHolder scoreCommandHolder;
    static SwerveDriveSubsystem swerveDriveSubsystem; 

    public AutoCommandHolder(EndEffectorSubsystem endEffectorSubsystem, ScoreCommandHolder scoreCommandHolder, SwerveDriveSubsystem swerveDriveSubsystem){
        this.endEffectorSubsystem = endEffectorSubsystem; 
        this.scoreCommandHolder = scoreCommandHolder;   
        AutoCommandHolder.swerveDriveSubsystem = swerveDriveSubsystem; 
    }
    

    public Command driveBack(){
       return new DriveCommand(swerveDriveSubsystem, 5);
    }

    public void doNothing(){}






}




