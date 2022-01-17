package com.bendertales.mc.roleplay.config;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;


public enum CharacterVisibilityMode {

	CAN_SEE,
	CANNOT_SEE;

	private static final Map<String, CharacterVisibilityMode> BY_NAME;

	public static CharacterVisibilityMode byName(String name) {
		if (name == null) {
			return null;
		}
		return BY_NAME.get(name.toUpperCase());
	}

	static {
		BY_NAME = Arrays.stream(CharacterVisibilityMode.values()).collect(Collectors.toMap(Enum::name, v -> v));
	}

	public static Collection<String> getNames() {
		return BY_NAME.keySet();
	}

}
