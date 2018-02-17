package com.team3925.utils;

import edu.wpi.first.wpilibj.Timer;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;

public class GnarlyController {

	boolean leftSide;
	double gyro_offset;

	int encoder_offset, encoder_tick_count;
	double wheel_circumference;

	double kp, ki, kd, kv, ka, kg;

	double last_error, last_time;
	
	double[] position_log = new double[2];
	double[] angle_log = new double[2];
	double[] velocity_log = new double[2];
	
	int segment;
	Trajectory trajectory;

	public GnarlyController(Trajectory traj) {
		this.trajectory = traj;
	}

	public void setTrajectory(Trajectory traj) {
		this.trajectory = traj;
		reset();
	}

	/**
	 * Configure the PID/VA Variables for the Follower
	 * 
	 * @param kp
	 *            The proportional term. This is usually quite high (0.8 - 1.0 are
	 *            common values)
	 * @param ki
	 *            The integral term. Currently unused.
	 * @param kd
	 *            The derivative term. Adjust this if you are unhappy with the
	 *            tracking of the follower. 0.0 is the default
	 * @param kv
	 *            The velocity ratio. This should be 1 over your maximum velocity @
	 *            100% throttle. This converts m/s given by the algorithm to a scale
	 *            of -1..1 to be used by your motor controllers
	 * @param ka
	 *            The acceleration term. Adjust this if you want to reach higher or
	 *            lower speeds faster. 0.0 is the default
	 * @param kg
	 *            The gyro term. This makes your robot more aggressively track its
	 *            heading.
	 */
	public void configurePIDVAG(double kp, double ki, double kd, double kv, double ka, double kg) {
		this.kp = kp;
		this.ki = ki;
		this.kd = kd;
		this.kv = kv;
		this.ka = ka;
		this.kg = kg;
	}

	/**
	 * Configure the Encoders and gyro being used in the follower.
	 * 
	 * @param initial_position
	 *            The initial 'offset' of your encoder. This should be set to the
	 *            encoder value just before you start to track
	 * @param ticks_per_revolution
	 *            How many ticks per revolution the encoder has
	 * @param wheel_diameter
	 *            The diameter of your wheels (or pulleys for track systems) in
	 *            meters
	 * @param leftSide
	 *            Used to calculate gyro heading in closed loop mode
	 * @param initial_position
	 *            The initial 'offset' of your gyro. This should be set to the gyro
	 *            value just before you start to track
	 */
	public void config(int initial_position, int ticks_per_revolution, double wheel_diameter, boolean leftSide, double initial_angle) {
		if (leftSide) {
			this.leftSide = true;
		} else {
			this.leftSide = false;
		}
		this.encoder_offset = initial_position;
		this.encoder_tick_count = ticks_per_revolution;
		this.wheel_circumference = Math.PI * wheel_diameter;
		this.gyro_offset = initial_angle;
	}

	
	/**
	 * Reset the follower to start again. Encoders must be reconfigured.
	 */
	public void reset() {
		last_error = 0;
		segment = 0;
	}

	/**
	 * Calculate the desired output for the motors, based on the amount of ticks the
	 * encoder has gone through. This does not account for heading of the robot. To
	 * account for heading, add some extra terms in your control loop for
	 * realignment based on gyroscope input and the desired heading given by this
	 * object.
	 * 
	 * @param encoder_tick
	 *            The amount of ticks the encoder has currently measured.
	 * @param gyro_heading
	 *            The angle the gyro currently measures.
	 * @return The desired output for your motor controller
	 */

	public double calculate(int encoder_tick, double gyro_heading, double encoder_vel_tpr) {
		// Number of Revolutions * Wheel Circumference

		if (last_time == 0) {
			last_time = Timer.getFPGATimestamp();
		}

		if (segment < trajectory.length()) {

			Trajectory.Segment seg = trajectory.get(segment);

			double distance_covered = ((encoder_tick - encoder_offset) / encoder_tick_count) * wheel_circumference;
			double enc_vel = encoder_vel_tpr / encoder_tick_count * wheel_circumference * 10;

			double positionError = seg.position - distance_covered;
			double velocityError = seg.velocity - enc_vel;
			// Clockwise creates a negative heading
			double headingError = Pathfinder.r2d(seg.heading) - Pathfinder.boundHalfDegrees(gyro_heading - gyro_offset);

			// Loop is Velocity_Set_Point + Gyro_Error + Position_Error + Velocity_Error
			double calculated_value = seg.velocity + seg.acceleration * ka + (leftSide ? -1.0 : 1.0) * kg * headingError + kp * positionError + kv * velocityError;
			// Convert Ft/S to RPM
			double converted_value = calculated_value / 10 / wheel_circumference * encoder_tick_count;

			//LOG
			position_log[0] = seg.position;
			position_log[1] = distance_covered;
			angle_log[0] = Pathfinder.r2d(seg.heading);
			angle_log[1] = Pathfinder.boundHalfDegrees(gyro_heading - gyro_offset);
			velocity_log[0] = seg.velocity;
			velocity_log[1] = enc_vel;
			
			
			double dt = Timer.getFPGATimestamp() - last_time;
			if (dt >= seg.dt) {
				segment++;
				last_time = Timer.getFPGATimestamp();
			}
			return converted_value;
		} else {
			return 0;
		}
	}

	public double[] logPosition() {
		return position_log;
	}
	
	public double[] logAngle() {
		return angle_log;
	}
	
	public double[] logVelocity() {
		return velocity_log;
	}
	
	/**
	 * @return the current segment being operated on
	 */
	public Trajectory.Segment getSegment() {
		return trajectory.get(segment);
	}

	/**
	 * @return whether we have finished tracking this trajectory or not.
	 */
	public boolean isFinished() {
		return segment >= trajectory.length();
	}

}
