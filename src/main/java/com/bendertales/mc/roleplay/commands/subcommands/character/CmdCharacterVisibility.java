package com.bendertales.mc.roleplay.commands.subcommands.character;

import com.bendertales.mc.roleplay.data.CharacterVisibilityMode;
import com.bendertales.mc.roleplay.impl.RolePlayManager;
import com.bendertales.mc.roleplay.impl.vo.RolePlayException;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.EntitySelector;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import static com.mojang.brigadier.Command.SINGLE_SUCCESS;


public class CmdCharacterVisibility {

	private final RolePlayManager rolePlayManager;

	public CmdCharacterVisibility(RolePlayManager rolePlayManager) {
		this.rolePlayManager = rolePlayManager;
	}

	public int playerDefault(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		var cmdSource = context.getSource();
		var playerSelector = context.getArgument("player", EntitySelector.class);
		var characterIndex = context.getArgument("characterIndex", Integer.class);
		var player = playerSelector.getPlayer(cmdSource);
		var mode = context.getArgument("mode", CharacterVisibilityMode.class);

		try {
			rolePlayManager.setDefaultVisibility(player, characterIndex, mode);
			cmdSource.sendFeedback(Text.of("New default visibility set to " + mode), true);
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
			rolePlayManager.clearPlayerVisibility(player, characterIndex, otherPlayer);
			cmdSource.sendFeedback(Text.of("Player visibility cleared"), true);
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
		var mode = context.getArgument("mode", CharacterVisibilityMode.class);

		try {
			rolePlayManager.setPlayerVisibility(player, characterIndex, otherPlayer, mode);
			cmdSource.sendFeedback(Text.of("New player visibility set to " + mode), true);
		}
		catch (RolePlayException e) {
			throw e.asCommandException();
		}
		return SINGLE_SUCCESS;
	}
}
