package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class LimelightSubsystem {
    
    NetworkTable table; 
    NetworkTableEntry tx;
    NetworkTableEntry ty;
    NetworkTableEntry ta; 

    public LimelightSubsystem() {
        table = NetworkTableInstance.getDefault().getTable("limelight");
        tx = table.getEntry("tx");
        ty = table.getEntry("ty");
        ta = table.getEntry("ta");
    }

      public double getTx() {
        return tx.getDouble(0.0);
    }

    public double getTy() {
        return ty.getDouble(0.0);
    }

    public double getTa() {
        return ta.getDouble(0.0);
    }

   public void displayData(){
    SmartDashboard.putNumber("LimelightX", getTx());
    SmartDashboard.putNumber("LimelightY", getTy());
    SmartDashboard.putNumber("LimelightArea", getTa());
   }





}
