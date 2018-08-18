package com.team3925.frc2018;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.ctre.phoenix.sensors.PigeonIMU;
import com.team3925.utils.CTREControllerFactory;

import edu.wpi.first.wpilibj.DoubleSolenoid;

public class RobotMap {

	public static final class DrivetrainMap {
		public static final WPI_TalonSRX LEFT_MASTER = CTREControllerFactory.createDefaultTalon(4);
		// public static final WPI_TalonSRX LEFT_SLAVE_A =
		// CTREControllerFactory.createDefaultTalon(5);
		public static final WPI_TalonSRX LEFT_SLAVE_A = CTREControllerFactory.createPermanentSlaveTalon(5, LEFT_MASTER);
		// public static final WPI_VictorSPX LEFT_SLAVE_B =
		// CTREControllerFactory.createDefaultVictor(6);
		public static final WPI_VictorSPX LEFT_SLAVE_B = CTREControllerFactory.createPermanentSlaveVictor(6,
				LEFT_MASTER);

		public static final WPI_TalonSRX RIGHT_MASTER = CTREControllerFactory.createDefaultTalon(1);
		public static final WPI_TalonSRX RIGHT_SLAVE_A = CTREControllerFactory.createPermanentSlaveTalon(2,
				RIGHT_MASTER);
		// public static final WPI_TalonSRX RIGHT_SLAVE_A =
		// CTREControllerFactory.createDefaultTalon(2);
		public static final WPI_VictorSPX RIGHT_SLAVE_B = CTREControllerFactory.createPermanentSlaveVictor(3,
				RIGHT_MASTER);
		// public static final WPI_VictorSPX RIGHT_SLAVE_B =
		// CTREControllerFactory.createDefaultVictor(3);

		public static final PigeonIMU DRIVETRAIN_IMU = new PigeonIMU(LEFT_SLAVE_A);

		public static final DoubleSolenoid SHIFT_SOLENOID = new DoubleSolenoid(0, 1);
	}

	public static final class IntakeMap {
//		public static final WPI_TalonSRX LEFT_INTAKE = CTREControllerFactory.createDefaultTalon(11);
		public static final WPI_VictorSPX RIGHT_INTAKE = CTREControllerFactory.createDefaultVictor(12);
		public static final WPI_TalonSRX LEFT_INTAKE = CTREControllerFactory.createDefaultTalon(11);

		public static final WPI_TalonSRX LIFT_MOTOR = CTREControllerFactory.createDefaultTalon(13);

		public static final DoubleSolenoid GRAB_SOLENOID = new DoubleSolenoid(2, 3);
		public static final DoubleSolenoid SPRING_SOLENOID = new DoubleSolenoid(4,5);
	}

	public static final class ElevatorMap {
		public static final WPI_TalonSRX MASTER = CTREControllerFactory.createDefaultTalon(9);
//		public static final WPI_TalonSRX SLAVE_A = CTREControllerFactory.createDefaultTalon(10);
//		public static final WPI_TalonSRX SLAVE_B = CTREControllerFactory.createDefaultTalon(8);
//		public static final WPI_TalonSRX SLAVE_C = CTREControllerFactory.createDefaultTalon(7);
		public static final WPI_VictorSPX SLAVE_A = CTREControllerFactory.createPermanentSlaveVictor(10, MASTER); //flipped
		public static final WPI_TalonSRX SLAVE_B = CTREControllerFactory.createPermanentSlaveTalon(7, MASTER); //flipped
		public static final WPI_VictorSPX SLAVE_C = CTREControllerFactory.createPermanentSlaveVictor(8, MASTER);//flipped
	}
}
