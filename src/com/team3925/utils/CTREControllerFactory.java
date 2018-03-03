package com.team3925.utils;

import javax.sound.midi.ControllerEventListener;

import com.ctre.phoenix.ParamEnum;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.IMotorController;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.RemoteFeedbackDevice;
import com.ctre.phoenix.motorcontrol.RemoteLimitSwitchSource;
import com.ctre.phoenix.motorcontrol.RemoteSensorSource;
import com.ctre.phoenix.motorcontrol.SensorTerm;
import com.ctre.phoenix.motorcontrol.can.BaseMotorController;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.team3925.frc2018.Constants;

public class CTREControllerFactory {

	public static WPI_TalonSRX createDefaultTalon(int id) {
		WPI_TalonSRX talon = new WPI_TalonSRX(id);
		applyGenericSettings(talon);
		return talon;
	}

	public static WPI_TalonSRX createPermanentSlaveTalon(int id, IMotorController masterId) {
		final WPI_TalonSRX talon = createDefaultTalon(id);
		talon.set(ControlMode.Follower, masterId.getDeviceID());
		return (WPI_TalonSRX) talon;
	}

	public static WPI_VictorSPX createDefaultVictor(int id) {
		WPI_VictorSPX victor = new WPI_VictorSPX(id);
		applyGenericSettings(victor);
		return new WPI_VictorSPX(id);
	}

	public static WPI_VictorSPX createPermanentSlaveVictor(int id, IMotorController masterId) {
		final WPI_VictorSPX victor = createDefaultVictor(id);
		victor.follow(masterId);
		return victor;
	}

	private static BaseMotorController applyGenericSettings(BaseMotorController controller) {
		controller.config_IntegralZone(0, 0, Constants.TIMEOUT_MS);
		controller.config_kD(0, 0, Constants.TIMEOUT_MS);
		controller.config_kF(0, 0, Constants.TIMEOUT_MS);
		controller.config_kI(0, 0, Constants.TIMEOUT_MS);
		controller.config_kP(0, 0, Constants.TIMEOUT_MS);
		controller.configAllowableClosedloopError(0, 0, Constants.TIMEOUT_MS);
		controller.configClosedloopRamp(0, Constants.TIMEOUT_MS);
		controller.configForwardLimitSwitchSource(RemoteLimitSwitchSource.Deactivated, LimitSwitchNormal.Disabled, 0,
				Constants.TIMEOUT_MS);
		controller.configForwardSoftLimitEnable(false, Constants.TIMEOUT_MS);
		controller.configForwardSoftLimitThreshold(0, Constants.TIMEOUT_MS);
		controller.configMaxIntegralAccumulator(0, 0, Constants.TIMEOUT_MS);
		controller.configMotionAcceleration(0, Constants.TIMEOUT_MS);
		controller.configMotionCruiseVelocity(0, Constants.TIMEOUT_MS);
		controller.configNeutralDeadband(0, Constants.TIMEOUT_MS);
		controller.configNominalOutputForward(0, Constants.TIMEOUT_MS);
		controller.configNominalOutputReverse(0, Constants.TIMEOUT_MS);
		controller.configOpenloopRamp(0, Constants.TIMEOUT_MS);
		controller.configPeakOutputForward(0, Constants.TIMEOUT_MS);
		controller.configPeakOutputReverse(0, Constants.TIMEOUT_MS);
		controller.configRemoteFeedbackFilter(0, RemoteSensorSource.Off, 0, Constants.TIMEOUT_MS);
		controller.configReverseLimitSwitchSource(RemoteLimitSwitchSource.Deactivated, LimitSwitchNormal.Disabled,
				0, Constants.TIMEOUT_MS);
		controller.configReverseSoftLimitThreshold(0, Constants.TIMEOUT_MS);
		controller.configSelectedFeedbackSensor(RemoteFeedbackDevice.None, 0, Constants.TIMEOUT_MS);
		controller.configSensorTerm(SensorTerm.Diff0, FeedbackDevice.None, Constants.TIMEOUT_MS);
		controller.configSetCustomParam(0, 0, Constants.TIMEOUT_MS);
		controller.configVoltageCompSaturation(0, Constants.TIMEOUT_MS);
		controller.configVoltageMeasurementFilter(0, Constants.TIMEOUT_MS);
		
		controller.overrideLimitSwitchesEnable(false);
		controller.clearStickyFaults(Constants.TIMEOUT_MS);
		controller.clearMotionProfileTrajectories();
		controller.enableHeadingHold(false);
		controller.enableVoltageCompensation(false);
		controller.overrideSoftLimitsEnable(false);
		controller.setInverted(false);
		
		return controller;
	}
}
