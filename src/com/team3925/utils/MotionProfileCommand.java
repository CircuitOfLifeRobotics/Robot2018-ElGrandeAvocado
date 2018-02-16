package com.team3925.utils;

import com.team3925.frc2018.subsystems.Drivetrain;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.modifiers.TankModifier;

/**
 *
 */
public class MotionProfileCommand extends Command {

	TankModifier modifier;
	
	Trajectory trajectory;
	
	GnarlyController left;
	GnarlyController right;

	Waypoint[] points;

	// TUNE THESE VALUES
	public static final double kP = 0.05; //P and V were 0.5
	public static final double kV = 3.25;
	public static final double kG = 0;

	public static final double MAX_VEL = 5.5;
	public static final double MAX_ACL = 3;
	public static final double MAX_JERK = 200;

	public static final double WHEEL_BASE_WIDTH = 2.2;
	public static final int ENC_TPR = 1024;
	public static final double WHEEL_DI = 0.33;
	
	

	public MotionProfileCommand(Waypoint[] points) {
		Trajectory.Config config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.05, MAX_VEL, MAX_ACL, MAX_JERK);
		this.points = points;
		trajectory = Pathfinder.generate(this.points, config);
		modifier = new TankModifier(trajectory).modify(WHEEL_BASE_WIDTH);
		requires(Drivetrain.getInstance());
	}

	// Called just before this Command runs the first time
	protected void initialize() {

		left = new GnarlyController(modifier.getLeftTrajectory());
		right = new GnarlyController(modifier.getRightTrajectory());

		left.config((int) Drivetrain.getInstance().getLeftEncoderPosition(), ENC_TPR, WHEEL_DI, true, Drivetrain.getInstance().getGyroHeading());
		right.config((int) Drivetrain.getInstance().getRightEncoderPosition(), ENC_TPR, WHEEL_DI, false, Drivetrain.getInstance().getGyroHeading());

		left.configurePIDVAG(kP, 0, 0, kV, 0, kG);
		right.configurePIDVAG(kP, 0, 0, kV, 0, kG);

	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		Drivetrain.getInstance().setVelocity(
				left.calculate(
						(int) Drivetrain.getInstance().getLeftEncoderPosition(), Drivetrain.getInstance().getGyroHeading(), Drivetrain.getInstance().getLeftSpeed()), 
				right.calculate(
						(int) Drivetrain.getInstance().getRightEncoderPosition(), Drivetrain.getInstance().getGyroHeading(), Drivetrain.getInstance().getRightSpeed())
				);
		logData();
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		if (left.isFinished() && right.isFinished()) {
			return true;
		} else {
			return false;
		}
	}

	// Called once after isFinished returns true
	protected void end() {
		Drivetrain.getInstance().setRaw(0, 0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		this.end();
	}
	
	public void logData() {
		SmartDashboard.putNumber("Left Position Predicted", left.logPosition()[0]);
		SmartDashboard.putNumber("Left Position Actual", left.logPosition()[1]);
		
		SmartDashboard.putNumber("Left Velocity Predicted", left.logVelocity()[0]);
		SmartDashboard.putNumber("Left Velocity Actual", left.logVelocity()[1]);
		
		
		SmartDashboard.putNumber("Right Position Predicted", right.logPosition()[0]);
		SmartDashboard.putNumber("Right Position Actual", right.logPosition()[1]);
		
		SmartDashboard.putNumber("Right Velocity Predicted", right.logVelocity()[0]);
		SmartDashboard.putNumber("Right Velocity Actual", right.logVelocity()[1]);
	}
	
}
