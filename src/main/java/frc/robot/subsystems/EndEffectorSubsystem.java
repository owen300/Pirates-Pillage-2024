package frc.robot.subsystems;

import com.revrobotics.CANSparkBase.ControlType;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.filter.LinearFilter;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.SubsystemConstants;


public class EndEffectorSubsystem extends SubsystemBase {
  public static double f=0.9;
  public static double p=0; //0.001;
  public static double i=0.0;
  public static double d=0.000000;
  public PIDController shooterPID=new PIDController(p, i, d);
   public static boolean usepid=false;
    private final CANSparkMax intakeMotor;
    public boolean intakeMotorInverted; 
    public double intakeMotorSpeed; 
    public static double filteredCurrentIntake;
    public LinearFilter filterIntake;


    private static CANSparkMax shootLead;
    private static CANSparkMax shootFollow;
    public static double filteredCurrentShoot;
    public LinearFilter filterShoot;
    

    private final CANSparkMax liftLeadLeft;
    private final CANSparkMax liftFollowLeft;

    private final CANSparkMax liftLeadRight;
    private final CANSparkMax liftFollowRight;

    static PIDController liftLeadLeftPIDController; 
    PIDController liftLeadRightPIDController; 

    static DutyCycleEncoder liftEncoder;
    static double liftEncoderPosition; 
    static double liftEncoderSetpoint; 

    private final CANSparkMax hangMotor;
    DutyCycleEncoder hangEncoder; 
    double hangEncoderPosition; 
    static double hangEncoderSetpoint; 
    static PIDController hangPIDController; 

    DigitalInput sensor;

    

    public EndEffectorSubsystem(){
      shooterPID.setTolerance(30);
      intakeMotor = new CANSparkMax(SubsystemConstants.kIntakeMotorCANID, MotorType.kBrushless);
      intakeMotorSpeed = 0; 
      intakeMotorInverted = false; 
      filteredCurrentIntake = 0; 
      filterIntake = LinearFilter.movingAverage(SubsystemConstants.kIntakeSampleSize); 

      shootLead = new CANSparkMax(SubsystemConstants.kShootLead, MotorType.kBrushless);
      shootFollow =  new CANSparkMax(SubsystemConstants.kShootFollow, MotorType.kBrushless);
     
      shootLead.setIdleMode(IdleMode.kCoast);
      shootFollow.setIdleMode(IdleMode.kCoast); 
      filterShoot = LinearFilter.movingAverage(SubsystemConstants.kShootSampleSize); 

      liftLeadLeft = new CANSparkMax(SubsystemConstants.kLiftLeadLeft, MotorType.kBrushless);
      liftLeadLeft.setInverted(true);
      liftFollowLeft =  new CANSparkMax(SubsystemConstants.kLiftFollowLeft, MotorType.kBrushless);
      liftFollowLeft.follow(liftLeadLeft, true);
      liftLeadLeft.setIdleMode(IdleMode.kBrake);
      liftFollowLeft.setIdleMode(IdleMode.kBrake);
      liftLeadLeftPIDController = new PIDController(SubsystemConstants.kLiftLeadLeftP, SubsystemConstants.kLiftLeadLeftI, SubsystemConstants.kLiftLeadLeftD);
      liftLeadLeftPIDController.setTolerance(5, 10);

      liftLeadRight = new CANSparkMax(SubsystemConstants.kLiftLeadRight, MotorType.kBrushless);
      liftLeadRight.setInverted(false);
      liftFollowRight=  new CANSparkMax(SubsystemConstants.kLiftFollowRight, MotorType.kBrushless);
      liftFollowRight.follow(liftLeadRight, false);
      liftLeadRight.setIdleMode(IdleMode.kBrake);
      liftFollowRight.setIdleMode(IdleMode.kBrake);
      liftLeadRightPIDController = new PIDController(SubsystemConstants.kLiftLeadRightP, SubsystemConstants.kLiftLeadRightI, SubsystemConstants.kLiftLeadRightD);
      liftLeadRightPIDController.setTolerance(5, 10);
      lift(Constants.SubsystemConstants.kLiftInitializedSetpoint);


      liftEncoder = new DutyCycleEncoder(SubsystemConstants.kLiftEncoderChannel); 

      liftEncoder.setDistancePerRotation(Math.toRadians(360));
      liftEncoderPosition = liftEncoder.getAbsolutePosition();

      hangMotor = new CANSparkMax(SubsystemConstants.kHangMotorCANID, MotorType.kBrushless); 
      hangMotor.setIdleMode(IdleMode.kBrake);

      hangEncoder = new DutyCycleEncoder(SubsystemConstants.kHangEncoderChannel); 
      hangEncoderPosition = hangEncoder.getAbsolutePosition();

      hangPIDController = new PIDController(SubsystemConstants.kHangP, SubsystemConstants.kHangI, SubsystemConstants.kHangD);

      sensor = new DigitalInput(SubsystemConstants.kSensorInput); 

      shootLead.burnFlash();  
      shootFollow.burnFlash(); 
      liftLeadLeft.burnFlash();
      liftFollowLeft.burnFlash();
      liftLeadRight.burnFlash();
      liftFollowRight.burnFlash();

  }
  public static boolean lastusepid=true;
  private static double[] pid={p,i,d};
  public void shooterpidmode(){
    SmartDashboard.putNumber("Target speed",5392.2);
    SmartDashboard.putNumberArray("pid", pid);
    double[] newpids=SmartDashboard.getNumberArray("pid", pid);
    if (!newpids.equals(pid)) {
        pid=newpids;
        shooterPID.setP(pid[0]);
        shooterPID.setI(pid[1]);
        shooterPID.setD(pid[2]);
    }
    SmartDashboard.putNumber("f",f);
    double newF = SmartDashboard.getNumber("f",f);
    if (newF != f) {
        f=newF;
    }
    SmartDashboard.putNumber("shooter current speed",shootLead.getEncoder().getVelocity());
    if(usepid&&(lastusepid != usepid)){
      shooterPID.setSetpoint(5392.2);
      shootLeadMotor(shooterPID.calculate(shootLead.getEncoder().getVelocity())+f);
      shootFollowMotor(shooterPID.calculate(shootFollow.getEncoder().getVelocity())+f);
     // shootMotors(0.95);
    }else if(usepid!=lastusepid) {
      shootMotors(0.3);
    }
    lastusepid=usepid;
   }

