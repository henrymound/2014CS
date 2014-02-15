/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.4572.robauts;


import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStationLCD;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Timer;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Turf extends IterativeRobot {
    
    private RobotDrive baseDrive;
    private Victor shooterVictor;
    private Victor armVictor;
    private DigitalInput[] digitalIO = new DigitalInput[14];
    private Timer timerTest;
    
    private Joystick joystickLeft;
    private Joystick joystickRight;
    
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        getWatchdog().setEnabled(true);
        getWatchdog().setExpiration(100); //Watchdog will function for 100 milliseconds between feeds

        baseDrive = new RobotDrive(
                new Victor(1), //front left motor
                new Victor(2), //rear left motor
                );
        baseDrive.setExpiration(0.100); // Set the safety expiration to 100 milliseconds
        baseDrive.setSafetyEnabled(false);

        shooterVictor = new Victor(3);
        armVictor = new Victor(4);
        
        joystickLeft = new Joystick(1);
        joystickRight = new Joystick(2);
        
        for (int i = 0; i < 14; i++) {
            digitalIO[i] = new DigitalInput(i + 1);
        }
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {

    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        getWatchdog().feed();
        grabInput();
        driveMotors();
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        getWatchdog().feed();
        grabInput();
        driveMotors();
    }
    
    private void driveMotors() {
        getWatchdog().feed();
        baseDrive.tankDrive(gamepad, gamepad.getAxisChannel(Joystick.AxisType.kThrottle), gamepad, gamepad.getAxisChannel(Joystick.AxisType.kY));
        dispMessage(2, 1, "Right Stick: " + Double.toString(gamepad.getThrottle()));
        dispMessage(1, 1, "Left Stick: " + Double.toString(gamepad.getY()));
    }

    private void grabInput() {
        getWatchdog().feed();
        
    }
    
    private void dispMessage(int lineNumber, int startingCollumn, String message) {
        DriverStationLCD.Line ln;
        switch (lineNumber) {
            case (0):
                ln = DriverStationLCD.Line.kUser1;
                break;
            case (1):
                ln = DriverStationLCD.Line.kUser2;
                break;
            case (2):
                ln = DriverStationLCD.Line.kUser3;
                break;
            case (3):
                ln = DriverStationLCD.Line.kUser4;
                break;
            case (4):
                ln = DriverStationLCD.Line.kUser5;
                break;
            case (5):
                ln = DriverStationLCD.Line.kUser6;
                break;
            default:
                ln = DriverStationLCD.Line.kUser1;
                break;
        }
        if (startingCollumn < 1 || startingCollumn > 21) {
            startingCollumn = 1;
        }
        DriverStationLCD.getInstance().println(ln, startingCollumn, "                                    ");
        DriverStationLCD.getInstance().println(ln, startingCollumn, message);
        DriverStationLCD.getInstance().updateLCD();
    }
    
}
