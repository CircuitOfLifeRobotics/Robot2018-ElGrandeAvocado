package com.team3925.utils;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.IMotorController;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

public class CTREControllerFactory {
	
	public static WPI_TalonSRX createDefaultTalon(int id) {
		return new WPI_TalonSRX(id); //TODO: Implement full factory
	}

	public static WPI_TalonSRX createPermanentSlaveTalon(int id, IMotorController masterId) {
		final WPI_TalonSRX talon = createDefaultTalon(id);
		talon.set(ControlMode.Follower, masterId.getDeviceID());
		return talon;
	}
	
	public static WPI_VictorSPX createDefaultVictor(int id) {
		return new WPI_VictorSPX(id); //TODO: Implement full factory
	}

	public static WPI_VictorSPX createPermanentSlaveVictor(int id, IMotorController masterId) {
		final WPI_VictorSPX victor = createDefaultVictor(id);
		victor.follow(masterId);
		return victor;
	}

}
