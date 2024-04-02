package frc.robot.commands.ScoreCommands;

import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;

public class ControllerRumbleCommand extends Command {

   
    private final XboxController controller;
    
    public ControllerRumbleCommand(XboxController controller){
        this.controller = controller;
    }


    @Override 
    public void execute(){
        controller.setRumble(RumbleType.kBothRumble, 1);
    }

    @Override
    public void end(boolean interrupted){
        controller.setRumble(RumbleType.kBothRumble, 0);
    }

}

