package com.bendertales.mc.roleplay.impl;

import java.util.List;

import com.bendertales.mc.roleplay.RolePlayConstants;
import com.bendertales.mc.roleplay.config.*;
import com.bendertales.mc.talesservercommon.repository.ModPaths;
import com.bendertales.mc.talesservercommon.repository.config.ConfigRepository;
import com.bendertales.mc.talesservercommon.repository.serialization.CommonSerializers;
import com.bendertales.mc.talesservercommon.repository.serialization.JsonSerializerRegistration;


public class ModSettingsRepository extends ConfigRepository<ModProperties, ModProperties> {

	public ModSettingsRepository() {
		super(ModProperties.class, ModPaths.createConfigFile(RolePlayConstants.MODID));
	}

	@Override
	protected boolean checkFileContent(ModProperties modProperties) {
		modProperties.mergeFrom(getDefaultConfiguration());
		return false;
	}

	@Override
	protected List<JsonSerializerRegistration<?>> getSerializers() {
		return List.of(CommonSerializers.identifier());
	}

	@Override
	protected ModProperties convert(ModProperties modProperties) {
		return modProperties;
	}

	@Override
	protected ModProperties getDefaultConfiguration() {
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

		var modConfiguration = new ModProperties();
		modConfiguration.setRollDice(rollProperties);
		modConfiguration.setNews(newsProperties);
		modConfiguration.setMe(meProperties);
		modConfiguration.setChannels(channels);

		return modConfiguration;
	}
}
