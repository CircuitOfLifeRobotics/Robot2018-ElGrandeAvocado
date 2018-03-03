package com.team3925.frc2018.commands;

import com.team3925.frc2018.subsystems.Intake;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class RunIntakeWheels extends Command{
	
	@Override
	protected void initialize() {
		Intake.getInstance().setIntakeRollers(0.6);
	}
	
	@Override
	protected void end() {
		Intake.getInstance().setIntakeRollers(0);
		SmartDashboard.putNumber("Intake Left Current", Intake.getInstance().getLeftCurrent());
		SmartDashboard.putNumber("Intake Right Current", Intake.getInstance().getRightCurrent());

	}

	@Override
	protected boolean isFinished() {
		return false;
	}

}
