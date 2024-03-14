package frc.robot.commands.ScoreCommands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.EndEffectorSubsystem;


public class HangCommand extends Command{

    private final EndEffectorSubsystem hangSubsystem;
    private final double setPoint;
    private final double TOLERANCE = 0.5; 
    double startTime;

    public HangCommand(EndEffectorSubsystem hangSubsystem, double setpoint){
        this.hangSubsystem = hangSubsystem; 
        this.setPoint = setpoint;
        addRequirements(hangSubsystem);
    }

    @Override 
    public void initialize(){}

    @Override 
    public void execute(){
       EndEffectorSubsystem.moveHang(setPoint);       
    }

    @Override 
    public boolean isFinished(){
       return (EndEffectorSubsystem.getLiftDistance() < setPoint + TOLERANCE && EndEffectorSubsystem.getLiftDistance() > setPoint - TOLERANCE);
    }
    
}