package frc.robot.commands.ScoreCommands;

import edu.wpi.first.math.filter.Debouncer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.EndEffectorSubsystem;
import static frc.robot.Constants.SubsystemConstants;

public class IntakeCommand extends Command{

    private final EndEffectorSubsystem intakeSubsystem;
    private final double speed;
    private final boolean inverted; 
     boolean noteIn = false; 
    private Debouncer filter_debouncer = new Debouncer(SubsystemConstants.kIntakeDebounceTime, Debouncer.DebounceType.kBoth);

    public IntakeCommand(EndEffectorSubsystem intakeSubsystem, double speed, boolean inverted){
        this.intakeSubsystem = intakeSubsystem; 
        this.speed = speed;
        this.inverted = inverted; 
        addRequirements(intakeSubsystem);
    }

    @Override 
    public void initialize(){
      SmartDashboard.putBoolean("NoteIn", noteIn); 
      intakeSubsystem.setIntakeSpeedDirection(speed, inverted);
    }

    // @Override 
    // public boolean isFinished(){ 
    //     if(SubsystemConstants.kIntakeDebounce) {
    //         noteIn = true; 
    //         SmartDashboard.putBoolean("NoteIn", noteIn); 
    //         return filter_debouncer.calculate(EndEffectorSubsystem.filteredCurrentIntake>SubsystemConstants.kIntakeCurrentThreshold);
    //     } else {
    //         noteIn = true; 
    //         SmartDashboard.putBoolean("NoteIn", noteIn); 
    //         return EndEffectorSubsystem.filteredCurrentIntake > SubsystemConstants.kIntakeCurrentThreshold;
    //     }
    // }
    // @Override
    // public void end(boolean interrupted){
    //   if(interrupted){
    //     if((EndEffectorSubsystem.filteredCurrentIntake < SubsystemConstants.kIntakeCurrentThreshold) && noteIn) {
    //         intakeSubsystem.setIntakeSpeedDirection(0, inverted); 
    //     }
    //     else {
    //         intakeSubsystem.setIntakeSpeedDirection(0, inverted);
    //     }
    //   }
    // }
}