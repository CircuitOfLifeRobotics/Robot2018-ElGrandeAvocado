package com.team3925.frc2018.commands;

import com.team3925.frc2018.subsystems.Elevator;

import edu.wpi.first.wpilibj.command.Command;

public class TuneElevator extends Command {

	boolean up;

	public TuneElevator(boolean tuneUp) {
		up = tuneUp;
	}

	@Override
	protected boolean isFinished() {
		if (up) {
			Elevator.getInstance().incrementHeight();
		} else {
			Elevator.getInstance().decrementHeight();
		}
		return true;
	}

}