  public void setIntakeSpeedDirection(double intakeMotorSpeed, boolean intakeMotorInverted){
    this.intakeMotorSpeed = intakeMotorSpeed; 
    this.intakeMotorInverted = intakeMotorInverted;
    intakeMotor.set(intakeMotorSpeed);
    intakeMotor.setInverted(intakeMotorInverted);
  }

  public static void shootMotors(double speed){
    // shootFollowMotor(speed);
    // shootLeadMotor(speed);
    shootLead.getPIDController().setReference(speed, ControlType.kDutyCycle);
    shootFollow.getPIDController().setReference(speed, ControlType.kDutyCycle);
   
  }
  public static void shootLeadMotor(double speed){
    shootLead.set(speed);
  }
  public static void shootFollowMotor(double speed){
   shootFollow.set(speed);
  }

  public static double getLiftDistance(){
    liftEncoderPosition = liftEncoder.getDistance();
    SmartDashboard.putNumber("LiftCurDist", liftEncoderPosition);
    return liftEncoderPosition;
  }
   
  public void resetliftEncoder(){
    liftEncoder.reset();
  }

  public static void lift(double pose) { 
    liftLeadLeftPIDController.setSetpoint(pose);
    liftEncoderSetpoint = pose;
 }

  public void calculateLift(){
    double outputLift;
    if(liftEncoderSetpoint < liftEncoder.getDistance()) 
        outputLift = MathUtil.clamp(liftLeadLeftPIDController.calculate(liftEncoder.getDistance()), -SubsystemConstants.kLiftLeadLeftMaxPower, SubsystemConstants.kLiftLeadLeftMaxPower);
    else
        outputLift = MathUtil.clamp(liftLeadLeftPIDController.calculate(liftEncoder.getDistance()), -SubsystemConstants.kLiftLeadLeftMaxPower, SubsystemConstants.kLiftLeadLeftMaxPower) ;
    
    liftLeadLeft.set(outputLift);
    liftLeadRight.set(outputLift);
   
   SmartDashboard.putNumber("Lift Output", outputLift);
   SmartDashboard.putNumber("Lift Setpoint", liftEncoderSetpoint);
  }

  public static boolean isLiftAtTarget() {
    return liftLeadLeftPIDController.atSetpoint();
  }

  public double getPose(){
    return liftEncoder.getDistance();
  }

  public double getHangDistance(){
    hangEncoderPosition = hangEncoder.getDistance();
    SmartDashboard.putNumber("HangDist", hangEncoderPosition);
    return hangEncoderPosition; 
  }

  public void resetEncoderHang(){
    hangEncoder.reset();    
  }

  public static void moveHang(double pose){
    hangPIDController.setSetpoint(pose);
    hangEncoderSetpoint = pose;
  }

  public void calculateHang(){
    double output;
    if(hangEncoderSetpoint > hangEncoder.getDistance())
        output = MathUtil.clamp(hangPIDController.calculate(hangEncoder.getDistance()), -SubsystemConstants.kHangMaxPower, SubsystemConstants.kHangMaxPower);
    else
        output = MathUtil.clamp(hangPIDController.calculate(hangEncoder.getDistance()), -SubsystemConstants.kHangMaxPower, -SubsystemConstants.kHangMaxPower);
    
      hangMotor.set(output);

   SmartDashboard.putNumber("Hang Output", output);
   SmartDashboard.putNumber("Hang Setpoint", hangEncoderSetpoint);
  }

  public boolean getSensorInput(){
      return sensor.get();
  }


  @Override
  public void periodic(){
    // filteredCurrentIntake = filterIntake.calculate(intakeMotor.getOutputCurrent());
    // filteredCurrentShoot = filterShoot.calculate(shootLead.getOutputCurrent());
    getSensorInput();
   
    calculateLift();
    getLiftDistance();
    calculateHang();
    getHangDistance();
    shooterpidmode();

  }

}