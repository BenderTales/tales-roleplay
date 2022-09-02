package com.bendertales.mc.roleplay.commands.subcommands.character;

import com.bendertales.mc.roleplay.data.CharacterReadabilityMode;
import com.bendertales.mc.roleplay.impl.RolePlayManager;
import com.bendertales.mc.roleplay.impl.vo.RolePlayException;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.EntitySelector;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import static com.mojang.brigadier.Command.SINGLE_SUCCESS;


public class CmdCharacterReadability {

	private final RolePlayManager rolePlayManager;

	public CmdCharacterReadability(RolePlayManager rolePlayManager) {
		this.rolePlayManager = rolePlayManager;
	}

	public int playerDefault(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		var cmdSource = context.getSource();
		var playerSelector = context.getArgument("player", EntitySelector.class);
		var characterIndex = context.getArgument("characterIndex", Integer.class);
		var player = playerSelector.getPlayer(cmdSource);
		var mode = context.getArgument("mode", CharacterReadabilityMode.class);

		try {
			rolePlayManager.setDefaultReadability(player, characterIndex, mode);
			cmdSource.sendFeedback(Text.of("New default readability set to " + mode), true);
		}
		catch (RolePlayException e) {
			throw e.asCommandException();
		}

		return SINGLE_SUCCESS;
	}

	public int clearPlayer(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		var cmdSource = context.getSource();
		var playerSelector = context.getArgument("player", EntitySelector.class);
		var characterIndex = context.getArgument("characterIndex", Integer.class);
		var player = playerSelector.getPlayer(cmdSource);
		var otherPlayerSelector = context.getArgument("other-player", EntitySelector.class);
		var otherPlayer = otherPlayerSelector.getPlayer(cmdSource);

		try {
			rolePlayManager.clearPlayerReadability(player, characterIndex, otherPlayer);
			cmdSource.sendFeedback(Text.of("Player readability clear"), true);
		}
		catch (RolePlayException e) {
			throw e.asCommandException();
		}

		return SINGLE_SUCCESS;
	}

	public int setPlayer(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		var cmdSource = context.getSource();
		var playerSelector = context.getArgument("player", EntitySelector.class);
		var characterIndex = context.getArgument("characterIndex", Integer.class);
		var player = playerSelector.getPlayer(cmdSource);
		var otherPlayerSelector = context.getArgument("other-player", EntitySelector.class);
		var otherPlayer = otherPlayerSelector.getPlayer(cmdSource);
		var mode = context.getArgument("mode", CharacterReadabilityMode.class);

		try {
			rolePlayManager.setPlayerReadability(player, characterIndex, otherPlayer, mode);
			cmdSource.sendFeedback(Text.of("Player readability set to " + mode), true);
		}
		catch (RolePlayException e) {
			throw e.asCommandException();
		}
		return SINGLE_SUCCESS;
	}
}
