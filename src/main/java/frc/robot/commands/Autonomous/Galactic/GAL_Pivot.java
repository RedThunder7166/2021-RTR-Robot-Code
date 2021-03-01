/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.Autonomous.Galactic;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;

public class GAL_Pivot extends CommandBase {

  DriveSubsystem driveSubsystem;
  int turn1;
  int turn2;
  /**
   * Creates a new GAL_Pivot.
   */
  public GAL_Pivot(DriveSubsystem subsystem, int Turn1, int Turn2) {
    Turn1 = turn1;
    Turn2 = turn2;
    driveSubsystem = subsystem;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(subsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    driveSubsystem.galPivot(turn1, turn2);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if(Math.abs(driveSubsystem.getGALYaw()) > 3){
      return false;
    }
    return true;
  }
}
