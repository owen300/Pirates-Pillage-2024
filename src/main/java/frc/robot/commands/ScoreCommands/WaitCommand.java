package frc.robot.commands.ScoreCommands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.EndEffectorSubsystem;


public class WaitCommand extends Command{

    double startTime;

    public WaitCommand(){
        addRequirements();
    }

    @Override 
    public void initialize(){
        startTime = Timer.getFPGATimestamp();
    }

    @Override 
    public void execute(){    
    }

    @Override 
    public boolean isFinished(){
       return Timer.getFPGATimestamp() - startTime > 3;
    }
    
}