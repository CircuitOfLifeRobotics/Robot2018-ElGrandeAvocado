package com.team3925.frc2018.commands;

import com.team3925.frc2018.subsystems.Arm;
import com.team3925.frc2018.subsystems.Arm.ArmState;
import com.team3925.frc2018.subsystems.Elevator;
import com.team3925.frc2018.subsystems.Elevator.ElevatorState;
import com.team3925.frc2018.subsystems.Intake;
import com.team3925.frc2018.subsystems.Intake.IntakeState;
import edu.wpi.first.wpilibj.command.Command;

public class SetSuperStructureState extends Command {

	ElevatorState elevatorState;
	ArmState armState;
	IntakeState intakeState;

	public SetSuperStructureState(ElevatorState elevatorState, ArmState armState, IntakeState intakeState) {
		requires(Elevator.getInstance());
		requires(Intake.getInstance());
		requires(Arm.getInstance());

		this.elevatorState = elevatorState;
		this.armState = armState;
		this.intakeState = intakeState;
	}
	
	@Override
	protected void initialize() {
		Elevator.getInstance().setPosition(elevatorState);
		Arm.getInstance().setSetpoint(armState);
	}
	
	@Override
	protected void end() {
		Intake.getInstance().setState(intakeState);
	}
	
	@Override
	protected boolean isFinished() {
		if (intakeState != IntakeState.HOLD || intakeState != IntakeState.UNKNOWN) {	
			return Elevator.getInstance().atSetpoint();
		}else {
			return true;
		}
	}

}
