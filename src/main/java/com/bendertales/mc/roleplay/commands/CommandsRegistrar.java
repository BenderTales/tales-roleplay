package com.bendertales.mc.roleplay.commands;

import java.util.Collection;
import java.util.List;

import com.bendertales.mc.roleplay.impl.RolePlayManager;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;


public class CommandsRegistrar {

	public static void registerCommands(RolePlayManager rolePlayManager) {
		var event = CommandRegistrationCallback.EVENT;

		buildCommands(rolePlayManager).forEach(event::register);
	}

	public static Collection<ModCommand> buildCommands(RolePlayManager rolePlayManager) {
		return List.of(
			new CmdRollDice(rolePlayManager)
		);
	}
}
