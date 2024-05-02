package frc.robot.commands.ScoreCommands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants.ScoreCommandHolderConstants;
import frc.robot.subsystems.EndEffectorSubsystem;


public class ScoreCommandHolder extends Command {

    private final EndEffectorSubsystem endEffectorSubsystem;
    

    public ScoreCommandHolder(EndEffectorSubsystem endEffectorSubsystem){
        this.endEffectorSubsystem = endEffectorSubsystem; 
    }

     public SequentialCommandGroup intakeNote(){
        return new SequentialCommandGroup(
            new LiftCommand(ScoreCommandHolderConstants.kIntakeFirstSetpoint),
            new LiftCommand(ScoreCommandHolderConstants.kIntakeSecondSetpoint),
            new IntakeCommand(endEffectorSubsystem, 1, false), 
            new IntakeTimeCommand(endEffectorSubsystem, 0.1, true, 0.3),
            new LiftCommand(ScoreCommandHolderConstants.kCompactSetpoint)
        ); 
    }

      public SequentialCommandGroup shootNote(){
        return new SequentialCommandGroup(
            new IntakeTimeCommand(endEffectorSubsystem, 1, false, 1),
            compactPosition()
        ); 
    }

    public SequentialCommandGroup shootNote2(){
        return new SequentialCommandGroup(
            new IntakeTimeCommand(endEffectorSubsystem, 1, false, 1),
            compactPosition2()
        ); 
    }

    public SequentialCommandGroup scoreAmp(){
        return new SequentialCommandGroup(
            new ShootCommand(0.3),
            new LiftCommand(ScoreCommandHolderConstants.kAmpSetpoint)
        ); 
    }

    public SequentialCommandGroup scoreSpeaker(){
        return new SequentialCommandGroup(
            new LiftCommand(ScoreCommandHolderConstants.kSpeakerSetpoint)
        ); 
    }

     public SequentialCommandGroup shuttle(){
        return new SequentialCommandGroup(
            new ShootCommand(0.72),
            new LiftCommand(ScoreCommandHolderConstants.kSpeakerSetpoint)
        ); 
    }

    public SequentialCommandGroup scoreSpeakerAndShootNote(){
        return new SequentialCommandGroup(
            scoreSpeaker(),
            new WaitCommand(1),
            shootNote2()
        ); 
    }

    public SequentialCommandGroup compactPosition(){
        return new SequentialCommandGroup(
            new LiftCommand(ScoreCommandHolderConstants.kCompactSetpoint),
            new IntakeCommand(endEffectorSubsystem, 0, false ),
            new ShootCommand(0.85) 
        ); 
    }

    public SequentialCommandGroup compactPosition2(){
        return new SequentialCommandGroup(
            new LiftCommand(ScoreCommandHolderConstants.kCompactSetpoint),
            new ShootCommand(0),
            new IntakeTimeCommand(endEffectorSubsystem, 0, false, 0.1)
        ); 
    }

    public Command setFlyWheel(){
        return new InstantCommand(()->endEffectorSubsystem.shooterpidmode(),endEffectorSubsystem);
    }

    public Command setFlyWheelZero(){
        return new ShootCommand(0); 
    }


    public SequentialCommandGroup getHangReady(){
        return new SequentialCommandGroup(new LiftCommand(ScoreCommandHolderConstants.kLiftHangSetpoint), new WaitCommand(0.5), new HangCommand(ScoreCommandHolderConstants.kHangUpSetpoint) ); 
    }

    public Command hang(){
        return new HangCommand(ScoreCommandHolderConstants.kHangDownSetpoint);
    }

    public SequentialCommandGroup outtake(){
        return new SequentialCommandGroup( new Outtake(endEffectorSubsystem, 0.5, true), new WaitCommand(0.05), new Outtake(endEffectorSubsystem, 0, true)); 
    }

    public Command shootMotorZero(){
        return new ShootCommand(0); 
    }

    public Command intakeMotorZero(){
        return new IntakeCommand(endEffectorSubsystem, 0, false); 
    }

    public Command intakeDown(){
        return new LiftCommand(ScoreCommandHolderConstants.kHangSetpoint); 
    }

 
}