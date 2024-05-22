package frc.robot.commands.ScoreCommands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.Robot;
import frc.robot.subsystems.EndEffectorSubsystem;
public class IntakeandCompact extends Command{

    private final EndEffectorSubsystem intakeSubsystem;
    private final double speed;
    private final double time; 
    private final boolean inverted; 
    double startTime;

    public IntakeandCompact(EndEffectorSubsystem intakeSubsystem, double speed, boolean inverted, double time){
        this.intakeSubsystem = intakeSubsystem; 
        this.speed = speed;
        this.inverted = inverted; 
        this.time = time; 
        addRequirements(intakeSubsystem);
    }

    @Override 
    public void initialize(){
      startTime = Timer.getFPGATimestamp();
      intakeSubsystem.setIntakeSpeedDirection(speed, inverted);
    }

    @Override 
    public boolean isFinished(){ 
        return Timer.getFPGATimestamp() - startTime > time;
    }

    @Override
    public void end(boolean interrupted){
      if(interrupted){
            ParallelCommandGroup robot= Robot.getInstance().scorecommands.compactPosition();
            CommandScheduler.getInstance().schedule(robot);
        }
        
        else {
            ParallelCommandGroup robot= Robot.getInstance().scorecommands.compactPosition();
            CommandScheduler.getInstance().schedule(robot);
        }
    }
}