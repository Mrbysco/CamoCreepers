package com.mrbysco.camocreepers.platform.services;

public interface IPlatformHelper {
	/**
	 * @return if `netherCamo` is true in the config
	 */
	boolean showNetherCamo();

	/**
	 * @return if `endCamo` is true in the config
	 */
	boolean showEndCamo();

	/**
	 * @return if `caveCamo` is true in the config
	 */
	boolean showCaveCamo();
}
