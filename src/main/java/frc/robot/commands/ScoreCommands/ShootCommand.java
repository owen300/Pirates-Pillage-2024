package frc.robot.commands.ScoreCommands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.EndEffectorSubsystem;


public class ShootCommand extends Command{

    private final EndEffectorSubsystem shootSubsystem;
    private final double setPoint;
    private final double TOLERANCE = 2; 

    public ShootCommand(EndEffectorSubsystem shootSubsystem, double setpoint){
        this.shootSubsystem = shootSubsystem; 
        this.setPoint = setpoint;
        addRequirements(shootSubsystem);
    }

    @Override 
    public void initialize(){
    }

    @Override 
    public void execute(){
       shootSubsystem.shoot(setPoint);
    }

    @Override 
    public boolean isFinished(){
       return shootSubsystem.getShootLeadDistance() < setPoint + TOLERANCE && shootSubsystem.getShootLeadDistance() > setPoint - TOLERANCE;
    }
    
}