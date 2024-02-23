package frc.robot.commands.ScoreCommands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.EndEffectorSubsystem;


public class ScoreCommandHolder extends Command {

    private final EndEffectorSubsystem endEffectorSubsystem;
    

    public ScoreCommandHolder(EndEffectorSubsystem endEffectorSubsystem){
        this.endEffectorSubsystem = endEffectorSubsystem; 
    }

    //TEST METHODS
    public Command testIntake(){
       return new IntakeCommand(endEffectorSubsystem, 0.1); 
    }

    public Command testShoot(){
        return new ShootCommand(endEffectorSubsystem, 0.1); 
    }

    // public Command testLift(){
    //     return new LiftCommand(endEffectorSubsystem, 0); 
    // }

    //REAL METHODS
    public SequentialCommandGroup name() {
         return new SequentialCommandGroup();
    }
   


}