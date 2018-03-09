package com.team3925.frc2018.commands;

import com.team3925.frc2018.subsystems.Elevator;
import com.team3925.frc2018.subsystems.Elevator.ElevatorState;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class SetLiftBottom extends CommandGroup {

	public SetLiftBottom() {

		addSequential(new Command() {

			@Override
			protected void initialize() {
				Elevator.getInstance().setPosition(ElevatorState.BOTTOM);
			}

			@Override
			protected boolean isFinished() {
				return Elevator.getInstance().getElevatorHeightPercentage() > 0.10;
			}
		});

		addSequential(new Command() {

			@Override
			protected boolean isFinished() {
				return Elevator.getInstance().getLimitSwitch();
			}

			@Override
			protected void initialize() {
				Elevator.getInstance().setRaw(-0.2);
			}

			@Override
			protected void end() {
				Elevator.getInstance().setRaw(0);
				Elevator.getInstance().zero();
			}
		});
	}

}
