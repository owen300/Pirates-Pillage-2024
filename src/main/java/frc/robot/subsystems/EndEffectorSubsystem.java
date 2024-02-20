package frc.robot.subsystems;

import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;


public class EndEffectorSubsystem extends SubsystemBase {
    
    private final CANSparkMax intakeMotor;

    private final CANSparkMax shootLead;
    private final CANSparkMax shootFollow;

    PIDController shootLeadPIDController; 
    double shootLeadCurrentDistance;
    RelativeEncoder shootLeadEncoder;
    double shootLeadSetpoint; 

    private final CANSparkMax liftLeadBack;
    private final CANSparkMax liftFollowBack;
    private final CANSparkMax liftLeadFront;
    private final CANSparkMax liftFollowFront;


  


    public EndEffectorSubsystem(){

      intakeMotor = new CANSparkMax(Constants.SubsystemConstants.kIntakeMotorCANID, MotorType.kBrushless);

      shootLead = new CANSparkMax(Constants.SubsystemConstants.kShootLeadCANID, MotorType.kBrushless);
      shootFollow =  new CANSparkMax(Constants.SubsystemConstants.kShootFollowCANID, MotorType.kBrushless);
      shootFollow.follow(shootLead, false);
      shootLead.setIdleMode(IdleMode.kCoast);
      shootFollow.setIdleMode(IdleMode.kCoast);
      shootLeadPIDController = new PIDController(Constants.SubsystemConstants.kShootLeadP, Constants.SubsystemConstants.kShootLeadI, Constants.SubsystemConstants.kShootLeadD);
      shootLeadPIDController.setTolerance(5, 10);
      shootLeadPIDController.setSetpoint(0);
      shootLeadEncoder = shootLead.getEncoder();
      shootLeadEncoder.setPosition(0);
      shootLeadSetpoint = 0; 

      




      
      shootLead.burnFlash(); 
      shootFollow.burnFlash(); 
  }

  public double getShootLeadDistance(){
    shootLeadCurrentDistance = shootLeadEncoder.getPosition();
    SmartDashboard.putNumber("ShootCurDist", shootLeadCurrentDistance);
    return shootLeadCurrentDistance;
  }
   
  public void resetShootLeadEncoder(){
    shootLeadEncoder.setPosition(0);
  }

  public void shootLeadMotorZero(){
    shootLead.set(0);
    shootLeadSetpoint = 0; 
  }
  
  public void shoot(double pose) { //amp and speaker 
    shootLeadPIDController.setSetpoint(pose);
    shootLeadSetpoint = pose;
 }

  public void calculateShoot(){
    double outputShoot;
    if(shootLeadSetpoint < shootLeadEncoder.getPosition()) 
        outputShoot = MathUtil.clamp(shootLeadPIDController.calculate(shootLeadEncoder.getPosition()), -Constants.SubsystemConstants.kShootLeadMaxPower, Constants.SubsystemConstants.kShootLeadMaxPower);
    else
        outputShoot = MathUtil.clamp(shootLeadPIDController.calculate(shootLeadEncoder.getPosition()), -Constants.SubsystemConstants.kShootLeadMaxPower, Constants.SubsystemConstants.kShootLeadMaxPower) ;
    
    shootLead.set(outputShoot);
   
   SmartDashboard.putNumber("Shoot Output", outputShoot);
   SmartDashboard.putNumber("Shoot Setpoint", shootLeadSetpoint);
  }

   @Override
  public void periodic(){
    calculateShoot();
    getShootLeadDistance(); 
   }

}
