/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import java.util.List;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import org.photonvision.PhotonCamera;
import org.photonvision.PhotonTrackedTarget;
import org.photonvision.PhotonUtils;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class DriveSubsystem extends SubsystemBase {
 
  public CANSparkMax frontLeft = new CANSparkMax(Constants.FRONT_LEFT_MOTOR_CAN, MotorType.kBrushless);
  public CANSparkMax frontRight = new CANSparkMax(Constants.FRONT_RIGHT_MOTOR_CAN, MotorType.kBrushless);
  public CANSparkMax backLeft = new CANSparkMax(Constants.BACK_LEFT_MOTOR_CAN, MotorType.kBrushless);
  public CANSparkMax backRight = new CANSparkMax(Constants.BACK_RIGHT_MOTOR_CAN, MotorType.kBrushless);

  ADXRS450_Gyro gyro = new ADXRS450_Gyro();

  //Group motors

  public SpeedControllerGroup leftGroup = new SpeedControllerGroup(frontLeft, backLeft);
  public SpeedControllerGroup rightGroup = new SpeedControllerGroup(frontRight, backRight);
   
  //Defining Differential Drive

  public DifferentialDrive differentialRocketLeagueDrive = new DifferentialDrive(leftGroup, rightGroup);

  //Create encoder from Spark MAX

  public CANEncoder leftEncoder = frontLeft.getEncoder();
  public CANEncoder rightEncoder = frontRight.getEncoder();

  //Photon Camera
  PhotonCamera camera = new PhotonCamera("photonvision");

  double range = 1.0;  // Degrees of range that it treats as being "on target"

  static final double kCameraHeight = 0.2794; // meters
  static final double kCameraPitch = 0.0; // radians
  static final double kTargetHeight = 0.0889; // meters

  double cell0;
  double cell1;

  //Characterization - Path Weaver

  public static final double ksVolts = 0.14;
  public static final double kvVoltSecondsPerMeter = 0.0861;
  public static final double kavoltSecondsSquaredPerMeter = 0.00849;
  public static final double kPDriveVel = 0.379;
  public static final double kTrackWidthMeters = 0.61;
  public static final DifferentialDriveKinematics kDriveKinematics = new DifferentialDriveKinematics(kTrackWidthMeters);
  public static final double kMaxSpeedMetersPerSecond = 2;
  public static final double kMaxAccelerationMetersPerSecondSquared = 2;
  public static final double kRamseteB = 2;
  public static final double kRamseteZeta = 0.7;



  public DifferentialDriveWheelSpeeds getWheelSpeeds() {
    leftEncoder.setVelocityConversionFactor(Constants.ENCODER_CONVERSION_VELOCITY_METERS);
    rightEncoder.setVelocityConversionFactor(Constants.ENCODER_CONVERSION_VELOCITY_METERS);
    return new DifferentialDriveWheelSpeeds(leftEncoder.getVelocity(), rightEncoder.getVelocity());
  }

  public DriveSubsystem() {
  }

  // FORWARD: RIGHT -
  // FORWARD: LEFT +

  // DRIVE TRAIN

  public void RocketLeagueDrive(double speed, double turn, double stop){
  
    double turning = 0.0;
    double moving = 0.0;
    double driveSpeed = 0.75; // Max drivespeed
    double rotateSpeed = 0.55; // How fast the bot turns while moving forward/backward
    double swivel = 0.45;  //How fast the bot pivots in place
    if(Math.abs(stop) > 0.5){
      setBrakeMode();
   
    } else if(speed >= 0.10 || speed <= -0.10){
        setCoastMode();
   
        moving = driveSpeed * speed;
        if(Math.abs(turn) > 0.10){
          turning = rotateSpeed * turn;
     
        }
      } else if(Math.abs(turn) > 0.10){
          setCoastMode();
          turning = swivel * Math.pow(turn, 3);
       
      }

    differentialRocketLeagueDrive.arcadeDrive(moving, turning);

  }

  public void setBrakeMode(){
    frontLeft.setIdleMode(IdleMode.kBrake);
    backLeft.setIdleMode(IdleMode.kBrake);
    frontRight.setIdleMode(IdleMode.kBrake);
    backRight.setIdleMode(IdleMode.kBrake);
  }

  public void setCoastMode(){
    frontLeft.setIdleMode(IdleMode.kCoast);
    backLeft.setIdleMode(IdleMode.kCoast);
    frontRight.setIdleMode(IdleMode.kCoast);
    backRight.setIdleMode(IdleMode.kCoast);
  }

  //Characterization - Path Weaver



  public double getleftEncoder(){
    leftEncoder.setPositionConversionFactor(Constants.ENCODER_CONVERSION_INCHES);
    return leftEncoder.getPosition();
  }

  public double getrightEncoder(){
    rightEncoder.setPositionConversionFactor(Constants.ENCODER_CONVERSION_INCHES);
    return rightEncoder.getPosition();
  }

  public void resetEncoders(){
    leftEncoder.setPosition(0.0);
    rightEncoder.setPosition(0.0);
  }

  public void setRightMotors(double speed){
    rightGroup.set(speed);
  }

  public void setLeftMotors(double speed){
    leftGroup.set(speed);
  }

  public double getRightMotors(){
    return rightGroup.get();
  }

  public double getLeftMotors(){
    return leftGroup.get();
  }

  // GYRO

  public void resetGyro(){
    gyro.reset();
  }

  public double getGyro(){
    return gyro.getAngle();
  }

  //AUTONOMOUS COMMAND METHODS

  public void barrelRacing(){

  }



  public String galacticTurn1(){
    String turn1 = "ERROR"; 
    var result = camera.getLatestResult();
    List<PhotonTrackedTarget> targets = result.getTargets();
    if(result.hasTargets()){
      if(targets.size() >1){
        double cell0 = targets.get(0).getYaw();
        double cell1 = targets.get(1).getYaw();
        if(cell1 > cell0 ){  //Cell1 is to the right of cell0
          turn1 = "RIGHT";  // true = turn right
        } else if(cell1 < cell0){ // cell1 is to the left of cell0
          turn1 = "LEFT"; // false = turn left
        } else{
          turn1 = "ERROR";
        }
      }
    } else{
      turn1 = "ERROR";
    }
    SmartDashboard.putString("Turn 1", turn1);
    return turn1;
  }

  public String galacticTurn2(){
    String turn2 = "ERROR";
    var result = camera.getLatestResult();
    List<PhotonTrackedTarget> targets = result.getTargets();
    if(result.hasTargets()){
      if(targets.size() > 2){
        double cell1 = targets.get(1).getYaw();
        double cell2 = targets.get(2).getYaw();
        if(cell2 > cell1){ //cell2 is to the right of cell1
          turn2 = "RIGHT";  // true = turn right
        } else if(cell2 < cell1){ //cell2 is to the left cell1
          turn2 = "LEFT"; // false = turn left
        } 
      } else{
        turn2 = "ERROR";
      }
    }
    SmartDashboard.putString("Turn 2", turn2);
    return turn2;
  }

  public void galPivot(String turn1, String turn2){
    double rotationSpeed = 0.0;
    var result = camera.getLatestResult();
    List<PhotonTrackedTarget> targets = result.getTargets();
  
      if(result.hasTargets() && targets.get(0).getYaw() > range){ // While it has targets, no errors
        rotationSpeed = 0.25*Math.sin((Math.PI/80)*targets.get(0).getYaw()) + .02; // Rotate first to 0, always the closest target
    } else if(result.hasTargets() && targets.get(0).getYaw() < -1*range){
        rotationSpeed = 0.25*Math.sin((Math.PI/80)*targets.get(0).getYaw()) - .02; // Rotate first to 0, always the closest target    }
    } else{
      rotationSpeed = 0.0;
    }
    setLeftMotors(rotationSpeed);
    setRightMotors(rotationSpeed);
  }

  public double getGALYaw(){
    var result = camera.getLatestResult();
    List<PhotonTrackedTarget> targets = result.getTargets();
    if(result.hasTargets()){
      return targets.get(0).getYaw();
    } else{
      return 0.0;
    }
  }

  public void galOrient(){
    double error = gyro.getAngle();
    double speed;
    // FORWARD: RIGHT -, LEFT +
    // TURN RIGHT: +/+
    // TURN LEFT: -/-
    if(error > 0){ //The bot is too far right, turn left
      speed = 0.25*Math.sin((Math.PI/180)*getGyro()) + .05; // outputs a positive speed, need a negative to turn left
    } else if (error < 0){
      speed = 0.25*Math.sin((Math.PI/180)*getGyro()) - .05;
    } else{
      speed = 0.0;
    }
    setLeftMotors(-speed);
    setRightMotors(-speed);
  }

  public double getGalDistance(){
    var result = camera.getLatestResult();
    if(result.hasTargets()){
      double distanceMeters = PhotonUtils.calculateDistanceToTargetMeters(
        kCameraHeight, kTargetHeight, kCameraPitch, Math.toRadians(result.getBestTarget().getPitch()));
      return distanceMeters * 39.372; // returns distance to target in feet
    } else{
      return 0.0;
    }
  }

  public void galDrive(){
    double kp = 1;
    double error = -gyro.getRate();
    double leftspeed;
    double rightspeed;
    var result = camera.getLatestResult();
    List<PhotonTrackedTarget> targets = result.getTargets();
    setBrakeMode();
    if(getGalDistance() > 24){
      if(targets.get(0).getYaw() > 1){
        leftspeed = .30;
        rightspeed = .25;
      } else if(targets.get(0).getYaw() < -1){
        leftspeed = .25;
        rightspeed = .30;
      }else{
        leftspeed = .30;
        rightspeed = .30;
      }
    } else{
      leftspeed = 0.0;
      rightspeed = 0.0;
    }
    setRightMotors(-rightspeed);
    setLeftMotors(leftspeed);
  }

  public void galEndDrive(){
    double distance = 24;
    if(getleftEncoder() < distance){
      setRightMotors(-.20);
      setLeftMotors(.20);
    } else{
      setRightMotors(0.0);
      setLeftMotors(0.0);
    }
  }

  public void galTurn1(String turn1){
    double turnPower;
    double currentAngle = getGyro();
    double kp = .50;
    double rightAngle = 45;
    double leftAngle = -45;
    if(turn1 == "RIGHT"){
      turnPower = kp * (currentAngle - rightAngle);
    }

    // if(turn1 == "RIGHT"){
    //   if(error > 45){ //The bot is too far right, turn left
    //     speed = 0.50*Math.sin((Math.PI/180)*getGyro()) + .05; // outputs a positive speed, need a negative to turn left
    //   } else if (error < 45){
    //     speed = 0.50*Math.sin((Math.PI/180)*getGyro()) - .05;
    //   } else{
    //     speed = 0.0;
    //   }
    // } else if(turn1 == "LEFT"){
    //   if(error > -45){ //The bot is too far right, turn left
    //     speed = 0.50*Math.sin((Math.PI/180)*getGyro()) + .05; // outputs a positive speed, need a negative to turn left
    //   } else if (error < -45){
    //     speed = 0.50*Math.sin((Math.PI/180)*getGyro()) - .05;
    //   } else{
    //     speed = 0.0;
    //   }
    // } else{
    //   speed = 0.0;
    // }
    // setLeftMotors(-speed);
    // setRightMotors(-speed);
  }

  public void galTurn2(String turn2){
    double speed;
    double error = getGyro();
    if(turn2 == "RIGHT"){
      if(error > 45){ //The bot is too far right, turn left
        speed = 0.25*Math.sin((Math.PI/180)*getGyro()) + .05; // outputs a positive speed, need a negative to turn left
      } else if (error < 45){
        speed = 0.25*Math.sin((Math.PI/180)*getGyro()) - .05;
      } else{
        speed = 0.0;
      }
    } else if(turn2 == "LEFT"){
      if(error > -45){ //The bot is too far right, turn left
        speed = 0.25*Math.sin((Math.PI/180)*getGyro()) + .05; // outputs a positive speed, need a negative to turn left
      } else if (error < -45){
        speed = 0.25*Math.sin((Math.PI/180)*getGyro()) - .05;
      } else{
        speed = 0.0;
      }
    } else{
      speed = 0.0;
    }
    setLeftMotors(-speed);
    setRightMotors(-speed);
  }  
  

       




  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
