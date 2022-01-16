package com.bendertales.mc.roleplay.config;

public class CharacterProperties {

	private String name;
	private CharacterVisibilityMode visibilityMode;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String formatName() {
		if (name == null) {
			return "No name";
		}
		return name;
	}

	public CharacterVisibilityMode getVisibilityMode() {
		return visibilityMode;
	}

	public void setVisibilityMode(CharacterVisibilityMode visibilityMode) {
		this.visibilityMode = visibilityMode;
	}


}
