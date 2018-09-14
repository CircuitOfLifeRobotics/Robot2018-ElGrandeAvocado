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

public class CenterSwitchAuto extends CommandGroup{
	
	public enum AutoSide{
		LEFT,RIGHT
	}
	
	public CenterSwitchAuto(AutoSide side) {
		addParallel(new Command() {
			
			@Override
			protected boolean isFinished() {
				// TODO Auto-generated method stub
				Intake.getInstance().setIntakeRollers(0.25);
				return true;
			}
		});
		addParallel(new CloseGrabbers());
		if (side.equals(AutoSide.LEFT)) {
			addSequential(new MotionProfileCommand("CENTER_LEFTSWITCH"));
		}else if (side.equals(AutoSide.RIGHT)){
			addSequential(new MotionProfileCommand("CENTER_RIGHTSWITCH"));
		}
		addSequential(new WaitCommand(4));
		addParallel(new RunElevator(ElevatorState.SWITCH));
		addSequential(new OpenGrabbers());
		addSequential(new RunIntakeWheels(-0.25));
	}

}

