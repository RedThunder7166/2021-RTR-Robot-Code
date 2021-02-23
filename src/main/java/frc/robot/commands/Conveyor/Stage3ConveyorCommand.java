/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.Conveyor;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Stage3ConveyorSubsystem;

public class Stage3ConveyorCommand extends CommandBase {

  private final Stage3ConveyorSubsystem stage3ConveyorSubsystem;
  private final DoubleSupplier speed;
  /**
   * Creates a new Stage3ConveyorCommand.
   */
  public Stage3ConveyorCommand(Stage3ConveyorSubsystem subsystem, DoubleSupplier rawspeed) {
    stage3ConveyorSubsystem = subsystem;
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
    stage3ConveyorSubsystem.stage3Conveyor(speed.getAsDouble());
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
