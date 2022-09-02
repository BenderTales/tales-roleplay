package com.bendertales.mc.roleplay.impl;

import java.util.List;

import com.bendertales.mc.roleplay.RolePlayConstants;
import com.bendertales.mc.roleplay.data.PlayerConfiguration;
import com.bendertales.mc.talesservercommon.repository.data.AbstractPlayerDataRepository;
import com.bendertales.mc.talesservercommon.repository.serialization.JsonSerializerRegistration;


public class PlayerDataRepository extends AbstractPlayerDataRepository<PlayerConfiguration, PlayerConfiguration> {

	public PlayerDataRepository() {
		super(RolePlayConstants.MODID, PlayerConfiguration.class);
	}

	@Override
	protected List<JsonSerializerRegistration<?>> getSerializers() {
		return List.of();
	}

	@Override
	protected PlayerConfiguration convert(PlayerConfiguration playerConfiguration) {
		return playerConfiguration;
	}

	@Override
	protected PlayerConfiguration deconvert(PlayerConfiguration playerConfiguration) {
		return playerConfiguration;
	}

	@Override
	protected PlayerConfiguration getDefaultConfiguration() {
		return new PlayerConfiguration();
	}
}
