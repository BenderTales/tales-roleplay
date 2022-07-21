package com.bendertales.mc.roleplay.commands.character;

import java.util.Collection;
import java.util.List;

import com.bendertales.mc.roleplay.commands.ModCommand;
import com.bendertales.mc.roleplay.impl.RolePlayManager;
import com.mojang.brigadier.CommandDispatcher;
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


public class CmdCharacterList implements ModCommand {

	private final RolePlayManager rolePlayManager;

	public CmdCharacterList(RolePlayManager rolePlayManager) {
		this.rolePlayManager = rolePlayManager;
	}

	@Override
	public void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess,
	                     CommandManager.RegistrationEnvironment environment) {
		dispatcher.register(
			literal("rp").then(literal("character").then(literal("list")
                .requires(getRequirements())
                .then(argument("player", EntityArgumentType.player())
                    .executes(this))
			))
		);
	}

	@Override
	public Collection<String> getRequiredPermissions() {
		return List.of("roleplay.commands.*", "roleplay.commands.character.list");
	}

	@Override
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
