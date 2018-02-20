package com.team3925.frc2018.commands;

import com.team3925.frc2018.OI;
import com.team3925.frc2018.subsystems.Elevator;

import edu.wpi.first.wpilibj.command.Command;

public class RunElevator extends Command{
	
	double height;
	
	private static final double ELEVATOR_SENSITIVITY = 0.01;
	
	public RunElevator() {
		height = 0;
		requires(Elevator.getInstance());
	}
	
	@Override
	protected void execute() {
		Elevator.getInstance().setHeight(height);
		height += OI.getInstance().getElevator() * ELEVATOR_SENSITIVITY;
	}

	@Override
	protected boolean isFinished() {
		return false;
	}
}