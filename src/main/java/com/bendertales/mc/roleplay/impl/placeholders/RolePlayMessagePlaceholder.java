package com.bendertales.mc.roleplay.impl.placeholders;

import com.bendertales.mc.chatapi.api.PerRecipientPlaceholderFormatter;
import com.bendertales.mc.chatapi.api.PlaceholderFormatter;
import com.bendertales.mc.chatapi.api.PlaceholderHandler;
import com.bendertales.mc.roleplay.RolePlayConstants;
import com.bendertales.mc.roleplay.impl.RolePlayManager;
import net.minecraft.util.Identifier;


public class RolePlayMessagePlaceholder implements PlaceholderHandler {

	private static final String PLACEHOLDER = "%RP_MESSAGE%";

	private final RolePlayManager rolePlayManager;

	public RolePlayMessagePlaceholder(RolePlayManager rolePlayManager) {
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
		return null;
	}

	@Override
	public PerRecipientPlaceholderFormatter getPerRecipientPlaceholderFormatter() {
		//TODO Crypt the message depending the hearing capability of the character
		return (message, recipient) -> message.content();
	}

	@Override
	public Identifier getId() {
		return RolePlayConstants.Ids.Placeholders.RP_MESSAGE;
	}
}
