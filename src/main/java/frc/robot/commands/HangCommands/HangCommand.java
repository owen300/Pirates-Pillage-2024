package frc.robot.commands.HangCommands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.HangSubsystem;


public class HangCommand extends Command{

    private final HangSubsystem hangSubsystem;
    private final double setPoint;
    private final double TOLERANCE = 0.5; 
    double startTime;

    public HangCommand(HangSubsystem hangSubsystem, double setpoint){
        this.hangSubsystem = hangSubsystem; 
        this.setPoint = setpoint;
        addRequirements(hangSubsystem);
    }

    @Override 
    public void initialize(){}

    @Override 
    public void execute(){
       hangSubsystem.hang(setPoint);       
    }

    @Override 
    public boolean isFinished(){
       return (hangSubsystem.getHangDistance() < setPoint + TOLERANCE && hangSubsystem.getHangDistance() > setPoint - TOLERANCE);
    }
    
}