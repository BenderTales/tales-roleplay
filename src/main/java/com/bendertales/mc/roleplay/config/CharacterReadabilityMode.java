package com.bendertales.mc.roleplay.config;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;


public enum CharacterReadabilityMode {

	CANNOT_HEAR,
	CANNOT_UNDERSTAND_WITH_MAGIC,
	CANNOT_UNDERSTAND,
	CAN_UNDERSTAND;

	private static final Map<String, CharacterReadabilityMode> BY_NAME;

	public static CharacterReadabilityMode byName(String name) {
		if (name == null) {
			return null;
		}
		return BY_NAME.get(name.toUpperCase());
	}

	static {
		BY_NAME = Arrays.stream(CharacterReadabilityMode.values()).collect(Collectors.toMap(Enum::name, v -> v));
	}

	public static Collection<String> getNames() {
		return BY_NAME.keySet();
	}

}
