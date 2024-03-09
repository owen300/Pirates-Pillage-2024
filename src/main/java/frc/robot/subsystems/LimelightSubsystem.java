package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.LimelightConstants;
import frc.utils.LimelightUtils;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class LimelightSubsystem extends SubsystemBase {

    SwerveDriveSubsystem swerveDriveSubsystem; 
    
    static double tx;
    static double ty;
    double ta;




    public LimelightSubsystem() {
      swerveDriveSubsystem = new SwerveDriveSubsystem(); 

      tx = LimelightUtils.getTX("");
      ty = LimelightUtils.getTY("");
      ta = LimelightUtils.getTA("");
    }

      public static double getTx() {
      tx = LimelightUtils.getTX("");
      return tx; 
    }

    public static double getTy() {
      ty = LimelightUtils.getTY("");
      return ty;  
    }

    public double getTa() {
      ta = LimelightUtils.getTA("");
      return ta; 
    }


    public void displayData(){
      SmartDashboard.putNumber("LimelightX", getTx());
      SmartDashboard.putNumber("LimelightY", getTy());
      SmartDashboard.putNumber("LimelightArea", getTa());
    }





    
    /**
    * This function returns whether the limelight has any valid targets (0 or 1)
    * @return Target status (0/1)
     */
    public double getTV() {
      return NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0);
    }

    /**
    * This function returns whether there is a target detected by the camera
    * @return Target status (boolean)
     */
    public boolean hasTarget() {
      return getTV() == 1.0;
    }

    /**
    * This function returns the Horizontal Offset From Crosshair To Target
    *-27 degrees to 27 degrees
    * @return Horizontal Offset From Crosshair To Target
     */
    public double getTX() {
      return NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
    }

    /**
    * This function returns the Vertical Offset From Crosshair To Target
    *-20.5 degrees to 20.5 degrees
    * @return Vertical Offset From Crosshair To Target
     */
    public double getTY() {
      return NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0);
    }

    /**
    * This function returns the Target Area
    * @return Target Area
     */
    public double getTA() {
      return NetworkTableInstance.getDefault().getTable("limelight").getEntry("ta").getDouble(0);
    }

    /**
    * This function returns the forward speed to approach the target
    * @return Forward Speed
     */
    public double getForwardSpeed() {
      if(this.hasTarget()){
        // try to drive forward until the target area reaches our desired area
        double forwardSpeed = (LimelightConstants.DESIRED_TARGET_AREA - getTA()) * LimelightConstants.SHOOTER_K;

        // don't let the robot drive too fast into the goal
        if (forwardSpeed > LimelightConstants.MAX_DRIVE)
        {
          forwardSpeed = LimelightConstants.MAX_DRIVE;
        }
        return -forwardSpeed;
      }
      return 0;
    }

    /**
    * This function returns the shooter speed
    * @return Shooter Speed
     */
    public double getShooterSpeed() {
      if(this.hasTarget()){
        // try to drive forward until the target area reaches our desired area
        double shooterSpeed = LimelightConstants.SHOOTER_F + ((LimelightConstants.DESIRED_TARGET_AREA - getTA()) * LimelightConstants.SHOOTER_K);
        return shooterSpeed;
      }
      return 1;
    }

    /**
    * This function returns theturning speed to approach the target
    * @return Turning Speed
     */
    public double getTurnSpeed(){
      if(this.hasTarget()){
      // Start with proportional steering
        double turnSpeed = getTX() * LimelightConstants.STEER_K;
        return -turnSpeed;
      }
      return 0;
    }

    public boolean alignedToGoal(){
      return ((getTX() <= 1) && (getTX() >= -1));
    }


    public static double limelightAimProportional()
    {    
      double kP = 0.035;
  
      // tx ranges from (-hfov/2) to (hfov/2) in degrees. If your target is on the rightmost edge of 
      // your limelight 3 feed, tx should return roughly 31 degrees.
      double targetingAngularVelocity = getTx() * kP;
      targetingAngularVelocity *= Constants.DriveConstants.kMaxAngularSpeed;
      targetingAngularVelocity *= -1.0;

      SmartDashboard.putNumber("targetingAngularVelocity", targetingAngularVelocity); 
      return targetingAngularVelocity;
    }


  public static double limelightRangeProportional()
  {    
    double kP = 0.1;
    double targetingForwardSpeed = getTy() * kP;
    targetingForwardSpeed *= Constants.DriveConstants.kMaxSpeedMetersPerSecond;
    targetingForwardSpeed *= -1.0;

    SmartDashboard.putNumber("targetingForwardSpeed", targetingForwardSpeed); 
    return targetingForwardSpeed;
  }




    @Override
    public void periodic(){
      displayData();
    }

}
