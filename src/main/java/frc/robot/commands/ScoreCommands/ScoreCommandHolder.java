package frc.robot.commands.ScoreCommands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;


public class ScoreCommandHolder extends Command {

    

    public ScoreCommandHolder(){}

    
    public SequentialCommandGroup getGround() {
         return new SequentialCommandGroup();
    }
   


}