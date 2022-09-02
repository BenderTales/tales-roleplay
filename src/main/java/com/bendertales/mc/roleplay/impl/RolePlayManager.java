package com.bendertales.mc.roleplay.impl;

import com.bendertales.mc.roleplay.config.*;
import com.bendertales.mc.roleplay.data.Character;
import com.bendertales.mc.roleplay.data.CharacterReadabilityMode;
import com.bendertales.mc.roleplay.data.CharacterVisibilityMode;
import com.bendertales.mc.roleplay.data.PlayerConfiguration;
import com.bendertales.mc.roleplay.impl.helper.Cypher;
import com.bendertales.mc.roleplay.impl.vo.RolePlayException;
import net.minecraft.server.network.ServerPlayerEntity;


public class RolePlayManager {

	private final ModSettingsRepository      modSettingsRepository      = new ModSettingsRepository();
	private final PlayerConfigurationManager playerConfigurationManager = new PlayerConfigurationManager();
	private final NewsstandManager newsstandManager = new NewsstandManager();

	private ModProperties config;
	private Cypher rpCypher = new Cypher("NOTINITIALIZED");

	public int getDiceDistance() {
		return config.getRollDice().getDistance();
	}

	public int getWhisperDistance() {
		return config.getChannels().getWhisper().getDistance();
	}

	public int getSayDistance() {
		return config.getChannels().getSay().getDistance();
	}

	public int getYellDistance() {
		return config.getChannels().getYell().getDistance();
	}

	public void reload() {
		playerConfigurationManager.clearSettings();
		load();
	}

	public void load() {
		config = modSettingsRepository.getConfig();
		newsstandManager.load();
		rpCypher = new Cypher(config.getComprehensionKey());
	}

	public PlayerConfiguration getOrCreatePlayerConfiguration(ServerPlayerEntity player) {
		return playerConfigurationManager.getOrCreatePlayerConfiguration(player);
	}

	public void createCharacter(ServerPlayerEntity owner) {
		var config = getOrCreatePlayerConfiguration(owner);

		var character = new Character();
		character.setDefaultVisibility(CharacterVisibilityMode.CAN_SEE);
		character.setDefaultReadability(CharacterReadabilityMode.CAN_UNDERSTAND);

		var characters = config.getCharacters();
		characters.add(character);

		playerConfigurationManager.savePlayerConfiguration(owner, config);
	}

	public Character selectCharacter(ServerPlayerEntity player, int characterIndex) throws RolePlayException {
		var config = getOrCreatePlayerConfiguration(player);
		config.selectCharacterIndex(characterIndex);

		playerConfigurationManager.savePlayerConfiguration(player, config);

		return config.getSelectedCharacter();
	}

	public void renameCharacter(ServerPlayerEntity player, int characterIndex, String newName)
	throws RolePlayException {
		var config = getOrCreatePlayerConfiguration(player);
		config.renameCharacter(characterIndex, newName);
		playerConfigurationManager.savePlayerConfiguration(player, config);
	}

	public void deleteCharacter(ServerPlayerEntity player, int characterIndex) throws RolePlayException {
		var config = getOrCreatePlayerConfiguration(player);
		config.removeCharacter(characterIndex);
		playerConfigurationManager.savePlayerConfiguration(player, config);
	}

	public void setDefaultReadability(ServerPlayerEntity player, int characterIndex, CharacterReadabilityMode mode)
	throws RolePlayException {
		var config = getOrCreatePlayerConfiguration(player);
		var character = config.getCharacter(characterIndex);
		character.setDefaultReadability(mode);
		playerConfigurationManager.savePlayerConfiguration(player, config);
	}

	public void clearPlayerReadability(ServerPlayerEntity player, int characterIndex,
	                                   ServerPlayerEntity otherPlayer) throws RolePlayException {

		var config = getOrCreatePlayerConfiguration(player);
		var character = config.getCharacter(characterIndex);
		character.clearPlayerReadability(otherPlayer);
		playerConfigurationManager.savePlayerConfiguration(player, config);
	}

	public void setPlayerReadability(ServerPlayerEntity player, int characterIndex, ServerPlayerEntity otherPlayer,
	                                 CharacterReadabilityMode mode) throws RolePlayException {
		var config = getOrCreatePlayerConfiguration(player);
		var character = config.getCharacter(characterIndex);
		character.setPlayerReadability(otherPlayer, mode);
		playerConfigurationManager.savePlayerConfiguration(player, config);
	}

	public void setDefaultVisibility(ServerPlayerEntity player, int characterIndex, CharacterVisibilityMode mode)
	throws RolePlayException {
		var config = getOrCreatePlayerConfiguration(player);
		var character = config.getCharacter(characterIndex);
		character.setDefaultVisibility(mode);
		playerConfigurationManager.savePlayerConfiguration(player, config);
	}

	public void clearPlayerVisibility(ServerPlayerEntity player, int characterIndex, ServerPlayerEntity otherPlayer)
	throws RolePlayException {
		var config = getOrCreatePlayerConfiguration(player);
		var character = config.getCharacter(characterIndex);
		character.clearPlayerVisibility(otherPlayer);
		playerConfigurationManager.savePlayerConfiguration(player, config);
	}

	public void setPlayerVisibility(ServerPlayerEntity player, int characterIndex, ServerPlayerEntity otherPlayer,
	                                CharacterVisibilityMode mode) throws RolePlayException {
		var config = getOrCreatePlayerConfiguration(player);
		var character = config.getCharacter(characterIndex);
		character.setPlayerVisibility(otherPlayer, mode);
		playerConfigurationManager.savePlayerConfiguration(player, config);
	}

	public String encryptText(String text) {
		return rpCypher.encrypt(text);
	}

	public String decryptText(String text) {
		return rpCypher.decrypt(text);
	}
}
