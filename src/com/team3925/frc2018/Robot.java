package com.team3925.frc2018;

import com.team3925.frc2018.commands.DriveManual;
import com.team3925.utils.MotionProfileCommand;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;

public class Robot extends IterativeRobot {
	DriveManual drive;
	MotionProfileCommand test;

	@Override
	public void robotInit() {
		drive = new DriveManual(OI.getInstance());
		test = new MotionProfileCommand("testAuto");
	}

	public void teleopInit() {
		drive.start();
	}

	@Override
	public void autonomousInit() {
		test.start();
	}

	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void robotPeriodic() {
		Logger.getInstance().update();
	}

	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
	}
}
