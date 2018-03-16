package com.team3925.frc2018.commands;

import com.team3925.frc2018.subsystems.Elevator;
import com.team3925.frc2018.subsystems.Elevator.ElevatorState;
import com.team3925.frc2018.subsystems.Intake;
import edu.wpi.first.wpilibj.command.Command;

public class ShootCube extends Command{
	@Override
	protected void initialize() {
		Intake.getInstance().setAngle(0);
	}
	@Override
	protected void execute() {
	}
	
	@Override
	protected void end() {
		Intake.getInstance().setIntakeRollers(-1);
	}
	
	@Override
	protected boolean isFinished() {
		if(Elevator.state == ElevatorState.BOTTOM) {
			return Intake.getInstance().isAtSetpoint();
		}else {
			return true;
		}
	}
}
