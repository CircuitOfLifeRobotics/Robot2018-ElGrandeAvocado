package com.team3925.frc2018;

import com.team3925.frc2018.commands.DriveManual;
import com.team3925.frc2018.commands.RunElevator;
import com.team3925.frc2018.subsystems.Drivetrain;
import com.team3925.frc2018.subsystems.Elevator;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;

public class Robot extends IterativeRobot {
	DriveManual drive;
	RunElevator elevate;

	@Override
	public void robotInit() {
		drive = new DriveManual(OI.getInstance());
		elevate = new RunElevator();
	}

	@Override
	public void teleopInit() {
		drive.start();
		elevate.start();
	}
	
	@Override
	public void autonomousInit() {
		Elevator.getInstance().zero();
	}
	
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
//		Elevator.getInstance().setHeight(5);
		Elevator.getInstance().setRaw(1);
	}

	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
	}
}
