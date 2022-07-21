package com.bendertales.mc.roleplay.commands.character;

import java.util.Collection;
import java.util.List;

import com.bendertales.mc.roleplay.commands.ModCommand;
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


public class CmdCharacterPerceptionCheck implements ModCommand {

	private static final Collection<String> PERMISSIONS = List.of("roleplay.commands.*", "roleplay.commands.check.perception");
	private final RolePlayManager rolePlayManager;

	public CmdCharacterPerceptionCheck(RolePlayManager rolePlayManager) {
		this.rolePlayManager = rolePlayManager;
	}

	@Override
	public void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess,
	                     CommandManager.RegistrationEnvironment environment) {
		dispatcher.register(
			literal("rp").then(literal("character").then(literal("check")
                .requires(getRequirements())
                .then(literal("perception")
                    .then(argument("player", EntityArgumentType.player())
                        .then(argument("characterIndex", IntegerArgumentType.integer(0))
                            .then(argument("other-player", EntityArgumentType.player())
                                .executes(this)))))
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
		var otherPlayerSelector = context.getArgument("other-player", EntitySelector.class);
		int characterIndex = context.getArgument("characterIndex", Integer.class);
		var player = playerSelector.getPlayer(cmdSource);
		var otherPlayer = otherPlayerSelector.getPlayer(cmdSource);

		var config = rolePlayManager.getOrCreatePlayerConfiguration(player);
		try {
			var character = config.getCharacter(characterIndex);
			var perception = character.getPlayerPerceptions().get(otherPlayer.getUuid());
			var visibility = perception == null ? null : perception.getVisibility();
			var readability = perception == null ? null : perception.getReadability();
			var msg = "%s's perception of %s: Visibility=%s - Readability=%s".formatted(
					player.getEntityName(), character.formatName(), visibility, readability);
			cmdSource.sendFeedback(Text.of(msg), false);
		}
		catch (RolePlayException e) {
			throw e.asCommandException();
		}

		return SINGLE_SUCCESS;
	}

}
