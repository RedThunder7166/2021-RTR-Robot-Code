/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.LEDSubsystem;

public class DriveCommand extends CommandBase {

  private final DriveSubsystem driveSubsystem;
  private final LEDSubsystem ledSubsystem;
  private final DoubleSupplier movingForward;
  private final DoubleSupplier movingBackward;
  private final DoubleSupplier turning;
  private final DoubleSupplier stopping;
  
  /**
   * Creates a new DriveCommand.
   */
  public DriveCommand(DriveSubsystem subsystem, LEDSubsystem subsystem2, DoubleSupplier forward, DoubleSupplier backward, DoubleSupplier rotation, DoubleSupplier stop) {
    driveSubsystem = subsystem;
    ledSubsystem = subsystem2;
    movingForward = forward;
    movingBackward = backward;
    stopping = stop;
    turning = rotation;
    
    // Use addRequirements() here to declare subsystem dependencies.

    addRequirements(subsystem);
    addRequirements(subsystem2);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    
    
    //driveSubsystem.resetGyro();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

      SmartDashboard.putNumber("Gyro", driveSubsystem.getGyro());
      double xMotion = movingForward.getAsDouble() - movingBackward.getAsDouble(); 

      driveSubsystem.RocketLeagueDrive(xMotion, turning.getAsDouble(), stopping.getAsDouble());

      if(driveSubsystem.getLeftMotors() > 0 && driveSubsystem.getRightMotors() < 0){ //going forward
        ledSubsystem.setLEDLeft(.77); //Green
        ledSubsystem.setLEDRight(.77);
      } else if(driveSubsystem.getLeftMotors() < 0 && driveSubsystem.getRightMotors() > 0){ //going backward
        ledSubsystem.setLEDLeft(.65); //Orange
        ledSubsystem.setLEDRight(.65);
      } else if (driveSubsystem.getLeftMotors() > 0){
        ledSubsystem.setLEDLeft(-.11); //Strobe Red
        ledSubsystem.setLEDRight(-.11);
      } else if (driveSubsystem.getLeftMotors() <0){
        ledSubsystem.setLEDLeft(-.09); //Strobe Blue
        ledSubsystem.setLEDRight(-.09);
      } else {
        ledSubsystem.setLEDLeft(-.99); //Rainbow
        ledSubsystem.setLEDRight(-.99);
      }



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
