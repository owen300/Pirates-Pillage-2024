package frc.robot.commands.LimelightCommands;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.LimelightSubsystem;
import frc.robot.subsystems.SwerveDriveSubsystem;

public class LimelightCommand extends Command {

    private final SwerveDriveSubsystem swerveDrive;
    private final LimelightSubsystem limelight; 
    
    PIDController m_pidController; 
    double kP = 0.1; 
    double kI = 0.00; 
    double kD = 0.001; 

    double currentX;
    double desiredX; 
    double maxPower = 0.5; 

    public LimelightCommand(SwerveDriveSubsystem swerveDrive, LimelightSubsystem limelight) {
        this.swerveDrive = swerveDrive;
        this.limelight = limelight; 
        addRequirements(swerveDrive);
    }

    @Override
    public void initialize() {
        desiredX = 0; //change to desired X
    }

    @Override
    public void execute() {
        currentX = limelight.getTx(); 
        double output; 
        if(desiredX < currentX)
            output = MathUtil.clamp(m_pidController.calculate(currentX), -maxPower, maxPower);
        else
            output = 0;
        
        swerveDrive.drive(output, 0, 0, true, true);
    }

    @Override
    public boolean isFinished() {
        return desiredX - currentX < 3;  //tolerance likely adjust 
    }
}

