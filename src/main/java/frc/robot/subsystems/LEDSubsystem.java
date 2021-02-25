// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PWMSparkMax;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class LEDSubsystem extends SubsystemBase {
  /** Creates a new LEDSubsystem. */
  public LEDSubsystem() {}

  PWMSparkMax ledLeft = new PWMSparkMax(0);
  PWMSparkMax ledRight = new PWMSparkMax(1);  

  public void setLEDLeft(double value){
    ledLeft.set(value);
  }
  
  public void setLEDRight(double value){
    ledRight.set(value);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
