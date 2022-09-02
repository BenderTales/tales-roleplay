package com.bendertales.mc.roleplay.commands.subcommands.character;

import com.bendertales.mc.roleplay.impl.RolePlayManager;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.EntitySelector;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;


public class CmdCharacterList {

	private final RolePlayManager rolePlayManager;

	public CmdCharacterList(RolePlayManager rolePlayManager) {
		this.rolePlayManager = rolePlayManager;
	}

	public int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		var cmdSource = context.getSource();
		var playerSelector = context.getArgument("player", EntitySelector.class);
		var player = playerSelector.getPlayer(cmdSource);

		var playerConfiguration = rolePlayManager.getOrCreatePlayerConfiguration(player);
		var characters = playerConfiguration.getCharacters();
		if (characters.isEmpty()) {
			cmdSource.sendFeedback(Text.of("This player has no characters"), false);
		}
		else {
			for (int i = 0 ; i < characters.size() ; i++) {
				var character = characters.get(i);
				cmdSource.sendFeedback(Text.of((i+1) + ". " + character.formatName()), false);
			}
		}
		return 0;
	}
}
