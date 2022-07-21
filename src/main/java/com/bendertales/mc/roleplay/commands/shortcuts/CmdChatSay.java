package com.bendertales.mc.roleplay.commands.shortcuts;

import java.util.Collection;

import com.bendertales.mc.chatapi.api.Messenger;
import com.bendertales.mc.roleplay.RolePlayConstants;
import com.bendertales.mc.roleplay.impl.channels.SayChannel;
import net.minecraft.util.Identifier;


public class CmdChatSay extends ShortcutModCommand{

	public CmdChatSay(Messenger messageSender) {
		super(messageSender);
	}

	@Override
	public Collection<String> getRequiredPermissions() {
		return SayChannel.PERMISSION;
	}

	@Override
	protected String getCommandRoot() {
		return "say";
	}

	@Override
	protected Identifier getChannelId() {
		return RolePlayConstants.Ids.Channels.SAY;
	}

}
