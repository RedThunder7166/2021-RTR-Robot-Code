/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.Autonomous.Galactic;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.PneumaticSubsystem;
import frc.robot.subsystems.Stage12ConveyorSubsystem;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class GalacticSequential extends SequentialCommandGroup {
  /**
   * Creates a new GalacticSequential.
   */
  public GalacticSequential(DriveSubsystem drive, Stage12ConveyorSubsystem conveyor, PneumaticSubsystem pneumatic, String turn1, String turn2, double distance) {
    // Add your commands in the super() call, e.g.
    // super(new FooCommand(), new BarCommand());
    super(
      //Cell0
      new InstantCommand(() -> pneumatic.setLowGear()),
      new InstantCommand(() -> drive.resetGyro()), // Sets gyro to zero at beginning
        new PrintCommand("Executing GyroReset"),
        new PrintCommand("Turn 1: " + turn1 + ", Turn 2: " + turn2),
        new PrintCommand("Executing GAL_Pivot"),
      new GAL_Pivot(drive, turn1, turn2), // Turn to cell0
        new PrintCommand("Executing GAL_ConveyorON"),
      new GAL_ConveyorON(conveyor),
        new PrintCommand("Executing GAL_Drive"),
        new PrintCommand("Driving " + drive.getGalDistance() + " inches to target"),
      new GAL_Drive(drive), // Drive to cell0
      new InstantCommand(() -> drive.resetEncoders()), // Reset encoders to use in next step
        new PrintCommand("Executing GAL_EndDrive"),
      new GAL_EndDrive(drive),
        new PrintCommand("Executing GAL_Orient"),
      new GAL_Orient(drive), //Orient zero degrees
        new PrintCommand("Executing WAIT(2)"),
      new WaitCommand(2),
        new PrintCommand("Executing GAL_ConveyorOFF"),
      new GAL_ConveyorOFF(conveyor),
      //Cell1
        new PrintCommand("Executing GAL_Turn"),
        new PrintCommand("Turning to the " + turn1),
      new GAL_Turn1(drive, turn1),
        new PrintCommand("GAL_Turn has ended"),
        new PrintCommand("Completed turn to the " + turn1)
      //Rotate to cell 1
      //Start conveyor
      //Drive to cell 1
      //Stop conveyor
      //Rotate to zero degrees
      //Turn based on turn2

      //Rotate to object
      //Start conveyor
      //Drive to cell 2
      //Stop conveyor
      //Rotate to zero degrees
      //Drive to end zone ???
    );
  }
}

