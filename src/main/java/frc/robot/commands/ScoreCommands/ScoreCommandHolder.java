package frc.robot.commands.ScoreCommands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.Constants.ScoreCommandHolderConstants;
import frc.robot.subsystems.EndEffectorSubsystem;


public class ScoreCommandHolder extends Command {

    private final EndEffectorSubsystem endEffectorSubsystem;
    

    public ScoreCommandHolder(EndEffectorSubsystem endEffectorSubsystem){
        this.endEffectorSubsystem = endEffectorSubsystem; 
    }

     public SequentialCommandGroup intakeNote(){
        return new SequentialCommandGroup(
            new LiftCommand(endEffectorSubsystem, ScoreCommandHolderConstants.kIntakeSetpoint), 
            new IntakeCommand(endEffectorSubsystem, 0.5, isFinished()), 
            new LiftCommand (endEffectorSubsystem, ScoreCommandHolderConstants.kCompactSetpoint)
        ); 
    }

    public SequentialCommandGroup scoreAmp(){
        return new SequentialCommandGroup(
            new ShootCommand(endEffectorSubsystem, 0.8),
            new LiftCommand(endEffectorSubsystem, ScoreCommandHolderConstants.kAmpSetpoint)
        ); 
    }

    public SequentialCommandGroup scoreSpeaker(){
        return new SequentialCommandGroup(
            new ShootCommand(endEffectorSubsystem, 0.8),
            new LiftCommand(endEffectorSubsystem, ScoreCommandHolderConstants.kSpeakerSetpoint)
        ); 
    }

     public SequentialCommandGroup shootNote(){
        return new SequentialCommandGroup(
            new IntakeTimeCommand(endEffectorSubsystem, 0.7, false),
            new ShootCommand(endEffectorSubsystem, 0),
            new LiftCommand (endEffectorSubsystem, ScoreCommandHolderConstants.kCompactSetpoint)
        ); 
    }






    //TEST METHODS
    public Command testIntake(){
       return new IntakeCommand(endEffectorSubsystem, 0.5, false); 
    }

    public Command testShoot(){
        return new ShootCommand(endEffectorSubsystem, 0.8); 
    }

    public Command liftScoreAmp(){
        return new LiftCommand(endEffectorSubsystem, -0.19); 
    }

    public SequentialCommandGroup liftScoreSpeaker(){
        return new SequentialCommandGroup(
        new LiftCommand(endEffectorSubsystem, -0.1), 
        new LiftCommand(endEffectorSubsystem, -0.04));
    }



    public Command shootMotorZero(){
        return new ShootCommand(endEffectorSubsystem, 0); 
    }
    public Command intakeMotorZero(){
        return new IntakeCommand(endEffectorSubsystem, 0, false); 
    }
   
}