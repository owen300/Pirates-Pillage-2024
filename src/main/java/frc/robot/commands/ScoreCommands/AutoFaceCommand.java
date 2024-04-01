package frc.robot.commands.ScoreCommands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.BlinkinSubsystem;
import frc.robot.subsystems.SmartSwerveDriveSubsystem;

public class AutoFaceCommand extends Command {

   
    private final SmartSwerveDriveSubsystem swerveDriveSubsystem;
    
    public AutoFaceCommand(SmartSwerveDriveSubsystem swerveDriveSubsystem){
        this.swerveDriveSubsystem = swerveDriveSubsystem;
        //addRequirements(swerveDriveSubsystem);
    }


    @Override 
    public void execute(){
        swerveDriveSubsystem.enableAutoFace(true);
    }

    @Override
    public void end(boolean interrupted){

        swerveDriveSubsystem.enableAutoFace(false);
        BlinkinSubsystem.green();
    }

}

