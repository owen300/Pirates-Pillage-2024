package frc.robot.commands.ScoreCommands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.EndEffectorSubsystem;


public class LiftTest extends Command{

    private final EndEffectorSubsystem liftSubsystem;

    public LiftTest(EndEffectorSubsystem liftSubsystem){
        this.liftSubsystem = liftSubsystem; 
        addRequirements(liftSubsystem);
    }

    @Override 
    public void initialize(){
    }

    @Override 
    public void execute(){
        liftSubsystem.liftLeadLeftMotorTest();
        liftSubsystem.liftLeadRightMotorTest();
       
    }

    // @Override 
    // public boolean isFinished(){
    //    return liftSubsystem.getLiftLeadBackDistance() < setPoint + TOLERANCE && liftSubsystem.getLiftLeadBackDistance() > setPoint - TOLERANCE;
    // }
    
}