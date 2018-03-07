package com.team3925.frc2018;

import javax.swing.text.DefaultStyledDocument.ElementBuffer;

import com.team3925.frc2018.commands.Center_Left_Switch_Auto;
import com.team3925.frc2018.commands.DriveManual;
import com.team3925.frc2018.commands.RunElevator;
import com.team3925.frc2018.commands.RunElevatorRaw;
import com.team3925.frc2018.commands.RunIntakeLiftRaw;
import com.team3925.frc2018.commands.ShootCube;
import com.team3925.frc2018.subsystems.Elevator;
import com.team3925.frc2018.subsystems.Elevator.ElevatorState;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;

public class Robot extends IterativeRobot {
	DriveManual drive;
	RunElevator elevate;
	RunElevatorRaw elevateRaw;
	RunIntakeLiftRaw liftRaw;
	Command testAuto;

	@Override
	public void robotInit() {
		drive = new DriveManual(OI.getInstance());
		elevateRaw = new RunElevatorRaw();
		liftRaw = new RunIntakeLiftRaw();
		testAuto = new Center_Left_Switch_Auto();
	}

	@Override
	public void autonomousInit() {
		Elevator.getInstance().zero();
		testAuto.start();
	}
	
	public void teleopInit() {
		Elevator.getInstance().zero();
		drive.start();
	}
	
	@Override
	public void teleopPeriodic() {
	}
	
	@Override
	public void robotPeriodic() {
		Logger.getInstance().update();
		Scheduler.getInstance().run();
	}
}
