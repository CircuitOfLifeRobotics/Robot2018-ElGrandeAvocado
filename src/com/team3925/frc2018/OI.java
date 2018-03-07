package com.team3925.frc2018;

import com.team3925.frc2018.commands.CloseGrabbers;
import com.team3925.frc2018.commands.DecrementAdjustElevator;
import com.team3925.frc2018.commands.DriveManual.DriveManualInput;
import com.team3925.frc2018.commands.DropCube;
import com.team3925.frc2018.commands.IncrementAdjustElevator;
import com.team3925.frc2018.commands.OpenGrabbers;
import com.team3925.frc2018.commands.RunElevator;
import com.team3925.frc2018.commands.RunIntakeWheels;
import com.team3925.frc2018.commands.ShiftHigh;
import com.team3925.frc2018.commands.ShiftLow;
import com.team3925.frc2018.commands.ShootCube;
import com.team3925.frc2018.subsystems.Elevator.ElevatorState;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.buttons.Trigger;

public class OI implements DriveManualInput{
	
	private final Joystick wheel;
	private final Joystick xbox;
	private final Joystick stick;
	
	private static OI instance;
	
	private Button drivetrain_Shift;
	
	private Button jogElevatorTop;
	private Button jogElevatorScaleHigh;
	private Button jogElevatorScaleMiddle;
	private Button jogElevatorScaleLow;
	private Button jogElevatorSwitch;
	private Button jogElevatorBottom;
	private Trigger dropCube;
	private Trigger shootCube;
	private Button intakeCube;
	private Button openIntake;
	
	private Trigger tuneUp;
	private Trigger tuneDown;
	
	public static OI getInstance() {
		if (instance == null)
			instance = new OI();
		return instance;
	}
	
	private OI() {
		stick = new Joystick(0);
		wheel = new Joystick(1);
		xbox  = new Joystick(2);
		
		jogElevatorTop = new JoystickButton(xbox, 9);
		jogElevatorScaleHigh = new JoystickButton(xbox, 4);
		jogElevatorScaleMiddle = new JoystickButton(xbox, 2);
		jogElevatorScaleLow = new JoystickButton(xbox, 1);
		jogElevatorSwitch = new JoystickButton(xbox, 3);
		jogElevatorBottom = new JoystickButton(xbox, 10);
		
		drivetrain_Shift = new JoystickButton(wheel, 5);
		
		openIntake = new JoystickButton(xbox, 6);
		intakeCube = new JoystickButton(xbox, 5);
				
		dropCube = new Trigger() {
			@Override
			public boolean get() {
				return xbox.getPOV() == 180;
			}
		};
		
		shootCube = new Trigger() {
			
			@Override
			public boolean get() {
				return xbox.getPOV() == 0;
			}
		};
		
		tuneUp = new Trigger() {
			
			@Override
			public boolean get() {
				return xbox.getRawAxis(3) > 0.7;
			}
		};
		
		tuneDown = new Trigger() {
			@Override
			public boolean get() {
				return xbox.getRawAxis(2) > 0.7;
			}
		};
				
				
		jogElevatorTop.whenPressed(new RunElevator(ElevatorState.TOP));
		jogElevatorScaleHigh.whenPressed(new RunElevator(ElevatorState.SCALE_MAX));
		jogElevatorScaleMiddle.whenPressed(new RunElevator(ElevatorState.SCALE_MED));
		jogElevatorScaleLow.whenPressed(new RunElevator(ElevatorState.SCALE_LOW));
		jogElevatorSwitch.whenPressed(new RunElevator(ElevatorState.SWITCH));
		jogElevatorBottom.whenPressed(new RunElevator(ElevatorState.BOTTOM));
		
		dropCube.whenActive(new DropCube());
		shootCube.whenActive(new ShootCube());
		
		drivetrain_Shift.whenPressed(new ShiftLow());
		drivetrain_Shift.whenReleased(new ShiftHigh());
		
		openIntake.whenPressed(new OpenGrabbers());
		openIntake.whenReleased(new CloseGrabbers());
		
		intakeCube.whenPressed(new RunIntakeWheels(1));
		intakeCube.whenReleased(new RunIntakeWheels(0));
		
		tuneUp.whenActive(new IncrementAdjustElevator());
		tuneDown.whenActive(new DecrementAdjustElevator());
	}
	
	@Override
	public double getLeft() {
		return wheel.getRawAxis(0);
	}
	
	@Override public double getFwd() {
		return -stick.getRawAxis(1);
	}
	
	public double getElevator() {
		return -xbox.getRawAxis(1);
	}
	
	public double getLiftIntake() {
		return stick.getRawAxis(2);
	}
	
	public double getRawElevator() {
		return stick.getRawAxis(5);
	}
	public boolean getTestButton() {
		return wheel.getRawButton(4);
	}
}
