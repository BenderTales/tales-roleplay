package com.bendertales.mc.roleplay.commands.subcommands.character;

import com.bendertales.mc.roleplay.impl.RolePlayManager;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.EntitySelector;
import net.minecraft.server.command.ServerCommandSource;

import static com.mojang.brigadier.Command.SINGLE_SUCCESS;


public class CmdCharacterCreate {

	private final RolePlayManager rolePlayManager;

	public CmdCharacterCreate(RolePlayManager rolePlayManager) {
		this.rolePlayManager = rolePlayManager;
	}

	public int createForSelf(CommandContext<ServerCommandSource> context) {
		var cmdSource = context.getSource();
		var player = cmdSource.getPlayer();
		rolePlayManager.createCharacter(player);
		return SINGLE_SUCCESS;
	}

	public int createForOther(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		var cmdSource = context.getSource();

		var playerSelector = context.getArgument("target", EntitySelector.class);
		var target = playerSelector.getPlayer(cmdSource);

		rolePlayManager.createCharacter(target);
		return SINGLE_SUCCESS;
	}
}
