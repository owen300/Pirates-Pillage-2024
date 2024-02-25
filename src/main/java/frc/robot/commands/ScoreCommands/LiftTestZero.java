package frc.robot.commands.ScoreCommands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.EndEffectorSubsystem;


public class LiftTestZero extends Command{

    private final EndEffectorSubsystem liftSubsystem;

    public LiftTestZero(EndEffectorSubsystem liftSubsystem){
        this.liftSubsystem = liftSubsystem; 
        addRequirements(liftSubsystem);
    }

    @Override 
    public void initialize(){
    }

    @Override 
    public void execute(){
        liftSubsystem.liftLeadLeftMotorZero();
        liftSubsystem.liftLeadRightMotorZero();
       
    }

    // @Override 
    // public boolean isFinished(){
    //    return liftSubsystem.getLiftLeadBackDistance() < setPoint + TOLERANCE && liftSubsystem.getLiftLeadBackDistance() > setPoint - TOLERANCE;
    // }
    
}