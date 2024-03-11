package frc.robot.subsystems;

import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.BlinkinConstants;

public class BlinkinSubsystem extends SubsystemBase {
    private static Spark m_led = new Spark(BlinkinConstants.kBlinkinPort);

    
    public BlinkinSubsystem() {
        red();
    }
    
    @Override
    public void periodic() {

    }
     
    /**
     * @param value color value, see Constants
     */
    public void color(double value) {
        m_led.set(value);
    }

    public void red(){
        color(BlinkinConstants.kRed);
    }

    public void orange(){
        color(BlinkinConstants.kOrange);
    }

    public void yellow(){
        color(BlinkinConstants.kYellow);
    }

    public void green(){
        color(BlinkinConstants.kGreen);
    }

    public void blue(){
        color(BlinkinConstants.kBlue);
    }

    public void violet(){
        color(BlinkinConstants.kViolet);
    }

    public void white(){
        color(BlinkinConstants.kWhite);
    }

    public void off(){
        color(BlinkinConstants.kBlack);
    }

}