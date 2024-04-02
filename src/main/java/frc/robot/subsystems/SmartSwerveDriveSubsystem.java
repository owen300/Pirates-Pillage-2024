package frc.robot.subsystems;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import frc.robot.Constants.SubsystemConstants;

public class SmartSwerveDriveSubsystem extends SwerveDriveSubsystem {

  private LimelightSubsystem limelightSubsystem;

  private PIDController facePIDController;
  private boolean isAutoFaceEnabled = false;

  public SmartSwerveDriveSubsystem(LimelightSubsystem limelightSubsystem) {
    this.facePIDController = new PIDController(SubsystemConstants.kFaceP, SubsystemConstants.kFaceI, SubsystemConstants.kFaceD);
    this.facePIDController.setIZone(5);
    this.facePIDController.setSetpoint(0);
    this.facePIDController.setTolerance(SubsystemConstants.kFaceTolerance, 1);
    this.limelightSubsystem = limelightSubsystem;
  }

  public void enableAutoFace(boolean enable) {
    if (!isAutoFaceEnabled && enable) facePIDController.reset();
    isAutoFaceEnabled = enable;
  }

  public void drive(double xSpeed, double ySpeed, double rot, boolean fieldRelative, boolean rateLimit) {

    if (!isAutoFaceEnabled || !limelightSubsystem.getTV()) {
      super.drive(xSpeed, ySpeed, rot, fieldRelative, rateLimit);
    } else {
      double tx = limelightSubsystem.getTX();
      //double pidOut = -tx * SubsystemConstants.kFaceP;
      double pidOut = facePIDController.calculate(-tx);
      //if (tx > 0) pidOut = -pidOut; // poor man's angle wrapping
      double rotControlled = MathUtil.clamp(pidOut, -1.0, 1.0);
      super.drive(xSpeed, ySpeed, rotControlled, fieldRelative, rateLimit); // automatically face the target apriltag
    }

  }

  public boolean alignedToGoal(){
    //double tx = limelightSubsystem.getTX();
    //return -SubsystemConstants.kFaceTolerance < tx && tx < SubsystemConstants.kFaceTolerance;
    return facePIDController.atSetpoint();
  }

}
