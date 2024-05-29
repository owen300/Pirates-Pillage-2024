// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// e WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.commands.ScoreCommands.ScoreCommandHolder;


public class Robot extends TimedRobot {
  private static Robot   instance;
  private Command m_autonomousCommand;
  public ScoreCommandHolder scorecommands;
  

  private RobotContainer m_robotContainer;

  @Override
  public void robotInit() {
    CameraServer.startAutomaticCapture();
    
    m_robotContainer = new RobotContainer();
    
    m_robotContainer.endEffectorSubsystem.resetliftEncoder();
    m_robotContainer.endEffectorSubsystem.resetEncoderHang();
    ScoreCommandHolder scorecommands=new ScoreCommandHolder(m_robotContainer.endEffectorSubsystem);
    SmartAimLookup.populateTable();
    System.gc();
  }
  public Robot()
  {
    instance = this;
  }

  public static Robot getInstance()
  {
    return instance;
  }

  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();
  }

  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_robotContainer.getAutonomousCommand();


    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }

  @Override
  public void autonomousPeriodic() {}

  @Override
  public void teleopInit() {

    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }

    m_robotContainer.swerveDriveSubsystem.getRoll();
    //m_robotContainer.endEffectorSubsystem.shootLeadMotor(0.85);
  }

  @Override
  public void teleopPeriodic() {
     m_robotContainer.swerveDriveSubsystem.getRoll();

  }

 
}
