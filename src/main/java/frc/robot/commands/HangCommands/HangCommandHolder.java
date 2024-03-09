package frc.robot.commands.HangCommands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.HangSubsystem;

public class HangCommandHolder {
    
  private final HangSubsystem hangSubsystem;
    

    public HangCommandHolder(HangSubsystem hangSubsystem){
        this.hangSubsystem = hangSubsystem; 
    }


    public Command hang(double speed){
        return new HangCommand(hangSubsystem, speed); 
    }

}
