package com.team3925.frc2018.commands;

import com.team3925.frc2018.subsystems.Elevator.ElevatorState;
import com.team3925.utils.MotionProfileCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

public class Center_Left_Switch_Auto extends CommandGroup{
	
	public Center_Left_Switch_Auto() {
		addParallel(new RunElevator(ElevatorState.BOTTOM));
		addParallel(new MotionProfileCommand("CENTER_LEFTSWITCH"));
		addSequential(new WaitCommand(2));
		addSequential(new RunElevator(ElevatorState.SWITCH));
		addSequential(new OpenGrabbers());
		addSequential(new RunIntakeWheels(-0.5));
	}

}
