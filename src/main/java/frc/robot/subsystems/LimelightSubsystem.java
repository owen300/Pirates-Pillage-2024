package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.ScoreCommandHolderConstants;
import frc.robot.Robot;
import frc.robot.SmartAimLookup;
import frc.utils.LimelightUtils;

public class LimelightSubsystem extends SubsystemBase {

  private final String limelightName;

  public LimelightSubsystem(String limelightName) {
    this.limelightName = limelightName;
  }

  public double getTX() {
    boolean doRejectUpdate=false;
    LimelightUtils.SetRobotOrientation("limelight", Robot.getInstance().m_robotContainer.swerveDriveSubsystem.m_odometry.getPoseMeters().getRotation().getDegrees(), 0.0, 0.0, 0.0, 0.0, 0.0);
    LimelightUtils.PoseEstimate mt2 = LimelightUtils.getBotPoseEstimate_wpiBlue_MegaTag2("limelight");
    if(mt2.tagCount == 0)
    {
      doRejectUpdate = true;
    
    return 999999999; 
    }
    return Math.tan(Math.abs((-0.0381)-mt2.pose.getX())/(mt2.pose.getY()-5.547868)); 
  }

  public double getTY() {
    boolean doRejectUpdate=false;
    LimelightUtils.SetRobotOrientation("limelight", Robot.getInstance().m_robotContainer.swerveDriveSubsystem.m_odometry.getPoseMeters().getRotation().getDegrees(), 0.0, 0.0, 0.0, 0.0, 0.0);
    LimelightUtils.PoseEstimate mt2 = LimelightUtils.getBotPoseEstimate_wpiBlue_MegaTag2("limelight");
    if(mt2.tagCount == 0)
    {
      doRejectUpdate = true;
    
    return 999999999; 
    }
    return Math.sqrt(((Math.abs((-0.0381)-mt2.pose.getX())) * (Math.abs((-0.0381)-mt2.pose.getX()))) + ((Math.abs((5.547868)-mt2.pose.getY())) * (Math.abs((5.547868)-mt2.pose.getY()))));
  }

  public boolean getTV() {
    LimelightUtils.SetRobotOrientation("limelight", Robot.getInstance().m_robotContainer.swerveDriveSubsystem.m_odometry.getPoseMeters().getRotation().getDegrees(), 0.0, 0.0, 0.0, 0.0, 0.0);
    LimelightUtils.PoseEstimate mt2 = LimelightUtils.getBotPoseEstimate_wpiBlue_MegaTag2("limelight");
    if(mt2.tagCount == 0)
    {
      return false;
    }
    return true;
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

  public Double getAutoAimEncoderTarget(){
   
    if (!getTV()) return ScoreCommandHolderConstants.kSpeakerSetpoint;
    double dx =(getTY());
    Double targetAngle = SmartAimLookup.getAngle(dx); // Make sure that the lookup table has been populated before this runs
    if (targetAngle == null) return null;

    SmartDashboard.putNumber("LimelightDistanceX", dx);
   
    double encoderTarget = targetAngle + Constants.ScoreCommandHolderConstants.kAutoAimSetpoint;
    encoderTarget = Math.min(encoderTarget, 0.0);
    return encoderTarget;
  }

}
