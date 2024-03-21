package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.SmartAimLookup;

public class SmartLimelightSubsystem extends LimelightSubsystem {

  public static double getEncoderTarget(){

    final double DEFAULT_ANGLE = 75;
    final double MIN_ENCODER_VALUE = 0;
    final double MAX_ENCODER_VALUE = 0.8;

    // final double ANGLE_OFFSET = 10; 
    // final double ANGLE_SCALE = 1; 

    double tv = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0);
    SmartDashboard.putNumber("TV", tv);

    double ty = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0.0);
   
    Double targetAngle = SmartAimLookup.getAngle(ty); // Make sure that the loopup table has been populated before this runs
    if (targetAngle == null) targetAngle = Math.toRadians(DEFAULT_ANGLE);
   
    double encoderTarget = (targetAngle / (Math.PI / 2.0)) * (MAX_ENCODER_VALUE - MIN_ENCODER_VALUE) + MIN_ENCODER_VALUE;
    encoderTarget = Math.max(Math.min(encoderTarget, MAX_ENCODER_VALUE), MIN_ENCODER_VALUE);
    return -encoderTarget; 

  }

}
