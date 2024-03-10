package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.LimelightConstants;
import frc.utils.LimelightUtils;
import edu.wpi.first.networktables.NetworkTable;
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


  //   public static double limelightAimProportional()
  //   {    
  //     double kP = 0.035;
  
  //     // tx ranges from (-hfov/2) to (hfov/2) in degrees. If your target is on the rightmost edge of 
  //     // your limelight 3 feed, tx should return roughly 31 degrees.
  //     double targetingAngularVelocity = getTx() * kP;
  //     targetingAngularVelocity *= Constants.DriveConstants.kMaxAngularSpeed;
  //     targetingAngularVelocity *= -1.0;

  //     SmartDashboard.putNumber("targetingAngularVelocity", targetingAngularVelocity); 
  //     return targetingAngularVelocity;
  //   }


  // public static double limelightRangeProportional()
  // {    
  //   double kP = 0.1;
  //   double targetingForwardSpeed = getTy() * kP;
  //   targetingForwardSpeed *= Constants.DriveConstants.kMaxSpeedMetersPerSecond;
  //   targetingForwardSpeed *= -1.0;

  //   SmartDashboard.putNumber("targetingForwardSpeed", targetingForwardSpeed); 
  //   return targetingForwardSpeed;
  // }




  public boolean isTargetVisible() {
    NetworkTable limelightTable = NetworkTableInstance.getDefault().getTable("limelight");
        
    double targetVisible = limelightTable.getEntry("tv").getDouble(0);
        
    return targetVisible == 1;
  }

  public double calculateDistanceToTarget() {
    final double TARGET_HEIGHT = 1.8; //height to get in note
    final double LIMELIGHT_MOUNT_ANGLE = 85; // may have to adjust
    final double LIMELIGHT_LENS_HEIGHT = 0.3; // good

    // Get the vertical angle to the target from Limelight
    double ty = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0.0);

    // Calculate the angle in radians
    double angleToTargetRadians = Math.toRadians(LIMELIGHT_MOUNT_ANGLE + ty);

    // Calculate the distance to the target
    double distance = (TARGET_HEIGHT - LIMELIGHT_LENS_HEIGHT) / Math.tan(angleToTargetRadians);

    return distance;
  }


  public void setShooterAngle() {
    final double ANGLE_OFFSET = 15.0; // Base angle of the shooter when at minimum distance
    final double ANGLE_SCALE = 0.1; // Change in angle per unit of distance

    // Calculate the desired angle based on the distance
    double angle = ANGLE_OFFSET + (ANGLE_SCALE * calculateDistanceToTarget());

    // Ensure the calculated angle is within the physical limits of the shooter
    double constrainedAngle = Math.max(Math.min(angle, 45), 15);

    //change the angle to be encoder val need to get the angle to be equal to an encoder value distance
    

    // Code to set the shooter to the desired angle
   EndEffectorSubsystem.lift(setpoint); //lift setpoint? figure out what to do here????? change angle to be encoder values
  } 


  public void setShooterSpeed() {
    final double SPEED_OFFSET = 0.85; // Base speed of the shooter when at minimum distance (in RPM)
    final double SPEED_SCALE = 0.3; // Increase in speed per unit of distance (in RPM per meter)

    // Calculate the desired speed based on the distance
    double speed = SPEED_OFFSET + (SPEED_SCALE * calculateDistanceToTarget());

    // Ensure the calculated speed is within the physical limits of the shooter
    double constrainedSpeed = Math.max(Math.min(speed, 1), 0.85);

    // Code to set the shooter to the desired speed
    // This could involve setting the speed of a motor controller, for example:
    EndEffectorSubsystem.shootLeadMotor(constrainedSpeed); //shoot speed
  }



    @Override
    public void periodic(){
      displayData();
    }

}
