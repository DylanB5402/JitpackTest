/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.nerdherd687.lib.drivetrain.characterization;

import com.nerdherd687.lib.drivetrain.Drivetrain;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class DriveCharacterizationTest extends Command {

  private double m_voltage, m_startTime, m_time;
  private double m_rampRate;
  private Drivetrain m_drive;
  
  public DriveCharacterizationTest(Drivetrain drive, double rampRate) {
    m_rampRate = rampRate;
    m_drive = drive;
    requires(m_drive);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
      m_startTime = Timer.getFPGATimestamp();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    m_time = Timer.getFPGATimestamp() - m_startTime;
    m_voltage = (m_rampRate * m_time)/12;
    m_drive.setPower(m_voltage, m_voltage);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return m_time > 12 / m_rampRate;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    m_drive.setPowerZero();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
