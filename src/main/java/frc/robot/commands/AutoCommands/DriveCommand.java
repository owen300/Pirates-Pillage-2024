package frc.robot.commands.AutoCommands;

import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.SwerveDriveSubsystem;


public class DriveCommand extends Command{

    private final SwerveDriveSubsystem swerveDriveSubsystem;
    private double time = 5; 
    ChassisSpeeds chassisSpeeds = new ChassisSpeeds(-1,0,0); 
    double startTime; 
    
    public DriveCommand(SwerveDriveSubsystem swerveDriveSubsystem){
       this.swerveDriveSubsystem = swerveDriveSubsystem;
        addRequirements(swerveDriveSubsystem);
    }

    @Override 
    public void initialize(){
      swerveDriveSubsystem.driveRobotRelative(chassisSpeeds);
    }

    @Override 
    public boolean isFinished(){ 
        return Timer.getFPGATimestamp() - startTime > time;
    }

    @Override
    public void end(boolean interrupted){}
}