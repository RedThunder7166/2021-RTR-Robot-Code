/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import frc.robot.subsystems.Stage12ConveyorSubsystem;
import frc.robot.subsystems.Stage3ConveyorSubsystem;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.FlywheelSubsystem;
import frc.robot.subsystems.PneumaticSubsystem;
import frc.robot.subsystems.AdjustTurretSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.AdjustTurretCommand;
import frc.robot.commands.DriveCommand;
import frc.robot.commands.FlyWheelOn;
import frc.robot.commands.FlyWheelOff;
import frc.robot.Constants;
import frc.robot.commands.Conveyor.*;
import frc.robot.commands.Autonomous.*;
import frc.robot.commands.Autonomous.Galactic.*;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {

  // The robot's subsystems and commands are defined here...
  private final DriveSubsystem m_drivesubsystem = new DriveSubsystem();
  private final PneumaticSubsystem m_pneumaticsubsystem = new PneumaticSubsystem();
  private final Stage12ConveyorSubsystem m_stage12conveyorsubsystem = new Stage12ConveyorSubsystem();
  private final Stage3ConveyorSubsystem m_stage3conveyorsubsystem = new Stage3ConveyorSubsystem();
  private final AdjustTurretSubsystem m_adjustturretsubsystem = new AdjustTurretSubsystem();
  private final FlywheelSubsystem m_flywheelsubsystem = new FlywheelSubsystem();
  
  //Chooser for autonomous commands
  Command m_autonomousCommand;
  SendableChooser<Command> m_chooser = new SendableChooser<>();

  //Create the controllers
  GenericHID driverController = new XboxController(Constants.DRIVE_CONTROLLER);
  GenericHID opController = new Joystick(Constants.OPERATOR_CONTROLLER);
  GenericHID testing = new Joystick(Constants.TESTING_CONTROLLER);


  // Driver xBox Buttons

  Button A_Button = new JoystickButton(driverController, 1);
  Button B_Button = new JoystickButton(driverController, 2);
  Button X_Button = new JoystickButton(driverController, 3);
  Button Y_Button = new JoystickButton(driverController, 4);
  Button LB_Button = new JoystickButton(driverController, 5);
  Button RB_Button = new JoystickButton(driverController, 6);
  Button Select_Button = new JoystickButton(driverController, 7);
  Button Start_Button = new JoystickButton(driverController, 8);
  Button Left_Stick_Button = new JoystickButton(driverController, 9);
  Button Right_Stick_Button = new JoystickButton(driverController, 10);

  // Operator xBox Buttons

  Button OP_A_Button = new JoystickButton(opController, 1);
  Button OP_B_Button = new JoystickButton(opController, 2);
  Button OP_X_Button = new JoystickButton(opController, 3);
  Button OP_Y_Button = new JoystickButton(opController, 4);
  Button OP_LB_Button = new JoystickButton(opController, 5);
  Button OP_RB_Button = new JoystickButton(opController, 6);
  Button OP_Select_Button = new JoystickButton(opController, 7);
  Button OP_Start_Button = new JoystickButton(opController, 8);
  Button OP_Left_Stick_Button = new JoystickButton(opController, 9);
  Button OP_Right_Stick_Button = new JoystickButton(opController, 10);

  //xBox test controller buttons

  Button test_A_button = new JoystickButton(testing, 1);
  Button test_B_button = new JoystickButton(testing, 2);
  Button test_X_button = new JoystickButton(testing, 3);
  Button test_Y_button = new JoystickButton(testing, 4);
  Button test_LB_button = new JoystickButton(testing, 5);
  Button test_RB_button = new JoystickButton(testing, 6);
  Button test_Select_button = new JoystickButton(testing, 7);
  Button test_Start_button = new JoystickButton(testing, 8);
  Button test_Left_Stick_button = new JoystickButton(testing, 9);
  Button test_Right_Stick_button = new JoystickButton(testing, 10);

  public RobotContainer() {
    configureButtonBindings();

    m_drivesubsystem.setDefaultCommand(new DriveCommand(m_drivesubsystem, 
                                                          () -> driverController.getRawAxis(Constants.DRIVE_RIGHT_TRIGGER), 
                                                          () -> driverController.getRawAxis(Constants.DRIVE_LEFT_TRIGGER),
                                                          () -> driverController.getRawAxis(Constants.DRIVE_RIGHT_X_AXIS),
                                                          () -> driverController.getRawAxis(Constants.DRIVE_LEFT_Y_AXIS)));
    m_stage12conveyorsubsystem.setDefaultCommand(new Stage12ConveyorCommand(m_stage12conveyorsubsystem, 
                                                          () -> opController.getRawAxis(Constants.OPERATOR_LEFT_Y_AXIS)));
    m_stage3conveyorsubsystem.setDefaultCommand((new Stage3ConveyorCommand(m_stage3conveyorsubsystem,
                                                          () -> opController.getRawAxis(Constants.OPERATOR_RIGHT_Y_AXIS))));
                                                  
    //Creates tab for Autonomous on shuffleboard and selects what is available

    Shuffleboard.getTab("Autonomous").add(m_chooser);
    m_chooser.addOption("Galactic", new GalacticSequential(m_drivesubsystem, m_stage12conveyorsubsystem, m_pneumaticsubsystem, 
      m_drivesubsystem.galacticTurn1(), m_drivesubsystem.galacticTurn2(), m_drivesubsystem.getGalDistance()));
    m_chooser.addOption("Barrel Racing", new BarrelRacing(m_drivesubsystem));
    m_chooser.addOption("Bounce Path", new BouncePath(m_drivesubsystem));
                                                   
  }

  /**
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    A_Button.whenPressed(new InstantCommand(() -> m_pneumaticsubsystem.toggleGear()));
    OP_X_Button.whileHeld(new AdjustTurretCommand(m_adjustturretsubsystem));
    OP_Y_Button.whenPressed(new FlyWheelOn(m_flywheelsubsystem));
    OP_B_Button.whenPressed(new FlyWheelOff(m_flywheelsubsystem));
  }


  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return m_chooser.getSelected();
  }
}
