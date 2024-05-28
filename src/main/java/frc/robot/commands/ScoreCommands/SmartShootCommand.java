package frc.robot.commands.ScoreCommands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.subsystems.EndEffectorSubsystem;

public class SmartShootCommand extends Command{

    private final EndEffectorSubsystem intakeSubsystem;
    private final double speed;
    private final boolean inverted; 
    
    private boolean stop=false;

    public SmartShootCommand(EndEffectorSubsystem intakeSubsystem, double speed, boolean inverted){
        this.intakeSubsystem = intakeSubsystem; 
        this.speed = speed;
        this.inverted = inverted; 
        addRequirements(intakeSubsystem);
    }

    @Override 
    public void initialize(){
      intakeSubsystem.setIntakeSpeedDirection(speed, inverted);
      if(intakeSubsystem.getSensorInput()) stop =true;
      
    }

    @Override 
    public boolean isFinished(){ 
        if(intakeSubsystem.getSensorInput()) return true;
        return stop;
    }

    @Override
    public void end(boolean interrupted){
      if(interrupted){
            CommandScheduler.getInstance().schedule(new IntakeandCompact(intakeSubsystem, 1,false,0.25));
        }
        else {
            CommandScheduler.getInstance().schedule(new IntakeandCompact(intakeSubsystem, 1,false,0.25));
        }
    }
}