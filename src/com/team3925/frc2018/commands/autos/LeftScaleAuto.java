package com.team3925.frc2018.commands.autos;

import com.team3925.frc2018.commands.CloseGrabbers;
import com.team3925.frc2018.commands.OpenGrabbers;
import com.team3925.frc2018.commands.RunElevator;
import com.team3925.frc2018.commands.RunIntakeWheels;
import com.team3925.frc2018.commands.ZeroIntake;
import com.team3925.frc2018.subsystems.Intake;
import com.team3925.frc2018.subsystems.Elevator.ElevatorState;
import com.team3925.utils.MotionProfileCommand;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

public class LeftScaleAuto extends CommandGroup {

	public enum AutoSide {
		LEFT, RIGHT
	}

	public LeftScaleAuto() {
		addParallel(new Command() {
			@Override
			protected boolean isFinished() {
				Intake.getInstance().setIntakeRollers(0.25);
				return true;
			}
		});
		addParallel(new CloseGrabbers());
		addParallel(new RunElevator(ElevatorState.SCALE_MAX));
		addSequential(new MotionProfileCommand("LEFT_LEFTSCALE"));
		addSequential(new RunIntakeWheels(-1));
	}

}
