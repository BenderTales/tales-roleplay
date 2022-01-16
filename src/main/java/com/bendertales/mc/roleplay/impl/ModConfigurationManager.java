package com.bendertales.mc.roleplay.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import com.bendertales.mc.roleplay.RolePlayConstants;
import com.bendertales.mc.roleplay.config.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;
import org.jetbrains.annotations.NotNull;


public class ModConfigurationManager {

	private final Path configFile;

	public ModConfigurationManager() {
		configFile = FabricLoader.getInstance().getConfigDir()
		                         .resolve(RolePlayConstants.MODID).resolve("config.json");
	}

	public ModConfiguration load() {
		var modConfiguration = tryReadConfiguration();
		fix(modConfiguration);
		createIfNecessary(modConfiguration);

		return modConfiguration;
	}

	private void fix(ModConfiguration modConfiguration) {
		modConfiguration.mergeFrom(defaultConfiguration());
	}

	private void createIfNecessary(ModConfiguration modConfiguration) {
		if (Files.exists(configFile)) {
			tryWriteConfiguration(modConfiguration);
		}
	}

	private ModConfiguration tryReadConfiguration() {
		try {
			return readConfiguration();
		}
		catch (IOException e) {
			e.printStackTrace();
			return defaultConfiguration();
		}
	}

	private ModConfiguration readConfiguration() throws IOException {
		if (!Files.exists(configFile)) {
			return defaultConfiguration();
		}

		var fileContent = Files.readString(configFile);
		Gson gson = getGson();
		return gson.fromJson(fileContent, ModConfiguration.class);
	}

	private void tryWriteConfiguration(ModConfiguration modConfiguration) {
		try {
			writeConfiguration(modConfiguration);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void writeConfiguration(ModConfiguration modConfiguration) throws IOException {
		if (!Files.exists(configFile)) {
			Files.createDirectories(configFile.getParent());
		}

		var gson = getGson();
		var configurationJson = gson.toJson(modConfiguration, ModConfiguration.class);
		Files.writeString(configFile, configurationJson);
	}

	@NotNull
	private Gson getGson() {
		return new GsonBuilder()
				.setPrettyPrinting()
				.create();
	}

	private ModConfiguration defaultConfiguration() {
		var rollProperties = new RollProperties();
		rollProperties.setColor("§6");
		rollProperties.setDistance(30);

		var newsProperties = new NewsProperties();
		newsProperties.setFormat("Town crier: %MESSAGE%");
		newsProperties.setDistance(80);

		var meProperties = new MeProperties();
		meProperties.setDistance(30);
		meProperties.setColor("§6");

		var say = new ChannelProperties();
		say.setDistance(30);

		var yell = new ChannelProperties();
		yell.setDistance(80);

		var whisper = new ChannelProperties();
		whisper.setDistance(5);

		var channels = new ChannelsProperties();
		channels.setSay(say);
		channels.setYell(yell);
		channels.setWhisper(whisper);

		var modConfiguration = new ModConfiguration();
		modConfiguration.setRollDice(rollProperties);
		modConfiguration.setNews(newsProperties);
		modConfiguration.setMe(meProperties);
		modConfiguration.setChannels(channels);

		return modConfiguration;
	}
}
