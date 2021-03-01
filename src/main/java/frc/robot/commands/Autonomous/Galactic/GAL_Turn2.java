// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Autonomous.Galactic;


import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;

public class GAL_Turn2 extends CommandBase {
  DriveSubsystem drivesubsystem;
  int turn2;
  /** Creates a new GAL_Turn2. */
  public GAL_Turn2(DriveSubsystem subsystem, int Turn2) {

    drivesubsystem = subsystem;
    turn2 = Turn2;

    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(subsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    drivesubsystem.galTurn2(turn2);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if(Math.abs(drivesubsystem.getGyro()) < 35 ){
      return false;
    } else{
      drivesubsystem.setRightMotors(0.0);
      drivesubsystem.setLeftMotors(0.0);
      return true;
    }
  }
}
