/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.Conveyor;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Stage12ConveyorSubsystem;

public class Stage12ConveyorCommand extends CommandBase {

  private final Stage12ConveyorSubsystem conveyorSubsystem;
  private final DoubleSupplier speed;

  /**
   * Creates a new Stage12ConveyorCommand.
   */
  public Stage12ConveyorCommand(Stage12ConveyorSubsystem subsystem, DoubleSupplier rawspeed) {
    conveyorSubsystem = subsystem;
    speed = rawspeed;
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
    conveyorSubsystem.stage1Conveyor(speed.getAsDouble());
    conveyorSubsystem.stage2Conveyor(speed.getAsDouble());
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
