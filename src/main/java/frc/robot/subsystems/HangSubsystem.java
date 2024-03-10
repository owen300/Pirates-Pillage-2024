package frc.robot.subsystems;

import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.SubsystemConstants;


public class HangSubsystem extends SubsystemBase {

    private final CANSparkMax hangMotor;

    PIDController hangPIDController; 

    DutyCycleEncoder hangEncoder;
    double hangEncoderPosition; 
    double hangEncoderSetpoint; 

    public HangSubsystem(){
        hangMotor = new CANSparkMax(SubsystemConstants.kHangMotorCANID, MotorType.kBrushless);
        
        hangPIDController = new PIDController(SubsystemConstants.kHangP, SubsystemConstants.kHangI, SubsystemConstants.kHangD);
        hangPIDController.setTolerance(5, 10);

        hangMotor.setIdleMode(IdleMode.kBrake); 

        hangEncoder = new DutyCycleEncoder(SubsystemConstants.kHangEncoderChannel); 
        hangEncoderPosition = hangEncoder.getAbsolutePosition();
  
        hangMotor.burnFlash(); 
    }

    public void hangTest(double speed){
        hangMotor.set(speed);
    }


    public void calculate(){
        double output;
        if(hangEncoderSetpoint > hangEncoder.getDistance())
        output = MathUtil.clamp(hangPIDController.calculate(hangEncoder.getDistance()),-SubsystemConstants.kHangMaxPower, SubsystemConstants.kHangMaxPower);
        else
        output = MathUtil.clamp(hangPIDController.calculate(hangEncoder.getDistance()), -SubsystemConstants.kHangMaxPower, SubsystemConstants.kHangMaxPower);
    
        hangMotor.set(output);

        SmartDashboard.putNumber("Hang Output", output);
        SmartDashboard.putNumber("Hang Setpoint", hangEncoderSetpoint);
    }


    public double getHangDistance(){
        hangEncoderPosition = hangEncoder.getDistance();
        SmartDashboard.putNumber("HangCurDist", hangEncoderPosition);
        return hangEncoderPosition;
      }

    public void resetHangEncoder(){
        hangEncoder.reset();
    }

      public void hang(double pose) { 
        hangPIDController.setSetpoint(pose);
        hangEncoderSetpoint = pose;
    }

    @Override
    public void periodic(){
    
        calculate();
        getHangDistance();
   }



}