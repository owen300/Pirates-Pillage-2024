package frc.robot.commands.HangCommands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.HangSubsystem;


public class HangCommand extends Command{

    private final HangSubsystem hangSubsystem;
    double speed; 

    public HangCommand(HangSubsystem hangSubsystem, double speed){
        this.hangSubsystem = hangSubsystem; 
        this.speed = speed; 
        addRequirements(hangSubsystem);
    }

    @Override 
    public void initialize(){}

    @Override 
    public void execute(){
       hangSubsystem.hang(speed);       
    }
    
}