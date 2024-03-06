package frc.robot.commands.LimelightCommands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.EndEffectorSubsystem;
import frc.robot.subsystems.LimelightSubsystem;

public class LiftAimCommand extends Command {

   
    private final EndEffectorSubsystem endEffectorSubsystem;
    private final LimelightSubsystem limelight;
    double startTime;
    double speed; 

    public LiftAimCommand(EndEffectorSubsystem endEffectorSubsystem, LimelightSubsystem limelight, double speed){
        this.endEffectorSubsystem = endEffectorSubsystem; 
        this.limelight = limelight; 
        this.speed = speed;
        addRequirements(endEffectorSubsystem, limelight);
    }

   
    @Override 
    public void execute(){
       if(limelight.hasTarget() && limelight.alignedToGoal()){
        SmartDashboard.putString("Target & Aligned", "happy face");
       }
       else if (limelight.hasTarget()){
        SmartDashboard.putString("Has Target", "meh face");
       }
       else  SmartDashboard.putString("Nothing", "sad face");
       
       endEffectorSubsystem.limelightLift(speed + limelight.getTurnSpeed());
    }


}

