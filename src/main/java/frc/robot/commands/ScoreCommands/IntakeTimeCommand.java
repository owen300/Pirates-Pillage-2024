package frc.robot.commands.ScoreCommands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.EndEffectorSubsystem;
public class IntakeTimeCommand extends Command{

    private final EndEffectorSubsystem intakeSubsystem;
    private final double speed;
    private final double time; 
    private final boolean inverted; 
    double startTime;

    public IntakeTimeCommand(EndEffectorSubsystem intakeSubsystem, double speed, boolean inverted, double time){
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
            intakeSubsystem.setIntakeSpeedDirection(0, false); 
        }
        else {
            intakeSubsystem.setIntakeSpeedDirection(0, false);
        }
    }
}