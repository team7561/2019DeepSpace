/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.cameraserver.CameraServer;
//import edu.wpi.first.SmartDashboard;

/**
 * This is a demo program showing the use of the RobotDrive class, specifically
 * it contains the code necessary to operate a robot with tank drive.
 */
public class Robot extends TimedRobot {
  private DifferentialDrive m_myRobot;
  private Joystick m_leftStick;
  private Joystick m_rightStick;
  VictorSP left, right;

  @Override
  public void robotInit() {
    left = new VictorSP(3);
    right = new VictorSP(4);
    //m_myRobot = new DifferentialDrive(new PWMVictorSPX(3), new PWMVictorSPX(4));
    m_leftStick = new Joystick(1);
    //m_rightStick = new Joystick(1);
    CameraServer.getInstance().startAutomaticCapture();
  }

  @Override
  public void teleopPeriodic() {
    arcadeDrive(m_leftStick.getX(), m_leftStick.getY(), 1, false);
  }
  public void drive(double leftSpeed, double rightSpeed) {
    left.set(leftSpeed);
    right.set(-rightSpeed);
}
  public void arcadeDrive(double x, double y, double speed, boolean inverted) {
    //x = x * Math.abs(x) * speed;

    double right = (-y - x)*speed;
    double left = (- (y - x))*speed;
    if (left > 1) {
        left = 1;
    }
    if (right > 1) {
        right = 1;
    }
    if (inverted == true) {
        drive(-left, -right);
    }
    else
    {
        drive(left, right);
    }
}
}
