package com.team3925.frc2018.commands;

import com.team3925.frc2018.OI;
import com.team3925.frc2018.subsystems.Elevator;
import com.team3925.frc2018.subsystems.Intake;
import com.team3925.frc2018.subsystems.Elevator.ElevatorState;

import edu.wpi.first.wpilibj.command.Command;

public class RunElevator extends Command {

	private ElevatorState state;

	public RunElevator(ElevatorState state) {
		this.state = state;
		requires(Elevator.getInstance());
	}

	@Override
	protected void initialize() {
		Elevator.getInstance().setPosition(state);
	}

	@Override
	protected boolean isFinished() {
		return true;
	}
	
	@Override
	protected void end() {
		Intake.getInstance().setAngle(0);
	}
}