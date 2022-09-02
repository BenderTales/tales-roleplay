package com.bendertales.mc.roleplay.commands.subcommands.character;

import com.bendertales.mc.roleplay.impl.RolePlayManager;
import com.bendertales.mc.roleplay.impl.vo.RolePlayException;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.EntitySelector;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import static com.mojang.brigadier.Command.SINGLE_SUCCESS;


public class CmdCharacterInfo {

	private final RolePlayManager rolePlayManager;

	public CmdCharacterInfo(RolePlayManager rolePlayManager) {
		this.rolePlayManager = rolePlayManager;
	}

	public int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		var cmdSource = context.getSource();
		var playerSelector = context.getArgument("player", EntitySelector.class);
		var player = playerSelector.getPlayer(cmdSource);
		var characterIndex = context.getArgument("characterIndex", Integer.class);

		var config = rolePlayManager.getOrCreatePlayerConfiguration(player);
		try {
			var character = config.getCharacter(characterIndex);
			cmdSource.sendFeedback(Text.of("Name: " + character.formatName()), false);
			cmdSource.sendFeedback(Text.of("- Visibility: " + character.getDefaultVisibility()), false);
			cmdSource.sendFeedback(Text.of("- Readability: " + character.getDefaultReadability()), false);
		}
		catch (RolePlayException e) {
			throw e.asCommandException();
		}

		return SINGLE_SUCCESS;
	}
}
