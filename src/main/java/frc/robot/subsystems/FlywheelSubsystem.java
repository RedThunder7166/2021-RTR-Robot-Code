/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class FlywheelSubsystem extends SubsystemBase {

  public TalonFX flyWheelRight = new TalonFX(Constants.FLYWHEEL_RIGHT_CAN);
  public TalonFX flyWheelLeft = new TalonFX(Constants.FLYWHEEL_LEFT_CAN);
  PowerDistributionPanel PDP = new PowerDistributionPanel(0);


  /**
   * Creates a new FlywheelSubsystem.
   */
  public FlywheelSubsystem() {

  }

  public void FlyWheelUp(){

    //SmartDashboard.putBoolean("Flywheel status", true);
    // double startup = .00001;
    // double flySpeedRight = 0.0;
    // double flySpeedLeft = 0.0;
    // while(flyWheelRight.getMotorOutputPercent() > -0.60 && flyWheelLeft.getMotorOutputPercent() < 0.60){
    //   flySpeedRight += startup;
    //   flySpeedLeft -= startup;

    //   flyWheelRight.set(ControlMode.PercentOutput, flySpeedRight);
    //   flyWheelLeft.set(ControlMode.PercentOutput, flySpeedLeft);
    // }

    flyWheelRight.set(ControlMode.PercentOutput, 0.60);
    flyWheelLeft.set(ControlMode.PercentOutput, -0.60);
    SmartDashboard.putNumber("Left Flywheel", PDP.getCurrent(12));
    SmartDashboard.putNumber("Right Flywheel", PDP.getCurrent(13));
    
  }

  public void FlyWheelOff(){
    flyWheelRight.set(ControlMode.PercentOutput, 0.0);
    flyWheelLeft.set(ControlMode.PercentOutput, 0.0);
    SmartDashboard.putBoolean("Flywheel status", false);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
