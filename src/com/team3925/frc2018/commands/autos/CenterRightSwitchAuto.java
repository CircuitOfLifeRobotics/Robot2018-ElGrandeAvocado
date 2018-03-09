package com.team3925.frc2018.commands.autos;

import com.team3925.frc2018.commands.OpenGrabbers;
import com.team3925.frc2018.commands.RunElevator;
import com.team3925.frc2018.commands.RunIntakeWheels;
import com.team3925.frc2018.commands.ZeroIntake;
import com.team3925.frc2018.subsystems.Elevator.ElevatorState;
import com.team3925.utils.MotionProfileCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

public class CenterRightSwitchAuto extends CommandGroup{
	
	public CenterRightSwitchAuto() {
		addSequential(new ZeroIntake());
		addParallel(new RunElevator(ElevatorState.BOTTOM));
		addParallel(new MotionProfileCommand("CENTER_RIGHTSWITCH"));
		addSequential(new WaitCommand(2));
		addSequential(new RunElevator(ElevatorState.SWITCH));
		addSequential(new OpenGrabbers());
		addSequential(new RunIntakeWheels(-0.5));
	}

}

