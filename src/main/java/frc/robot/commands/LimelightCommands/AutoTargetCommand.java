package frc.robot.commands.LimelightCommands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.BlinkinSubsystem;
import frc.robot.subsystems.EndEffectorSubsystem;
import frc.robot.subsystems.SmartLimelightSubsystem;
import frc.robot.subsystems.SmartSwerveDriveSubsystem;
import frc.robot.subsystems.SwerveDriveSubsystem;

public class AutoTargetCommand extends Command {

   
    private final SmartLimelightSubsystem limelightSubsystem;
    private final SmartSwerveDriveSubsystem swerveDriveSubsystem;
    private final EndEffectorSubsystem endEffectorSubsystem;
    private boolean isReadyToFire;
    
    public AutoTargetCommand(SmartLimelightSubsystem limelightSubsystem, SmartSwerveDriveSubsystem swerveDriveSubsystem, EndEffectorSubsystem endEffectorSubsystem){
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
        limelightSubsystem.setShooterAngle();
        swerveDriveSubsystem.enableAutoFace(true);
        if (endEffectorSubsystem.isLiftAtTarget() && swerveDriveSubsystem.alignedToGoal()) isReadyToFire = true;
        System.out.println("executed"); 
    }

    @Override
    public void end(boolean interrupted){

        EndEffectorSubsystem.lift(limelightSubsystem.getLastSetpoint());
        swerveDriveSubsystem.enableAutoFace(false);
        isReadyToFire = false;
        BlinkinSubsystem.green();
        System.out.println("ended");
    }

    public boolean getIsReadyToFire() {
        return isReadyToFire;
    }

}

