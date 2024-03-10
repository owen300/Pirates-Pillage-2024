package frc.robot.commands.HangCommands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.HangSubsystem;


public class hang extends Command{

    private final HangSubsystem hangSubsystem;
    double speed; 

    public hang(HangSubsystem hangSubsystem, double speed){
        this.hangSubsystem = hangSubsystem; 
        this.speed = speed;
        addRequirements(hangSubsystem);
    }

    @Override 
    public void initialize(){
    
    }

    @Override 
    public void execute(){
       hangSubsystem.hangTest(speed);       
    }

    
}