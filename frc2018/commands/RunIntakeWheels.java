package com.team3925.frc2018.commands;

import com.team3925.frc2018.subsystems.Intake;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class RunIntakeWheels extends Command{
	
	private double speed;
	
	public RunIntakeWheels(double speed) {
		this.speed = speed;
	}
	
	@Override
	protected void initialize() {
		Intake.getInstance().setIntakeRollers(speed);
	}
	
	@Override
	protected boolean isFinished() {
		return true;
	}

}
