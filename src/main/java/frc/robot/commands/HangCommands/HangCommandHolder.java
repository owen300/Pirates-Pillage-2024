package frc.robot.commands.HangCommands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.commands.ScoreCommands.LiftCommand;
import frc.robot.subsystems.EndEffectorSubsystem;
import frc.robot.subsystems.HangSubsystem;

public class HangCommandHolder {
    
  private final HangSubsystem hangSubsystem;
  private final EndEffectorSubsystem endEffectorSubsystem; 
    

    public HangCommandHolder(HangSubsystem hangSubsystem, EndEffectorSubsystem endEffectorSubsystem){
        this.hangSubsystem = hangSubsystem; 
        this.endEffectorSubsystem = endEffectorSubsystem;
    }


    public SequentialCommandGroup hang(){
        return new SequentialCommandGroup(
            new LiftCommand(endEffectorSubsystem, Constants.SubsystemConstants.kLiftInitializedSetpoint),
            new HangCommand(hangSubsystem, Constants.HangCommandHolderConstants.kHangUpSetpoint), 
            new HangCommand(hangSubsystem,  Constants.HangCommandHolderConstants.kHangDownSetpoint)); //down
    }

    public Command hangTest(double speed){
        return new hang(hangSubsystem, speed); 
    }

}
