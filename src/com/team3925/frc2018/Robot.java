package com.team3925.frc2018;

import com.team3925.frc2018.commands.DriveManual;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;

public class Robot extends IterativeRobot{
	DriveManual drive;
	
	@Override
	public void robotInit() {
		drive = new DriveManual(OI.getInstance());
	}
	
	@Override
	public void teleopInit() {
		drive.start();
	}
	
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
	}
}
