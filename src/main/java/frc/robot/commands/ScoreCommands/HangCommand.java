package frc.robot.commands.ScoreCommands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.HangSubsystem;


public class HangCommand extends Command{

    private final HangSubsystem hangSubsystem;
  

    public HangCommand(HangSubsystem hangSubsystem){
        this.hangSubsystem = hangSubsystem; 
        addRequirements(hangSubsystem);
    }

    @Override 
    public void initialize(){}

    @Override 
    public void execute(){
       hangSubsystem.hang();       
    }
    
}