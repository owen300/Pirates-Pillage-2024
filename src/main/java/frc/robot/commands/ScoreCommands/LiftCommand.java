package frc.robot.commands.ScoreCommands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.EndEffectorSubsystem;


public class LiftCommand extends Command{

    private final EndEffectorSubsystem liftSubsystem;
    private final double setPoint;
    private final double TOLERANCE = 0.5; 

    public LiftCommand(EndEffectorSubsystem liftSubsystem, double setpoint){
        this.liftSubsystem = liftSubsystem; 
        this.setPoint = setpoint;
        addRequirements(liftSubsystem);
    }

    @Override 
    public void initialize(){
    }

    @Override 
    public void execute(){
       liftSubsystem.lift(setPoint);       
    }

    @Override 
    public boolean isFinished(){
       return liftSubsystem.getLiftDistance() < setPoint + TOLERANCE && liftSubsystem.getLiftDistance() > setPoint - TOLERANCE;
    }
    
}