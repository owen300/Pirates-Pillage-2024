package frc.robot.commands.AutoCommands;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.path.PathConstraints;
import com.pathplanner.lib.path.PathPlannerPath;
import com.pathplanner.lib.path.PathPlannerTrajectory;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class AutoCommandHolder extends Command{
    

// public SequentialCommandGroup auto1(AutoBuilder autoBuilder){
//        PathPlannerPath path = PathPlannerPath.fromPathFile("TESTPATH1");
//          return new SequentialCommandGroup(
//             new MoveWrist(wrist, 0), 
//             new MoveArm(shoulder, 31.4), 
//             new moveExtension(extension, -56.8), 
//             new MoveWrist(wrist, -.7), 
//             new Intake(intake, 0.5, true), 
//             new MoveWrist(wrist, 0),  
//             new moveExtension(extension, 0), 
//             new MoveArm(shoulder, 0), 
//             new MoveWrist(wrist, -0.2),  
//             new Intake(intake, 0, false), 
//             AutoBuilder.followPath(path));
//     }



}
