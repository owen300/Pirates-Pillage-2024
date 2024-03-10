package frc.robot.commands.ScoreCommands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.EndEffectorSubsystem;


public class ShootCommand extends Command{

    private final EndEffectorSubsystem shootSubsystem;
    private final double speed; 
    double startTime;

    public ShootCommand(EndEffectorSubsystem shootSubsystem, double speed){
        this.shootSubsystem = shootSubsystem; 
        this.speed = speed;
        addRequirements(shootSubsystem);
    }

    @Override 
    public void initialize(){
      startTime = Timer.getFPGATimestamp();
    }

    @Override 
    public void execute(){
      EndEffectorSubsystem.shootLeadMotor(speed);
    }

     @Override 
    public boolean isFinished(){ 
      return Timer.getFPGATimestamp() - startTime > 0.5;
    }

}