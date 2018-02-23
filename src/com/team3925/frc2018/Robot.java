package com.team3925.frc2018;

import com.team3925.frc2018.commands.DriveManual;
import com.team3925.frc2018.commands.RunElevator;
import com.team3925.frc2018.commands.RunElevatorRaw;
import com.team3925.frc2018.subsystems.Elevator;
import com.team3925.utils.MotionProfileCommand;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;

public class Robot extends IterativeRobot {
	DriveManual drive;
	RunElevatorRaw elevateRaw;
	MotionProfileCommand test;

	@Override
	public void robotInit() {
		drive = new DriveManual(OI.getInstance());
		elevateRaw = new RunElevatorRaw();
		test = new MotionProfileCommand("testAuto");
	}

	@Override
	public void autonomousInit() {
		test.start();
	}
	
	public void teleopInit() {
		Elevator.getInstance().zero();
		drive.start();
		elevateRaw.start();
	}
	
	@Override
	public void robotPeriodic() {
		Logger.getInstance().update();
		Scheduler.getInstance().run();
	}
}
