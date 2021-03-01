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
  public GalacticSequential(DriveSubsystem drive, Stage12ConveyorSubsystem conveyor, PneumaticSubsystem pneumatic, int turn1, int turn2, double distance) {
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
        new PrintCommand("Driving " + drive.getGalDistance() + " inches to cell0"),
      new GAL_Drive(drive), // Drive to within 24 inches of cell0
      new InstantCommand(() -> drive.resetEncoders()), // Reset encoders to use in next step
        new PrintCommand("Executing GAL_EndDrive"),
      new GAL_EndDrive(drive), //drives 24 inches
        new PrintCommand("Executing GAL_Orient"),
      new GAL_Orient(drive), //Orient zero degrees
        new PrintCommand("Executing GAL_ConveyorOFF"),
      new GAL_ConveyorOFF(conveyor),
        new PrintCommand("Executing GAL_Turn"),
        new PrintCommand("Turning to the " + turn1),
      new GAL_Turn1(drive, turn1),
        new PrintCommand("GAL_Turn has ended"),
        new PrintCommand("Completed turn to the " + turn1),
      //cell1
      new GAL_Pivot(drive, turn1, turn2), // Turn to cell1
        new PrintCommand("Executing GAL_ConveyorON cell1"),
      new GAL_ConveyorON(conveyor),
        new PrintCommand("Executing GAL_Drive cell1"),
        new PrintCommand("Driving " + drive.getGalDistance() + " inches to cell1"),
      new GAL_Drive(drive), // Drive to within 24 inches of cell0
      new InstantCommand(() -> drive.resetEncoders()), // Reset encoders to use in next step
        new PrintCommand("Executing GAL_EndDrive cell1"),
      new GAL_EndDrive(drive), //drives 24 inches
        new PrintCommand("Executing GAL_Orient cell1"),
      new GAL_Orient(drive),
        new PrintCommand("Executing GAL_ConveyorOFF"),
      new GAL_ConveyorOFF(conveyor),
        new PrintCommand("Executing GAL_Turn cell1"),
        new PrintCommand("Turning to the " + turn2),
      new GAL_Turn2(drive, turn2) 

      //Rotate to object
      //Start conveyor
      //Drive to cell 2
      //Stop conveyor
      //Rotate to zero degrees
      //Drive to end zone ???
    );
  }
}

