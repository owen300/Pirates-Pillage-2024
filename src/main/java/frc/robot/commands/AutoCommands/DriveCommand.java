package frc.robot.commands.AutoCommands;

import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.EndEffectorSubsystem;
import frc.robot.subsystems.SwerveDriveSubsystem;


public class DriveCommand extends Command{

    private final SwerveDriveSubsystem swerveDriveSubsystem;
    private double time = 4; 
    private final double xSpeed, ySpeed, rSpeed;
    double startTime; 
    

    public DriveCommand(SwerveDriveSubsystem swerveDriveSubsystem, double xSpeed, double ySpeed, double rSpeed){
       this.swerveDriveSubsystem = swerveDriveSubsystem;
        addRequirements(swerveDriveSubsystem);
    }

    @Override 
    public void initialize(){
      swerveDriveSubsystem.drive(xSpeed, ySpeed, rSpeed, true, true );
    }

    @Override 
    public boolean isFinished(){ 
        return Timer.getFPGATimestamp() - startTime > time;
    }

    @Override
    public void end(boolean interrupted){}
}