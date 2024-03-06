package frc.robot.commands.ScoreCommands;

import edu.wpi.first.math.filter.Debouncer;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.EndEffectorSubsystem;
import static frc.robot.Constants.SubsystemConstants;

public class Outtake extends Command{

    private final EndEffectorSubsystem endEffectorSubsystem;
    private final double speed;
    private final boolean inverted; 

 
    public Outtake(EndEffectorSubsystem endEffectorSubsystem, double speed, boolean inverted){
        this.endEffectorSubsystem = endEffectorSubsystem; 
        this.speed = speed;
        this.inverted = inverted; 
        addRequirements(endEffectorSubsystem);
    }

    @Override 
    public void initialize(){
    }


    @Override 
    public void execute(){
      endEffectorSubsystem.setIntakeSpeedDirection(speed, inverted);
    }

     @Override 
    public boolean isFinished(){
      return true;
    }


}