package com.bendertales.mc.roleplay.commands;

import java.util.Collection;
import java.util.List;

import com.bendertales.mc.chatapi.api.MessageSender;
import com.bendertales.mc.roleplay.commands.character.*;
import com.bendertales.mc.roleplay.commands.shortcuts.CmdChatSay;
import com.bendertales.mc.roleplay.commands.shortcuts.CmdChatWhisper;
import com.bendertales.mc.roleplay.commands.shortcuts.CmdChatYell;
import com.bendertales.mc.roleplay.impl.RolePlayManager;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;


public class CommandsRegistrar {

	public static void registerCommands(RolePlayManager rolePlayManager, MessageSender messageSender) {
		var event = CommandRegistrationCallback.EVENT;

		buildCommands(rolePlayManager, messageSender).forEach(event::register);
	}

	public static Collection<ModCommand> buildCommands(RolePlayManager rolePlayManager, MessageSender messageSender) {
		return List.of(
			new CmdReload(rolePlayManager),
			new CmdRollDice(rolePlayManager),
			new CmdChatSay(messageSender),
			new CmdChatWhisper(messageSender),
			new CmdChatYell(messageSender),
			new CmdCharacterCreate(rolePlayManager),
			new CmdCharacterDelete(rolePlayManager),
			new CmdCharacterList(rolePlayManager),
			new CmdCharacterInfo(rolePlayManager),
			new CmdCharacterSelect(rolePlayManager),
			new CmdCharacterRename(rolePlayManager)
		);
	}
}
