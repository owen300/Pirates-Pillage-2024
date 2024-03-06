package frc.robot.commands.ScoreCommands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.EndEffectorSubsystem;

public class LiftDown extends Command{

    private final EndEffectorSubsystem endEffectorSubsystem; 
    private double setpoint; 

    public LiftDown(EndEffectorSubsystem endEffectorSubsystem){
        this.endEffectorSubsystem = endEffectorSubsystem;
    }    

    @Override
    public void initialize(){
        setpoint = endEffectorSubsystem.getLiftDistance();
    }

    @Override
    public void execute(){
        setpoint-= 0.05;
    }

    @Override 
    public boolean isFinished(){
        return true;
    }

    @Override
    public void end(boolean interrupted){
        endEffectorSubsystem.lift(setpoint);

    }



}
