package frc.robot.commands.ScoreCommands;

import edu.wpi.first.math.filter.Debouncer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.EndEffectorSubsystem;
import static frc.robot.Constants.SubsystemConstants;

public class IntakeCommand extends Command{

    private final EndEffectorSubsystem intakeSubsystem;
    private final double speed;
    private Debouncer filter_debouncer = new Debouncer(SubsystemConstants.kDebounceTime, Debouncer.DebounceType.kBoth);

    public IntakeCommand(EndEffectorSubsystem intakeSubsystem, double speed){
        this.intakeSubsystem = intakeSubsystem; 
        this.speed = speed;
        addRequirements(intakeSubsystem);
    }

    @Override 
    public void initialize(){
      intakeSubsystem.setIntakeSpeedDirection(speed, false);
    }

    // @Override 
    // public boolean isFinished(){ 
    //     if(SubsystemConstants.kDebounce) {
    //         return filter_debouncer.calculate(EndEffectorSubsystem.filteredCurrentIntake>SubsystemConstants.kCurrentThreshold);
    //     } else {
    //         return EndEffectorSubsystem.filteredCurrentIntake > SubsystemConstants.kCurrentThreshold;
    //     }
    // }
    // @Override
    // public void end(boolean interrupted){
    //     if(interrupted) {
    //         intakeSubsystem.setIntakeSpeedDirection(0.05, false); 
    //     }
    //     else {
    //         intakeSubsystem.setIntakeSpeedDirection(0.05, false);
    //     }
    // }
}