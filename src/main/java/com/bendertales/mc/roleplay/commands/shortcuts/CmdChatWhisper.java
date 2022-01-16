package com.bendertales.mc.roleplay.commands.shortcuts;

import java.util.Collection;

import com.bendertales.mc.chatapi.api.MessageSender;
import com.bendertales.mc.roleplay.RolePlayConstants;
import com.bendertales.mc.roleplay.impl.channels.WhisperChannel;
import net.minecraft.util.Identifier;


public class CmdChatWhisper extends ShortcutModCommand{

	public CmdChatWhisper(MessageSender messageSender) {
		super(messageSender);
	}

	@Override
	public Collection<String> getRequiredPermissions() {
		return WhisperChannel.PERMISSION;
	}

	@Override
	protected String getCommandRoot() {
		return "whisper";
	}

	@Override
	protected Identifier getChannelId() {
		return RolePlayConstants.Ids.Channels.WHISPER;
	}
}
