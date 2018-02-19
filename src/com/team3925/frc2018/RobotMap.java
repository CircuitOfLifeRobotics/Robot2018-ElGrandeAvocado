package com.team3925.frc2018;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.team3925.utils.CTREControllerFactory;

import edu.wpi.first.wpilibj.DoubleSolenoid;

public class RobotMap {
	
	public static final class DrivetrainMap{
		public static final WPI_TalonSRX LEFT_MASTER = CTREControllerFactory.createDefaultTalon(4);
		public static final WPI_TalonSRX LEFT_SLAVE_A = CTREControllerFactory.createPermanentSlaveTalon(5, LEFT_MASTER);
		public static final WPI_VictorSPX LEFT_SLAVE_B = CTREControllerFactory.createPermanentSlaveVictor(6, LEFT_MASTER);
		
		public static final WPI_TalonSRX RIGHT_MASTER = CTREControllerFactory.createDefaultTalon(1);
		public static final WPI_TalonSRX RIGHT_SLAVE_A = CTREControllerFactory.createPermanentSlaveTalon(2, RIGHT_MASTER);
		public static final WPI_VictorSPX RIGHT_SLAVE_B = CTREControllerFactory.createPermanentSlaveVictor(3, RIGHT_MASTER);
		
		public static final DoubleSolenoid SHIFT_SOLENOID = new DoubleSolenoid(0, 1);
	}
	
	public static final class IntakeMap{
		public static final WPI_TalonSRX LEFT_INTAKE = CTREControllerFactory.createDefaultTalon(11);
		public static final WPI_TalonSRX RIGHT_INTAKE = CTREControllerFactory.createDefaultTalon(12);
		
		public static final WPI_TalonSRX LIFT_MOTOR = CTREControllerFactory.createDefaultTalon(13);
		
		public static final DoubleSolenoid GRAB_SOLENOID = new DoubleSolenoid(6, 7);
	}

	public static final class LiftMap{
		public static final WPI_TalonSRX MASTER = CTREControllerFactory.createDefaultTalon(7);
		public static final WPI_VictorSPX SLAVE  = CTREControllerFactory.createPermanentSlaveVictor(8, MASTER);
	}
	
	public static final class ElevatorMap{
		public static final WPI_TalonSRX MASTER = CTREControllerFactory.createDefaultTalon(9);
		public static final WPI_TalonSRX SLAVE = CTREControllerFactory.createPermanentSlaveTalon(10, MASTER);
		
		public static final DoubleSolenoid GRABBER_SOLENOID = new DoubleSolenoid(2, 3);
		public static final DoubleSolenoid SPATULA_SOLENOID = new DoubleSolenoid(4, 5);
	}
}
