package com.bendertales.mc.roleplay.commands.character;

import java.util.Collection;
import java.util.List;

import com.bendertales.mc.roleplay.commands.ModCommand;
import com.bendertales.mc.roleplay.commands.type.VisibilityArgumentType;
import com.bendertales.mc.roleplay.data.CharacterVisibilityMode;
import com.bendertales.mc.roleplay.impl.RolePlayManager;
import com.bendertales.mc.roleplay.impl.vo.RolePlayException;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.EntitySelector;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;


public class CmdCharacterVisibility implements ModCommand {

	private static final Collection<String> PERMISSIONS = List.of("roleplay.commands.*", "roleplay.commands.visibility");

	private final RolePlayManager rolePlayManager;

	public CmdCharacterVisibility(RolePlayManager rolePlayManager) {
		this.rolePlayManager = rolePlayManager;
	}

	@Override
	public void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess,
	                     CommandManager.RegistrationEnvironment environment) {
		var type = new VisibilityArgumentType();
		dispatcher.register(
			literal("rp").then(literal("character").then(literal("visibility")
                .requires(getRequirements())
                .then(argument("player", EntityArgumentType.player())
                    .then(argument("characterIndex", IntegerArgumentType.integer(0))
                        .then(literal("default")
                            .then(argument("mode", type)
                                .executes(this)))
                        .then(argument("other-player", EntityArgumentType.player())
                            .then(literal("clear")
                                .executes(this::clearPlayer))
                            .then(literal("set")
                                .then(argument("mode", type)
                                    .executes(this::setPlayer))))))
			))
		);
	}

	@Override
	public Collection<String> getRequiredPermissions() {
		return PERMISSIONS;
	}

	@Override
	public int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
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
