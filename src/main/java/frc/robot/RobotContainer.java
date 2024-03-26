package frc.robot;


import com.pathplanner.lib.auto.NamedCommands;
import com.pathplanner.lib.commands.PathPlannerAuto;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants.OIConstants;
import frc.robot.commands.AutoCommands.AutoCommandHolder;
import frc.robot.commands.LimelightCommands.AutoTargetCommand;
import frc.robot.commands.LimelightCommands.LiftAimCommand;
import frc.robot.commands.ScoreCommands.LiftCommand;
import frc.robot.commands.ScoreCommands.ScoreCommandHolder;
import frc.robot.subsystems.EndEffectorSubsystem;
import frc.robot.subsystems.LimelightSubsystem;
import frc.robot.subsystems.SmartLimelightSubsystem;
import frc.robot.subsystems.SmartSwerveDriveSubsystem;
import frc.robot.subsystems.SwerveDriveSubsystem;

public class RobotContainer {

  public static CommandXboxController driverController= new CommandXboxController(0); 
  public static CommandXboxController coDriverController= new CommandXboxController(1); 

  SendableChooser<Command> AutoChooser = new SendableChooser<>();

  //SUBSYSTEM
  EndEffectorSubsystem endEffectorSubsystem = new EndEffectorSubsystem();
  SmartLimelightSubsystem limelightSubsystem = new SmartLimelightSubsystem();
  SmartSwerveDriveSubsystem swerveDriveSubsystem = new SmartSwerveDriveSubsystem(limelightSubsystem);
  

  //COMMANDS
  ScoreCommandHolder scoreCommands = new ScoreCommandHolder(endEffectorSubsystem);
  LiftAimCommand liftAimCommand = new LiftAimCommand(limelightSubsystem, endEffectorSubsystem);
  AutoTargetCommand autoTargetCommand = new AutoTargetCommand(limelightSubsystem, swerveDriveSubsystem, endEffectorSubsystem);
  LiftCommand liftCommand = new LiftCommand(endEffectorSubsystem, LimelightSubsystem.getEncoderTarget() ); //for setpoint after limelight aim 
 



  //TRIGGERS 
  Trigger yButton = coDriverController.y(); 
  Trigger xButton = coDriverController.x(); 
  Trigger aButton = coDriverController.a();
  Trigger bButton = coDriverController.b();
  Trigger dpadUpCoDriver = coDriverController.povUp();
  Trigger dpadDownCoDriver = coDriverController.povDown();
  Trigger leftBumperCoDriver = coDriverController.leftBumper(); 
  Trigger rightBumperCoDriver = coDriverController.rightBumper(); 
  Trigger rightTriggerCoDriver = coDriverController.rightTrigger(); 


  Trigger aDriverButton = driverController.a(); 
  Trigger bDriverButton = driverController.b(); 
  Trigger xDriverButton = driverController.x(); 
  Trigger yDriverButton = driverController.y(); 
  Trigger leftDriverTrigger = driverController.leftTrigger(); 
  Trigger leftDriverBumper = driverController.leftBumper(); 
  Trigger rightDriverTrigger = driverController.rightTrigger();
  Trigger rightDriverBumper = driverController.rightBumper();
  Trigger dpadUpDriver = driverController.povUp();
  Trigger dpadDownDriver = driverController.povDown();
  Trigger startButton = driverController.start();


  Trigger autoFireReady = new Trigger(autoTargetCommand::getIsReadyToFire);



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
    //Driver Controls
    aDriverButton.onTrue(scoreCommands.intakeNote());
    leftDriverTrigger.onTrue(scoreCommands.scoreAmp()); 
    rightDriverTrigger.onTrue(scoreCommands.scoreSpeaker());
    bDriverButton.onTrue(scoreCommands.shootNote());
    yDriverButton.onTrue(scoreCommands.compactPosition());
    xDriverButton.onTrue(scoreCommands.intakeDown());
    rightDriverBumper.onTrue(scoreCommands.setFlyWheel());


    startButton.onTrue(scoreCommands.getHangReady());


    //Co-Driver Controls
    xButton.onTrue(new RunCommand(() -> swerveDriveSubsystem.setX(), swerveDriveSubsystem));
    yButton.onTrue(new InstantCommand(swerveDriveSubsystem::zeroHeading));
    bButton.onTrue(scoreCommands.outtake());
    aButton.whileTrue(liftAimCommand);
    rightBumperCoDriver.whileTrue(autoTargetCommand).onTrue(scoreCommands.setFlyWheel()).onFalse(scoreCommands.compactPosition());
    dpadDownCoDriver.onTrue(scoreCommands.hang());
   
    
    //autoFireReady.onTrue(scoreCommands.shootNote());

  }

  public void registerNamedCommands() {
    NamedCommands.registerCommand("Intake Note", scoreCommands.intakeNote());
    NamedCommands.registerCommand("Score Speaker", scoreCommands.scoreSpeaker());
    NamedCommands.registerCommand("Compact Position", scoreCommands.compactPosition());
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
  }

  public Command getAutonomousCommand() {
    return AutoChooser.getSelected(); 
  }

}
