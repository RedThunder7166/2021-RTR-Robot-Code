/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Stage3ConveyorSubsystem extends SubsystemBase {

  public VictorSPX stage3Motor = new VictorSPX(Constants.STAGE_3_MOTOR);

  /**
   * Creates a new Stage3ConveyorSubsystem.
   */
  public Stage3ConveyorSubsystem() {

  }

  public void stage3Conveyor(double speed){
    double speedConstant = 0.75;
    stage3Motor.set(ControlMode.PercentOutput, speedConstant*speed);

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
