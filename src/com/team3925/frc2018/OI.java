package com.team3925.frc2018;

import com.team3925.frc2018.commands.DriveManual.DriveManualInput;
import com.team3925.frc2018.commands.OpenGrabbers;
import com.team3925.frc2018.commands.ReverseIntakeWheels;
import com.team3925.frc2018.commands.RunIntakeWheels;
import com.team3925.frc2018.commands.ShiftHigh;
import com.team3925.frc2018.commands.ShiftLow;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class OI implements DriveManualInput{
	
	private final Joystick wheel;
	private final Joystick xbox;
	private final Joystick stick;
	
	private static OI instance;
	
	private Button grab;
	private Button suck;
	private Button shoot;
	private Button shift;
	
	public static OI getInstance() {
		if (instance == null)
			instance = new OI();
		return instance;
	}
	
	private OI() {
		stick = new Joystick(0);
		wheel = new Joystick(1);
		xbox  = new Joystick(2);
		
		grab = new JoystickButton(xbox, 1);
		suck = new JoystickButton(xbox, 2);
		shoot = new JoystickButton(xbox, 4);
		
		
		shift = new JoystickButton(wheel, 5);
		
		shoot.whileHeld(new ReverseIntakeWheels());
		suck.whileHeld(new RunIntakeWheels());
		grab.whileHeld(new OpenGrabbers());
		shift.whileHeld(new ShiftLow());
		shift.whenInactive(new ShiftHigh());
	}
	
	@Override
	public double getLeft() {
		return wheel.getRawAxis(0);
	}
	
	@Override public double getFwd() {
		return -stick.getRawAxis(1);
	}
	
	public double getElevator() {
		return -xbox.getRawAxis(5);
	}
	
	public double getRawElevator() {
		return -xbox.getRawAxis(1);
	}
}
