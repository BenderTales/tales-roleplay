package com.bendertales.mc.roleplay.commands.shortcuts;

import java.util.Collection;

import com.bendertales.mc.chatapi.api.MessageSender;
import com.bendertales.mc.roleplay.RolePlayConstants;
import com.bendertales.mc.roleplay.impl.channels.YellChannel;
import net.minecraft.util.Identifier;


public class CmdChatYell extends ShortcutModCommand {

	public CmdChatYell(MessageSender messageSender) {
		super(messageSender);
	}

	@Override
	public Collection<String> getRequiredPermissions() {
		return YellChannel.PERMISSION;
	}

	@Override
	protected String getCommandRoot() {
		return "yell";
	}

	@Override
	protected Identifier getChannelId() {
		return RolePlayConstants.Ids.Channels.YELL;
	}
}
