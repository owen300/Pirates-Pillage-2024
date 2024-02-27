package frc.robot.commands.ScoreCommands;

import edu.wpi.first.math.filter.Debouncer;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.SubsystemConstants;
import frc.robot.subsystems.EndEffectorSubsystem;
import static frc.robot.Constants.SubsystemConstants;

public class IntakeTimeCommand extends Command{

    private final EndEffectorSubsystem intakeSubsystem;
    private final double speed;
    private final boolean inverted; 
    double startTime;

    public IntakeTimeCommand(EndEffectorSubsystem intakeSubsystem, double speed, boolean inverted){
        this.intakeSubsystem = intakeSubsystem; 
        this.speed = speed;
        this.inverted = inverted; 
        addRequirements(intakeSubsystem);
    }

    @Override 
    public void initialize(){
      startTime = Timer.getFPGATimestamp();
      intakeSubsystem.setIntakeSpeedDirection(speed, inverted);
    }

    @Override 
    public boolean isFinished(){ 
        return Timer.getFPGATimestamp() - startTime > 2;
    }

    @Override
    public void end(boolean interrupted){
      if(interrupted){
            intakeSubsystem.setIntakeSpeedDirection(0, inverted); 
        }
        else {
            intakeSubsystem.setIntakeSpeedDirection(0, inverted);
        }
    }
}