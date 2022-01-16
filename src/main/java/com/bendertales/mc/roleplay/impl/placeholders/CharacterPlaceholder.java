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
	public int getDefaultPriorityOrder() {
		return 2;
	}

	@Override
	public boolean shouldApplyFormat(String format) {
		return format.contains(PLACEHOLDER);
	}

	@Override
	public PlaceholderFormatter getPlaceholderFormatter() {
		return (line, message) -> line.replace(PLACEHOLDER, message.sender().getEntityName());
	}

	@Override
	public Identifier getId() {
		return RolePlayConstants.Ids.Placeholders.CHARACTER;
	}

}
