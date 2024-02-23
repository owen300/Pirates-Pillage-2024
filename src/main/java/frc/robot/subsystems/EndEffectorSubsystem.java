package frc.robot.subsystems;

import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.filter.LinearFilter;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.SubsystemConstants;


public class EndEffectorSubsystem extends SubsystemBase {
    
    private final CANSparkMax intakeMotor;
    public boolean intakeMotorInverted; 
    public double intakeMotorSpeed; 
    public static double filteredCurrentIntake;
    public LinearFilter filter;

    private final CANSparkMax shootLead;
    private final CANSparkMax shootFollow;

    private final CANSparkMax liftLeadLeft;
    private final CANSparkMax liftFollowLeft;

    PIDController liftLeadLeftPIDController; 
    double liftLeadLeftCurrentDistance;
    RelativeEncoder liftLeadLeftEncoder;
    double liftLeadLeftSetpoint; 

    private final CANSparkMax liftLeadRight;
    private final CANSparkMax liftFollowRight;
    
    PIDController liftLeadRightPIDController; 
    double liftLeadRightCurrentDistance;
    RelativeEncoder liftLeadRightEncoder;
    double liftLeadRightSetpoint; 


    public EndEffectorSubsystem(){
      intakeMotor = new CANSparkMax(SubsystemConstants.kIntakeMotorCANID, MotorType.kBrushless);
      intakeMotorSpeed = 0; 
      intakeMotorInverted = false; 
      filteredCurrentIntake = 0; 
      filter= LinearFilter.movingAverage(SubsystemConstants.kSampleSize); 

      shootLead = new CANSparkMax(SubsystemConstants.kShootLead, MotorType.kBrushless);
      shootFollow =  new CANSparkMax(SubsystemConstants.kShootFollow, MotorType.kBrushless);
      shootFollow.follow(shootLead, false);
      shootLead.setIdleMode(IdleMode.kCoast);
      shootFollow.setIdleMode(IdleMode.kCoast); 

      liftLeadLeft = new CANSparkMax(SubsystemConstants.kLiftLeadLeft, MotorType.kBrushless);
      liftFollowLeft =  new CANSparkMax(SubsystemConstants.kLiftFollowLeft, MotorType.kBrushless);
      liftFollowLeft.follow(liftLeadLeft, false);
      liftLeadLeft.setIdleMode(IdleMode.kCoast);
      liftFollowLeft.setIdleMode(IdleMode.kCoast);
      liftLeadLeftPIDController = new PIDController(SubsystemConstants.kLiftLeadLeftP, SubsystemConstants.kLiftLeadLeftI, SubsystemConstants.kLiftLeadLeftD);
      liftLeadLeftPIDController.setTolerance(5, 10);
      liftLeadLeftPIDController.setSetpoint(0);
      liftLeadLeftEncoder = shootLead.getEncoder();
      liftLeadLeftEncoder.setPosition(0);
      liftLeadLeftSetpoint = 0; 

      liftLeadRight = new CANSparkMax(SubsystemConstants.kLiftLeadRight, MotorType.kBrushless);
      liftFollowRight=  new CANSparkMax(SubsystemConstants.kLiftFollowRight, MotorType.kBrushless);
      liftFollowRight.follow(liftLeadRight, false);
      liftLeadRight.setIdleMode(IdleMode.kCoast);
      liftFollowRight.setIdleMode(IdleMode.kCoast);
      liftLeadRightPIDController = new PIDController(SubsystemConstants.kLiftLeadRightP, SubsystemConstants.kLiftLeadRightI, SubsystemConstants.kLiftLeadRightD);
      liftLeadRightPIDController.setTolerance(5, 10);
      liftLeadRightPIDController.setSetpoint(0);
      liftLeadRightEncoder = shootLead.getEncoder();
      liftLeadRightEncoder.setPosition(0);
      liftLeadRightSetpoint = 0; 


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

  //LIFT BACK METHODS
  public double getLiftLeadBackDistance(){
    liftLeadLeftCurrentDistance = liftLeadLeftEncoder.getPosition();
    SmartDashboard.putNumber("LiftBackCurDist", liftLeadLeftCurrentDistance);
    return liftLeadLeftCurrentDistance;
  }
   
  public void resetliftLeadBackEncoder(){
    liftLeadLeftEncoder.setPosition(0);
  }

  public void liftLeadBackMotorZero(){
    liftLeadLeft.set(0);
    liftLeadLeftSetpoint = 0; 
  }
  
//   public void liftBack(double pose) { 
//     liftLeadLeftPIDController.setSetpoint(pose);
//     liftLeadLeftSetpoint = pose;
//  }

  public void calculateLiftBack(){
    double outputLiftLeft;
    if(liftLeadLeftSetpoint < liftLeadLeftEncoder.getPosition()) 
        outputLiftLeft = MathUtil.clamp(liftLeadLeftPIDController.calculate(liftLeadLeftEncoder.getPosition()), -SubsystemConstants.kLiftLeadLeftMaxPower, SubsystemConstants.kLiftLeadLeftMaxPower);
    else
        outputLiftLeft = MathUtil.clamp(liftLeadLeftPIDController.calculate(liftLeadLeftEncoder.getPosition()), -SubsystemConstants.kLiftLeadLeftMaxPower, SubsystemConstants.kLiftLeadLeftMaxPower) ;
    
    shootLead.set(outputLiftLeft);
   
   SmartDashboard.putNumber("Lift Back Output", outputLiftLeft);
   SmartDashboard.putNumber("Lift Back Setpoint", liftLeadLeftSetpoint);
  }

  //LIFT FRONT METHODS
  public double getLiftLeadFrontDistance(){
    liftLeadRightCurrentDistance = liftLeadRightEncoder.getPosition();
    SmartDashboard.putNumber("LiftFrontCurDist", liftLeadRightCurrentDistance);
    return liftLeadRightCurrentDistance;
  }
   
  public void resetliftLeadFrontEncoder(){
    liftLeadRightEncoder.setPosition(0);
  }

  public void liftLeadFrontMotorZero(){
    liftLeadRight.set(0);
    liftLeadRightSetpoint = 0; 
  }
  
//   public void liftFront(double pose) { 
//     liftLeadRightPIDController.setSetpoint(pose);
//     liftLeadRightSetpoint = pose;
//  }

  public void calculateLiftFront(){
    double outputLiftRight;
    if(liftLeadRightSetpoint < liftLeadRightEncoder.getPosition()) 
        outputLiftRight = MathUtil.clamp(liftLeadRightPIDController.calculate(liftLeadRightEncoder.getPosition()), -SubsystemConstants.kLiftLeadRightMaxPower, SubsystemConstants.kLiftLeadRightMaxPower);
    else
        outputLiftRight = MathUtil.clamp(liftLeadRightPIDController.calculate(liftLeadRightEncoder.getPosition()), -SubsystemConstants.kLiftLeadRightMaxPower, SubsystemConstants.kLiftLeadRightMaxPower) ;
    
    shootLead.set(outputLiftRight);
   
   SmartDashboard.putNumber("Lift Front Output", outputLiftRight);
   SmartDashboard.putNumber("Lift Front Setpoint", liftLeadRightSetpoint);
  }


  @Override
  public void periodic(){
    filteredCurrentIntake = filter.calculate(intakeMotor.getOutputCurrent());

    // calculateLiftBack();
    getLiftLeadBackDistance();

    // calculateLiftFront();
    getLiftLeadFrontDistance();

   }

}
