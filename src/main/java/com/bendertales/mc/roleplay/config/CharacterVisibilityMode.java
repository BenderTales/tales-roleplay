package com.bendertales.mc.roleplay.config;

public enum CharacterVisibilityMode {

	/**
	 * Every player can see this character
	 */
	ALL,
	/**
	 * No player except the ones listed can see this character
	 */
	WHITELIST,
	/**
	 * Every player exception the one listed can see this character
	 */
	BLACKLIST
}
