package frc.robot;


import java.util.function.BooleanSupplier;

import com.pathplanner.lib.auto.NamedCommands;
import com.pathplanner.lib.commands.PathPlannerAuto;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.ConditionalCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants.OIConstants;
import frc.robot.commands.AutoCommands.AutoCommandHolder;
import frc.robot.commands.ScoreCommands.AutoAimCommand;
import frc.robot.commands.ScoreCommands.AutoFaceCommand;
import frc.robot.commands.ScoreCommands.AutoTargetNotifyCommand;
import frc.robot.commands.ScoreCommands.LiftSetpointDown;
import frc.robot.commands.ScoreCommands.LiftSetpointUp;
import frc.robot.commands.ScoreCommands.ScoreCommandHolder;
import frc.robot.subsystems.EndEffectorSubsystem;
import frc.robot.subsystems.LimelightSubsystem;
import frc.robot.subsystems.SmartSwerveDriveSubsystem;
import frc.robot.subsystems.SwerveDriveSubsystem;
import frc.utils.LimelightUtils.LimelightTarget_Detector;


public class RobotContainer {
  boolean autoaim = false;
  boolean state=false;
  boolean laststate=true;
  BooleanSupplier autoaimsupplier=()-> autoaim;

  public static CommandXboxController driverController= new CommandXboxController(0); 
  public static CommandXboxController coDriverController= new CommandXboxController(1); 

  SendableChooser<Command> AutoChooser = new SendableChooser<>();

  //SUBSYSTEM
  EndEffectorSubsystem endEffectorSubsystem = new EndEffectorSubsystem();
  LimelightSubsystem limelightSubsystem = new LimelightSubsystem("");
  SmartSwerveDriveSubsystem swerveDriveSubsystem = new SmartSwerveDriveSubsystem(limelightSubsystem);
  //SwerveDriveSubsystem swerveDriveSubsystem = new SwerveDriveSubsystem();
  

  //COMMANDS
  ScoreCommandHolder scoreCommands = new ScoreCommandHolder(endEffectorSubsystem);
  AutoAimCommand autoAimCommand = new AutoAimCommand(limelightSubsystem);
  AutoFaceCommand autoFaceCommand = new AutoFaceCommand(swerveDriveSubsystem);
  AutoTargetNotifyCommand autoTargetNotifyCommand = new AutoTargetNotifyCommand(/* driverController.getHID() */);
 
 



  //TRIGGERS 
  Trigger yButton = driverController.y(); 
  

  Trigger dpadUpCoDriver = coDriverController.povUp();
  Trigger dpadDownCoDriver = coDriverController.povDown();
  Trigger leftBumperCoDriver = coDriverController.leftBumper(); 
  Trigger rightBumperCoDriver = coDriverController.rightBumper(); 
  Trigger rightTriggerCoDriver = coDriverController.rightTrigger(); 
  Trigger leftTriggerCoDriver = coDriverController.leftTrigger(); 
  Trigger dpadRight = coDriverController.povRight();
  Trigger dpadleft = coDriverController.povLeft();


  Trigger aCoDriverButton = coDriverController.a(); 
  Trigger bCoDriverButton = coDriverController.b(); 
  Trigger xCoDriverButton = coDriverController.x(); 
  Trigger yCoDriverButton = coDriverController.y(); 
  Trigger leftDriverTrigger = coDriverController.leftTrigger(); 
  Trigger leftDriverBumper = coDriverController.leftBumper(); 
  Trigger rightDriverTrigger = coDriverController.rightTrigger();
  Trigger rightDriverBumper = coDriverController.rightBumper();
  Trigger dpadUpDriver = coDriverController.povUp();
  Trigger dpadDownDriver = coDriverController.povDown();
  Trigger startButton = coDriverController.start();


  Trigger atAutoAimTarget = new Trigger(autoAimCommand::isAtTarget);
  Trigger atAutoFaceTarget = new Trigger(autoFaceCommand::isAtTarget);



