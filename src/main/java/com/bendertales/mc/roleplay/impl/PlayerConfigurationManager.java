package com.bendertales.mc.roleplay.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.UUID;

import com.bendertales.mc.roleplay.RolePlayConstants;
import com.bendertales.mc.roleplay.config.PlayerConfiguration;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.network.ServerPlayerEntity;
import org.jetbrains.annotations.NotNull;


public class PlayerConfigurationManager {

	private final Map<UUID, PlayerConfiguration> playerConfigurationByUid = new Object2ObjectOpenHashMap<>();
	private final Path                           dataFolder;

	public PlayerConfigurationManager() {
		dataFolder = FabricLoader.getInstance().getGameDir()
		                         .resolve("mods").resolve(RolePlayConstants.MODID).resolve("players");
	}

	public void clearSettings() {
		playerConfigurationByUid.clear();
	}

	public PlayerConfiguration getOrCreatePlayerConfiguration(ServerPlayerEntity player) {
		return playerConfigurationByUid.computeIfAbsent(player.getUuid(), id -> tryReadPlayerConfiguration(player));
	}

	public void savePlayerConfiguration(ServerPlayerEntity player) {
		var configuration = getOrCreatePlayerConfiguration(player);
		savePlayerConfiguration(player, configuration);
	}

	public void savePlayerConfiguration(ServerPlayerEntity player, PlayerConfiguration config) {
		tryWritePlayerConfiguration(player, config);
	}

	private PlayerConfiguration tryReadPlayerConfiguration(ServerPlayerEntity player) {
		try {
			return readPlayerConfiguration(player);
		}
		catch (IOException e) {
			return defaultConfiguration();
		}
	}


	private PlayerConfiguration readPlayerConfiguration(ServerPlayerEntity player) throws IOException {
		var playerFile = dataFolder.resolve(player.getUuid().toString() + ".json");
		if (Files.exists(playerFile)) {
			var playerJson = Files.readString(playerFile);
			var gson = getGson();
			return gson.fromJson(playerJson, PlayerConfiguration.class);
		}

		return defaultConfiguration();
	}

	private void tryWritePlayerConfiguration(ServerPlayerEntity player, PlayerConfiguration playerConfiguration) {
		try {
			writePlayerConfiguration(player, playerConfiguration);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void writePlayerConfiguration(ServerPlayerEntity player, PlayerConfiguration playerConfiguration)
	throws IOException {
		if (!Files.exists(dataFolder)) {
			Files.createDirectories(dataFolder);
		}

		var playerFile = dataFolder.resolve(player.getUuid().toString() + ".json");
		var gson = getGson();
		var json = gson.toJson(playerConfiguration);
		Files.writeString(playerFile, json);
	}

	private PlayerConfiguration defaultConfiguration() {
		return new PlayerConfiguration();
	}

	@NotNull
	private Gson getGson() {
		return new GsonBuilder()
				.setPrettyPrinting()
				.create();
	}

}
