/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class PneumaticSubsystem extends SubsystemBase {
  /**
   * Creates a new PneumaticSubsystem.
   */
  public Compressor c = new Compressor();
  public Solenoid Sol = new Solenoid(Constants.PCM_CAN_ID, Constants.TRANSMISSION_SOLENOID);

  public PneumaticSubsystem() {

  }

  public void disableCompressor(){
    c.setClosedLoopControl(false);
  }

  public void enableCompressor(){
    c.setClosedLoopControl(true);
  }


  public void setHighGear(){
    Sol.set(true);
  }

  public void setLowGear(){
    Sol.set(false);
  }

  public void toggleGear(){
    if(Sol.get() == true){
      Sol.set(false);
    } else{
      Sol.set(true);
    }
  }

  public String getSolStatus(){
    if(Sol.get() == true){
      return "LOW";
    } else {
      return "HIGH";
    }
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
