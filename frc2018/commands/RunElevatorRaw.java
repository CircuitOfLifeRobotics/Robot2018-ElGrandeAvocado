package com.team3925.frc2018.commands;

import com.team3925.frc2018.OI;
import com.team3925.frc2018.subsystems.Elevator;

import edu.wpi.first.wpilibj.command.Command;

public class RunElevatorRaw extends Command {


	@Override
	protected void execute() {
		if (Math.abs(OI.getInstance().getRawElevator()) > 0.2){
			Elevator.getInstance().setRaw(OI.getInstance().getRawElevator());
		}
	}

	@Override
	protected boolean isFinished() {
		return false;
	}
}