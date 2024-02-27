package frc.robot.subsystems;

import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.filter.LinearFilter;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.SubsystemConstants;


public class EndEffectorSubsystem extends SubsystemBase {
    
    private final CANSparkMax intakeMotor;
    public boolean intakeMotorInverted; 
    public double intakeMotorSpeed; 
    public static double filteredCurrentIntake;
    public LinearFilter filterIntake;


    private final CANSparkMax shootLead;
    private final CANSparkMax shootFollow;
    public static double filteredCurrentShoot;
    public LinearFilter filterShoot;
    

    private final CANSparkMax liftLeadLeft;
    private final CANSparkMax liftFollowLeft;

    private final CANSparkMax liftLeadRight;
    private final CANSparkMax liftFollowRight;

    PIDController liftLeadLeftPIDController; 
    PIDController liftLeadRightPIDController; 

    DutyCycleEncoder liftEncoder;
    double liftEncoderPosition; 
    double liftEncoderSetpoint; 
   

    public EndEffectorSubsystem(){
      intakeMotor = new CANSparkMax(SubsystemConstants.kIntakeMotorCANID, MotorType.kBrushless);
      intakeMotorSpeed = 0; 
      intakeMotorInverted = false; 
      filteredCurrentIntake = 0; 
      filterIntake = LinearFilter.movingAverage(SubsystemConstants.kIntakeSampleSize); 

      shootLead = new CANSparkMax(SubsystemConstants.kShootLead, MotorType.kBrushless);
      shootFollow =  new CANSparkMax(SubsystemConstants.kShootFollow, MotorType.kBrushless);
      shootFollow.follow(shootLead, false);
      shootLead.setIdleMode(IdleMode.kCoast);
      shootFollow.setIdleMode(IdleMode.kCoast); 
      filterShoot = LinearFilter.movingAverage(SubsystemConstants.kShootSampleSize); 

      liftLeadLeft = new CANSparkMax(SubsystemConstants.kLiftLeadLeft, MotorType.kBrushless);
      liftLeadLeft.setInverted(false);
      liftFollowLeft =  new CANSparkMax(SubsystemConstants.kLiftFollowLeft, MotorType.kBrushless);
      liftFollowLeft.follow(liftLeadLeft, false);
      liftLeadLeft.setIdleMode(IdleMode.kCoast);
      liftFollowLeft.setIdleMode(IdleMode.kCoast);
      liftLeadLeftPIDController = new PIDController(SubsystemConstants.kLiftLeadLeftP, SubsystemConstants.kLiftLeadLeftI, SubsystemConstants.kLiftLeadLeftD);
      liftLeadLeftPIDController.setTolerance(5, 10);
      liftLeadLeftPIDController.setSetpoint(0);

      liftLeadRight = new CANSparkMax(SubsystemConstants.kLiftLeadRight, MotorType.kBrushless);
      liftLeadRight.setInverted(true);
      liftFollowRight=  new CANSparkMax(SubsystemConstants.kLiftFollowRight, MotorType.kBrushless);
      liftFollowRight.follow(liftLeadRight, true);
      liftLeadRight.setIdleMode(IdleMode.kCoast);
      liftFollowRight.setIdleMode(IdleMode.kCoast);
      liftLeadRightPIDController = new PIDController(SubsystemConstants.kLiftLeadRightP, SubsystemConstants.kLiftLeadRightI, SubsystemConstants.kLiftLeadRightD);
      liftLeadRightPIDController.setTolerance(5, 10);
      liftLeadRightPIDController.setSetpoint(0);
     
      liftEncoder = new DutyCycleEncoder(SubsystemConstants.kLiftEncoderChannel); 
      liftEncoderPosition = liftEncoder.getDistance();

      shootLead.burnFlash(); 
      shootFollow.burnFlash(); 
      liftLeadLeft.burnFlash();
      liftFollowLeft.burnFlash();
      liftLeadRight.burnFlash();
      liftFollowRight.burnFlash();
  }

  //INTAKE METHODS
  public void setIntakeSpeedDirection(double intakeMotorSpeed, boolean intakeMotorInverted){
    this.intakeMotorSpeed = intakeMotorSpeed; 
    this.intakeMotorInverted = intakeMotorInverted;
    intakeMotor.set(intakeMotorSpeed);
    intakeMotor.setInverted(intakeMotorInverted);
  }

  //SHOOT METHODS
  public void shootLeadMotor(double speed){
    shootLead.set(speed);
  }

  //LIFT METHODS
  public double getLiftDistance(){
    liftEncoderPosition = liftEncoder.getDistance();
    SmartDashboard.putNumber("LiftCurDist", liftEncoderPosition);
    return liftEncoderPosition;
  }
   
  public void resetliftEncoder(){
    liftEncoder.reset();
  }

  public void lift(double pose) { 
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
   
   SmartDashboard.putNumber("Lift Back Output", outputLift);
   SmartDashboard.putNumber("Lift Back Setpoint", liftEncoderSetpoint);
  }


  @Override
  public void periodic(){
    filteredCurrentIntake = filterIntake.calculate(intakeMotor.getOutputCurrent());
    System.out.println("Filtered Current: " + filteredCurrentIntake);
    filteredCurrentShoot = filterShoot.calculate(shootLead.getOutputCurrent());

    calculateLift();
    getLiftDistance();

   }

}
