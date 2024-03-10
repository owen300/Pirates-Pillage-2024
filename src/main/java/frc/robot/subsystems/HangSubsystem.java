package frc.robot.subsystems;

import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.SubsystemConstants;


public class HangSubsystem extends SubsystemBase {

    private final CANSparkMax hangMotor;
    private final RelativeEncoder hangEncoder; 

    PIDController hangPIDController; 

    RelativeEncoder liftEncoder;
    double hangEncoderPosition; 
    double hangEncoderSetpoint; 

    public HangSubsystem(){
        hangMotor = new CANSparkMax(SubsystemConstants.kHangMotorCANID, MotorType.kBrushless);
        hangEncoder = hangMotor.getEncoder();
        
        hangPIDController = new PIDController(SubsystemConstants.kHangP, SubsystemConstants.kHangI, SubsystemConstants.kHangD);
        hangPIDController.setTolerance(5, 10);

        hangMotor.setIdleMode(IdleMode.kBrake); 
        hangEncoder.setPosition(0);
        hangMotor.burnFlash(); 
    }


    public void calculate(){
        double output;
        if(hangEncoderSetpoint > hangEncoder.getPosition())
        output = MathUtil.clamp(hangPIDController.calculate(hangEncoder.getPosition()),-SubsystemConstants.kHangMaxPower, SubsystemConstants.kHangMaxPower);
        else
        output = MathUtil.clamp(hangPIDController.calculate(hangEncoder.getPosition()), -SubsystemConstants.kHangMaxPower, SubsystemConstants.kHangMaxPower);
    
        hangMotor.set(output);

        SmartDashboard.putNumber("Hang Output", output);
        SmartDashboard.putNumber("Hang Setpoint", hangEncoderSetpoint);
    }


    public double getHangDistance(){
        hangEncoderPosition = liftEncoder.getPosition();
        SmartDashboard.putNumber("HangCurDist", hangEncoderPosition);
        return hangEncoderPosition;
      }

    public void resetEncoder(){
        hangEncoder.setPosition(0);    
      }

      public void hang(double pose) { 
        hangPIDController.setSetpoint(pose);
        hangEncoderSetpoint = pose;
     }



}