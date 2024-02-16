package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.utils.LimelightUtils;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class LimelightSubsystem extends SubsystemBase {

    SwerveDriveSubsystem swerveDriveSubsystem; 
    
    double tx;
    double ty;
    double ta;

    private boolean hasTarget;
    private double drive;
    private double turn; 
    private double steer;
    
    private double kP; 


    public LimelightSubsystem() {
      swerveDriveSubsystem = new SwerveDriveSubsystem(); 

      tx = LimelightUtils.getTX("");
      ty = LimelightUtils.getTY("");
      ta = LimelightUtils.getTA("");

      hasTarget = false; 
      drive = 0.0;
      turn = 0.0; 
      steer = 0.0;

      kP = 0.5; 
    }

      public double getTx() {
      tx = LimelightUtils.getTX("");
      return tx; 
    }

    public double getTy() {
      ty = LimelightUtils.getTY("");
      return ty;  
    }

    public double getTa() {
      ta = LimelightUtils.getTA("");
      return ta; 
    }

    public void driveToTarget(){
      drive = getTx() * kP; 
      turn = getTy() * kP; 
      swerveDriveSubsystem.drive(drive, turn, steer, true, true);
    }
   

    public void displayData(){
      SmartDashboard.putNumber("LimelightX", getTx());
      SmartDashboard.putNumber("LimelightY", getTy());
      SmartDashboard.putNumber("LimelightArea", getTa());
    }

    @Override
    public void periodic(){
      displayData();
    }

}
