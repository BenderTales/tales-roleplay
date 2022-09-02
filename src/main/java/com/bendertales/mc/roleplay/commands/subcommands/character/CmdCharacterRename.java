package com.bendertales.mc.roleplay.commands.subcommands.character;

import com.bendertales.mc.roleplay.impl.RolePlayManager;
import com.bendertales.mc.roleplay.impl.vo.RolePlayException;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.EntitySelector;
import net.minecraft.server.command.ServerCommandSource;

import static com.mojang.brigadier.Command.SINGLE_SUCCESS;


public class CmdCharacterRename {

	private final RolePlayManager rolePlayManager;

	public CmdCharacterRename(RolePlayManager rolePlayManager) {
		this.rolePlayManager = rolePlayManager;
	}

	public int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		var cmdSource = context.getSource();
		var playerSelector = context.getArgument("player", EntitySelector.class);
		var player = playerSelector.getPlayer(cmdSource);
		var characterIndex = context.getArgument("characterIndex", Integer.class);
		var newName = context.getArgument("new-name", String.class);

		try {
			rolePlayManager.renameCharacter(player, characterIndex, newName);
		}
		catch (RolePlayException e) {
			throw e.asCommandException();
		}

		return SINGLE_SUCCESS;
	}
}
