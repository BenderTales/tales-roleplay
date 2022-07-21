package com.bendertales.mc.roleplay.data;

import java.util.Map;
import java.util.UUID;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.server.network.ServerPlayerEntity;


public class Character {

	private final Map<UUID, OtherPlayerPerception> playerPerceptions = new Object2ObjectOpenHashMap<>();

	private String                   name;
	private CharacterVisibilityMode  defaultVisibility;
	private CharacterReadabilityMode defaultReadability;

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

	public CharacterVisibilityMode getDefaultVisibility() {
		return defaultVisibility;
	}

	public void setDefaultVisibility(CharacterVisibilityMode defaultVisibility) {
		this.defaultVisibility = defaultVisibility;
	}

	public CharacterReadabilityMode getDefaultReadability() {
		return defaultReadability;
	}

	public void setDefaultReadability(CharacterReadabilityMode defaultReadability) {
		this.defaultReadability = defaultReadability;
	}

	public Map<UUID, OtherPlayerPerception> getPlayerPerceptions() {
		return playerPerceptions;
	}

	public CharacterVisibilityMode getPlayerVisibility(ServerPlayerEntity player) {
		var perception = playerPerceptions.get(player.getUuid());
		if (perception == null || perception.getVisibility() == null) {
			return defaultVisibility;
		}

		return perception.getVisibility();
	}

	public CharacterReadabilityMode getPlayerReadability(ServerPlayerEntity player) {
		var perception = playerPerceptions.get(player.getUuid());
		if (perception == null || perception.getReadability() == null) {
			return defaultReadability;
		}

		return perception.getReadability();
	}

	public void setPlayerVisibility(ServerPlayerEntity player, CharacterVisibilityMode visibility) {
		var perception = playerPerceptions.computeIfAbsent(player.getUuid(), id -> new OtherPlayerPerception());
		perception.setVisibility(visibility);
	}

	public void setPlayerReadability(ServerPlayerEntity player, CharacterReadabilityMode readability) {
		var perception = playerPerceptions.computeIfAbsent(player.getUuid(), id -> new OtherPlayerPerception());
		perception.setReadability(readability);
	}

	public void clearPlayerVisibility(ServerPlayerEntity player) {
		var perception = playerPerceptions.get(player.getUuid());
		if (perception != null) {
			if (perception.getReadability() == null) {
				playerPerceptions.remove(player.getUuid());
			}
			else {
				perception.setVisibility(null);
			}
		}
	}

	public void clearPlayerReadability(ServerPlayerEntity player) {
		var perception = playerPerceptions.get(player.getUuid());
		if (perception != null) {
			if (perception.getVisibility() == null) {
				playerPerceptions.remove(player.getUuid());
			}
			else {
				perception.setReadability(null);
			}
		}
	}
}
