package frc.robot.commands.ScoreCommands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.EndEffectorSubsystem;

public class LiftSetpointAdjuster extends Command{

    private final EndEffectorSubsystem endEffectorSubsystem; 
    private double setpoint; 
    private boolean up; 

    public LiftSetpointAdjuster(EndEffectorSubsystem endEffectorSubsystem, boolean up){
        this.up = up; 
        this.endEffectorSubsystem = endEffectorSubsystem;
    }    

    @Override
    public void initialize(){
        setpoint = endEffectorSubsystem.getLiftDistance();
    }

    @Override
    public void execute(){
        if(up = true){
        setpoint-= 0.05;
        }
        else setpoint += 0.01;
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
