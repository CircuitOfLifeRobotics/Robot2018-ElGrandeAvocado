package com.team3925.frc2018.commands.autos;

import com.team3925.frc2018.commands.ZeroIntake;
import com.team3925.utils.MotionProfileCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class DriveForwardAuto extends CommandGroup{
	
	public DriveForwardAuto() {
		addParallel(new ZeroIntake());
		addParallel(new MotionProfileCommand("DRIVE_FORWARD"));
	}

}
