/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.Autonomous.Galactic;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Stage12ConveyorSubsystem;

public class GAL_ConveyorON extends CommandBase {
  Stage12ConveyorSubsystem stage12ConveyorSubsystem;
  /**
   * Creates a new GAL_ConveyorON.
   */
  public GAL_ConveyorON(Stage12ConveyorSubsystem subsystem) {
    stage12ConveyorSubsystem = subsystem;
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
    stage12ConveyorSubsystem.stage1Conveyor(1);
    stage12ConveyorSubsystem.stage2Conveyor(1);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if(Math.abs(stage12ConveyorSubsystem.getStage1Conveyor()) > 0){
      return true;
    } else{
    return false;
    }
  }
}
