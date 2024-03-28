package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ScoreCommandHolderConstants;
import frc.robot.SmartAimLookup;
import frc.utils.LimelightUtils;

public class LimelightSubsystem extends SubsystemBase {

  private final String limelightName;

  public LimelightSubsystem(String limelightName) {
    this.limelightName = limelightName;
  }

  public double getTX() {
    return LimelightUtils.getTX(limelightName);
  }

  public double getTY() {
    return LimelightUtils.getTY(limelightName);
  }

  public boolean getTV() {
    return LimelightUtils.getTV(limelightName);
  }

  public double getTA() {
    return LimelightUtils.getTA(limelightName);
  }

  public void displayData(){
    SmartDashboard.putNumber("LimelightX", getTX());
    SmartDashboard.putNumber("LimelightY", getTY());
    SmartDashboard.putNumber("LimelightArea", getTA());
    SmartDashboard.putBoolean("LimelightVisible", getTV());
  }

  @Override
  public void periodic(){
    displayData();
  }

  public double getAutoAimEncoderTarget(){

    final double ENCODER_AT_ANGLE_ZERO = -1.03;
   
    if (!getTV()) return ScoreCommandHolderConstants.kSpeakerSetpoint;
    Double targetAngle = SmartAimLookup.getAngle(SmartAimLookup.tyToDx(getTY())); // Make sure that the lookup table has been populated before this runs
    if (targetAngle == null) return ScoreCommandHolderConstants.kSpeakerSetpoint;
   
    double encoderTarget = targetAngle + ENCODER_AT_ANGLE_ZERO;
    encoderTarget = Math.min(encoderTarget, 0.0);
    return encoderTarget;
  }

}
