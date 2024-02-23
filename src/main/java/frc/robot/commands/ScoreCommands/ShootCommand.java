package frc.robot.commands.ScoreCommands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.EndEffectorSubsystem;


public class ShootCommand extends Command{

    private final EndEffectorSubsystem shootSubsystem;
    private final double speed; 

    public ShootCommand(EndEffectorSubsystem shootSubsystem, double speed){
        this.shootSubsystem = shootSubsystem; 
        this.speed = speed;
        addRequirements(shootSubsystem);
    }

    @Override 
    public void initialize(){
    }

    @Override 
    public void execute(){
       shootSubsystem.shootLeadMotor(speed);
    }

    // @Override 
    // public boolean isFinished(){}
    
}