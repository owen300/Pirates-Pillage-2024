package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.LimelightConstants;
import frc.utils.LimelightUtils;
import edu.wpi.first.math.filter.LinearFilter;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class LimelightSubsystem extends SubsystemBase {

    SwerveDriveSubsystem swerveDriveSubsystem; 
    
    static double tx;
    static double ty;
    double ta;
    double lastSetpoint;
    static double setpoint;
    




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
      return ((getTY() <= 1) && (getTY() >= -1));
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


    //NEW CODE START
    //implementation?
  public boolean isTargetVisible() {
    NetworkTable limelightTable = NetworkTableInstance.getDefault().getTable("limelight");
        
    double targetVisible = limelightTable.getEntry("tv").getDouble(0);
        
    return targetVisible == 1;
  }

  // public double calculateDistanceToTarget() {
  //   final double TARGET_HEIGHT = 1.8; //height to get in note in speaker
  //   final double LIMELIGHT_MOUNT_ANGLE = 85; // may have to adjust
  //   final double LIMELIGHT_LENS_HEIGHT = 0.3; // good

  //   double ty = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0.0);

  //   // radians
  //   double angleToTargetRadians = Math.toRadians(LIMELIGHT_MOUNT_ANGLE + (ty * 49.7));

  //   // distance to target
  //   double distance = (TARGET_HEIGHT - LIMELIGHT_LENS_HEIGHT) / Math.tan(angleToTargetRadians);

  //   return distance;
  // }


  public void setShooterAngle() {  
    lastSetpoint = getEncoderTarget();
    EndEffectorSubsystem.lift(lastSetpoint);
    
  } 
 
  public double getLastSetpoint(){
    return lastSetpoint;
  }


  public static double getEncoderTarget(){

    final double LIMELIGHT_MOUNT_ANGLE = 75; 
    final double MIN_ENCODER_VALUE = 0; 
    final double MAX_ENCODER_VALUE = 0.8;

    // final double ANGLE_OFFSET = 10; 
    // final double ANGLE_SCALE = 1; 

    double tv = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0);
    SmartDashboard.putNumber("TV", tv);

    double ty = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0.0);
   
    double angleToTargetRadians = Math.toRadians(LIMELIGHT_MOUNT_ANGLE + (ty*49.7)); //add angle offset here radians

   
    double encoderTarget = (angleToTargetRadians / (Math.PI / 2.0)) * (MAX_ENCODER_VALUE - MIN_ENCODER_VALUE) + MIN_ENCODER_VALUE;
    encoderTarget = Math.max(Math.min(encoderTarget, MAX_ENCODER_VALUE), MIN_ENCODER_VALUE);
    return -encoderTarget; 

  }


  public static double getX(){
    LinearFilter filter = LinearFilter.movingAverage(10);
    double ty = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0.0);
    double top = 900; 
    double degRad = Math.toRadians(ty);
    double degToRadians = Math.toRadians(18);
    double base = Math.tan(degToRadians+degRad); 
    System.out.println(top/base);
    return filter.calculate(top/base); 
  }

  public static void setPosition(){
    double oneFt = 1230; // 1 ft -->
    double twoFt =1570; //2.2 ft

    //2.2ft -->
    double fourFt =2180; //4ft

    double fiveFt =2470; // 5ft

    if(getX() >= oneFt && getX()<=twoFt){
      setpoint = -0.6;
      System.out.println(setpoint);
      EndEffectorSubsystem.lift(setpoint);}
    else if(getX() >= twoFt && getX()<=fourFt){
      setpoint = -0.65;
      System.out.println(setpoint);
      EndEffectorSubsystem.lift(setpoint);}
    else if(getX() >= fourFt && getX()<=fiveFt){
      setpoint = -0.75;
      System.out.println(setpoint);
      EndEffectorSubsystem.lift(setpoint);}
    else
      setpoint = -0.4;
      System.out.println(setpoint);
      EndEffectorSubsystem.lift(setpoint);

  }

  public static double getSetpoint(){
    return setpoint; 
  }




  // public static void setShootAngle(){
  //   if(x)
  // }


  // public static void setLED(){
  //   if(EndEffectorSubsystem.getLiftDistance() < LimelightSubsystem.getEncoderTarget() && (EndEffectorSubsystem.getLiftDistance() > LimelightSubsystem.getEncoderTarget())){
  //       BlinkinSubsystem.green();
  //   } 
  //   else BlinkinSubsystem.red();
  // }



  // public void setShooterSpeed() {
  //   final double SPEED_OFFSET = 0.85; // Base speed of the shooter when at minimum distance (in RPM)
  //   final double SPEED_SCALE = 0.3; // Increase in speed per unit of distance (in RPM per meter)

  //   // desired speed based on distance
  //   double speed = SPEED_OFFSET + (SPEED_SCALE * calculateDistanceToTarget());

  //   // ensure within limits
  //   double constrainedSpeed = Math.max(Math.min(speed, 1), 0.85);

  //   // set the shooter to the desired speed ???? is this correct?????????
  //   EndEffectorSubsystem.shootLeadMotor(constrainedSpeed); //shoot speed
  // }

  //how should i call all of this?? 
  //Goal: have lift auto adjust angle and while driving to ensure it is always at an angle to shoot
  //then when press shoot button the speed should be whatever is calculated at that distance




    @Override
    public void periodic(){
      displayData();
    }

}
