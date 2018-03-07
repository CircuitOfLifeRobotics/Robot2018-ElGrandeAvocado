package com.team3925.frc2018.commands;

import com.team3925.frc2018.subsystems.Elevator;

import edu.wpi.first.wpilibj.command.Command;

public class DecrementAdjustElevator extends Command{
	
	
	@Override
	protected void initialize() {
		Elevator.getInstance().setPosition(Elevator.elevatorHeight - Elevator.TELEOP_ELEVATOR_INCREMENT);
	}

	@Override
	protected boolean isFinished() {
		return true;
	}

}
