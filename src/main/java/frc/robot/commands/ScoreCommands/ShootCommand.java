package frc.robot.commands.ScoreCommands;

import edu.wpi.first.math.filter.Debouncer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.SubsystemConstants;
import frc.robot.subsystems.EndEffectorSubsystem;


public class ShootCommand extends Command{

    private final EndEffectorSubsystem shootSubsystem;
    private final double speed; 
      private Debouncer filter_debouncer = new Debouncer(SubsystemConstants.kShootDebounceTime, Debouncer.DebounceType.kBoth);
    public boolean noteIn = false; 

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

    //  @Override 
    // public boolean isFinished(){ 
    //     if(SubsystemConstants.kShootDebounce) {
    //         noteIn = true; 
    //         SmartDashboard.putBoolean("NoteIn", noteIn); 
    //         return filter_debouncer.calculate(EndEffectorSubsystem.filteredCurrentShoot>SubsystemConstants.kShootCurrentThreshold);
    //     } else {
    //         noteIn = true; 
    //         SmartDashboard.putBoolean("NoteIn", noteIn); 
    //         return EndEffectorSubsystem.filteredCurrentShoot > SubsystemConstants.kShootCurrentThreshold;
    //     }
    // }
    // @Override
    // public void end(boolean interrupted){
    //   if(interrupted){
    //     if((EndEffectorSubsystem.filteredCurrentShoot < SubsystemConstants.kShootCurrentThreshold) && noteIn) {
    //          shootSubsystem.shootLeadMotor(0);
    //     }
    //     else {
    //         shootSubsystem.shootLeadMotor(0);
    //     }
    //   }
    // }
}