package com.team3925.frc2018.commands.autos;

import com.team3925.frc2018.commands.CloseGrabbers;
import com.team3925.frc2018.commands.OpenGrabbers;
import com.team3925.frc2018.commands.RunElevator;
import com.team3925.frc2018.commands.RunIntakeWheels;
import com.team3925.frc2018.commands.ZeroIntake;
import com.team3925.frc2018.subsystems.Elevator.ElevatorState;
import com.team3925.utils.MotionProfileCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

public class CenterSwitchAuto extends CommandGroup{
	
	public enum AutoSide{
		LEFT,RIGHT
	}
	
	public CenterSwitchAuto(AutoSide side) {
		addParallel(new CloseGrabbers());
		addParallel(new RunElevator(ElevatorState.SWITCH));
		if (side == AutoSide.LEFT) {
			addParallel(new MotionProfileCommand("CENTER_LEFTSWITCH"));
		}else {
			addParallel(new MotionProfileCommand("CENTER_RIGHTSWITCH"));
		}
		addSequential(new WaitCommand(2));
		addSequential(new OpenGrabbers());
		addSequential(new RunIntakeWheels(-0.5));
	}

}