  public RobotContainer() {
    registerNamedCommands();
    configureBindings();
     swerveDriveSubsystem.setDefaultCommand(
        new RunCommand(
            () -> swerveDriveSubsystem.drive(
                -MathUtil.applyDeadband(driverController.getLeftY(), OIConstants.kDriveDeadband),
                -MathUtil.applyDeadband(driverController.getLeftX(), OIConstants.kDriveDeadband),
                -MathUtil.applyDeadband(driverController.getRightX(), OIConstants.kDriveDeadband), // will be ignored if autoTargetCommand is running
                true, true),
            swerveDriveSubsystem));
    setAutoCommands();
    SmartDashboard.putData("Autos", AutoChooser);
  }

  private void configureBindings() {
    
    aCoDriverButton.onTrue(scoreCommands.intakeNote());
    leftDriverTrigger.onTrue(scoreCommands.scoreAmp()); 
    rightDriverTrigger.onTrue(scoreCommands.scoreSpeaker());
    dpadleft.onTrue(scoreCommands.shuttle()); //TODO: fix this
    bCoDriverButton.onTrue(scoreCommands.shootNote());
    yCoDriverButton.onTrue(scoreCommands.compactPosition());
    xCoDriverButton.onTrue(scoreCommands.intakeDown());
    startButton.onTrue(scoreCommands.getHangReady());

    atAutoAimTarget.and(atAutoFaceTarget).whileTrue(autoTargetNotifyCommand);

 
    //Co-Driver Controls
  
    yButton.onTrue(new InstantCommand(swerveDriveSubsystem::zeroHeading));// driver button

     dpadRight.onTrue(scoreCommands.outtake());
     rightBumperCoDriver.whileTrue(autoAimCommand).whileTrue(autoFaceCommand).whileFalse(scoreCommands.compactPosition());
    dpadDownCoDriver.onTrue(scoreCommands.hang());
  
    
    

  }

  public void registerNamedCommands() {
    NamedCommands.registerCommand("Intake Note", scoreCommands.intakeNote());
    NamedCommands.registerCommand("Shoot Note", scoreCommands.shootNote2());
    NamedCommands.registerCommand("Score Speaker", scoreCommands.scoreSpeaker());
    NamedCommands.registerCommand("Compact Position", scoreCommands.compactPosition2());
    NamedCommands.registerCommand("Score Speaker and Shoot", scoreCommands.scoreSpeakerAndShootNote());
  }

  public void setAutoCommands(){
    AutoCommandHolder autos = new AutoCommandHolder(endEffectorSubsystem, scoreCommands, swerveDriveSubsystem); 
    AutoChooser.addOption("DriveBack", autos.driveBack(3)); //anywhere
    AutoChooser.addOption("Speaker", autos.autoSpeaker()); //anywhere
    AutoChooser.addOption("SpeakerTaxi", autos.autoSpeakerTaxi()); //anywhere with caution--prefferably center
    AutoChooser.addOption("2-SpeakerTaxi", autos.autoCenterSpeakerTaxiIntakeSpeaker()); //center only
    AutoChooser.addOption("Taxi-FromBack-DirectAim", new PathPlannerAuto("Back-DirectAim"));
    AutoChooser.addOption("Taxi-FromCenter-DirectAim", new PathPlannerAuto("Center-DirectAim"));
    AutoChooser.addOption("Taxi-FromFront-DirectAim", new PathPlannerAuto("Front-DirectAim"));
    AutoChooser.addOption("Taxi-FromFront-EXIT", new PathPlannerAuto("Copy of Front-DirectAim"));
    AutoChooser.addOption("amp-side-3-note", new PathPlannerAuto("Amp-side-3-note"));
  }

  public Command getAutonomousCommand() {
    return AutoChooser.getSelected(); 
  }
  

}
