// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class LimelightSubsystem extends SubsystemBase {

    SwerveDriveSubsystem swerveDriveSubsystem; 
    
    NetworkTable table; 
    NetworkTableEntry tx;
    NetworkTableEntry ty;
    NetworkTableEntry ta; 

    private boolean hasTarget;
    private double drive;
    private double steer;
    private double kP; 



    public LimelightSubsystem() {
        swerveDriveSubsystem = new SwerveDriveSubsystem(); 

        table = NetworkTableInstance.getDefault().getTable("limelight");
        tx = table.getEntry("tx");
        ty = table.getEntry("ty");
        ta = table.getEntry("ta");

        hasTarget = false; 
        drive = 0.0;
        steer = 0.0;
    }

      public double getTx() {
        return tx.getDouble(0.0);
    }

    public double getTy() {
        return ty.getDouble(0.0);
    }

    public double getTa() {
        return ta.getDouble(0.0);
    }

   public void displayData(){
    SmartDashboard.putNumber("LimelightX", getTx());
    SmartDashboard.putNumber("LimelightY", getTy());
    SmartDashboard.putNumber("LimelightArea", getTa());
   }
   
   public void driveToTarget() {
    steer = getTx() * kP;
    
    swerveDriveSubsystem.drive(0.5, 0.25, steer, true, true); 
}
   
  @Override
  public void periodic() {
    displayData();
  }

  







}
