package frc.robot.commands.LimelightCommands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.EndEffectorSubsystem;
import frc.robot.subsystems.LimelightSubsystem;

public class LiftAimCommand extends Command {

   
    private final LimelightSubsystem limelightSubsystem;
    private final EndEffectorSubsystem liftSubsystem; 
    double startTime;
    
    public LiftAimCommand(LimelightSubsystem limelightSubsystem, EndEffectorSubsystem liftSubsystem){
        this.limelightSubsystem = limelightSubsystem; 
        this.liftSubsystem = liftSubsystem; 
        addRequirements(limelightSubsystem);
    }


    @Override 
    public void execute(){
     limelightSubsystem.setShooterAngle();
     System.out.println("executed"); 
    }

    @Override
    public void end(boolean interrupted){

        EndEffectorSubsystem.lift(limelightSubsystem.getLastSetpoint()); 
        System.out.println("ended");
    }



}

