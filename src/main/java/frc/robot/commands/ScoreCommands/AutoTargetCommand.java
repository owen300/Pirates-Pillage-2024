package frc.robot.commands.ScoreCommands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.BlinkinSubsystem;
import frc.robot.subsystems.EndEffectorSubsystem;
import frc.robot.subsystems.LimelightSubsystem;
import frc.robot.subsystems.SmartSwerveDriveSubsystem;

public class AutoTargetCommand extends Command {

   
    private final LimelightSubsystem limelightSubsystem;
    private final SmartSwerveDriveSubsystem swerveDriveSubsystem;
    private final EndEffectorSubsystem endEffectorSubsystem;
    private boolean isReadyToFire;
    
    public AutoTargetCommand(LimelightSubsystem limelightSubsystem, SmartSwerveDriveSubsystem swerveDriveSubsystem, EndEffectorSubsystem endEffectorSubsystem){
        this.limelightSubsystem = limelightSubsystem;
        this.swerveDriveSubsystem = swerveDriveSubsystem;
        this.endEffectorSubsystem = endEffectorSubsystem;
        this.isReadyToFire = false;
        addRequirements(limelightSubsystem);
        //addRequirements(swerveDriveSubsystem);
        addRequirements(endEffectorSubsystem);
    }


    @Override 
    public void execute(){
        EndEffectorSubsystem.lift(limelightSubsystem.getAutoAimEncoderTarget());
        //swerveDriveSubsystem.enableAutoFace(true);
        if (endEffectorSubsystem.isLiftAtTarget() && swerveDriveSubsystem.alignedToGoal()) isReadyToFire = true;
        System.out.println("executed"); 
    }

    @Override
    public void end(boolean interrupted){

        //swerveDriveSubsystem.enableAutoFace(false);
        isReadyToFire = false;
        BlinkinSubsystem.green();
        System.out.println("ended");
    }

    public boolean getIsReadyToFire() {
        return isReadyToFire;
    }

}

