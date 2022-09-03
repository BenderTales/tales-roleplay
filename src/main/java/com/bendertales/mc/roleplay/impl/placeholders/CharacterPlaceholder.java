package com.bendertales.mc.roleplay.impl.placeholders;

import com.bendertales.mc.chatapi.api.PlaceholderFormatter;
import com.bendertales.mc.chatapi.api.PlaceholderHandler;
import com.bendertales.mc.roleplay.RolePlayConstants;
import com.bendertales.mc.roleplay.impl.RolePlayManager;
import net.minecraft.util.Identifier;


public class CharacterPlaceholder implements PlaceholderHandler {

	private static final String PLACEHOLDER = "%CHARACTER%";

	private final RolePlayManager rolePlayManager;

	public CharacterPlaceholder(RolePlayManager rolePlayManager) {
		this.rolePlayManager = rolePlayManager;
	}

	@Override
	public String getPlaceholder() {
		return PLACEHOLDER;
	}

	@Override
	public boolean shouldApplyFormat(String format) {
		return format.contains(PLACEHOLDER);
	}

	@Override
	public PlaceholderFormatter getPlaceholderFormatter() {
		return (message) -> {
			var config = rolePlayManager.getOrCreatePlayerConfiguration(message.sender());
			var selectedCharacter = config.getSelectedCharacter();
			if (selectedCharacter == null) {
				return message.sender().getEntityName();
			}
			return selectedCharacter.getName();
		};
	}

	@Override
	public Identifier getId() {
		return RolePlayConstants.Ids.Placeholders.CHARACTER;
	}

}
