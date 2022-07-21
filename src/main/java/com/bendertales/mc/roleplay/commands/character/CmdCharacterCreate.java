package com.bendertales.mc.roleplay.commands.character;

import java.util.Collection;
import java.util.List;

import com.bendertales.mc.roleplay.commands.ModCommand;
import com.bendertales.mc.roleplay.impl.RolePlayManager;
import com.bendertales.mc.roleplay.impl.helper.Perms;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.EntitySelector;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;


public class CmdCharacterCreate implements ModCommand {

	public static final List<String>    PERMISSIONS       = List.of("roleplay.commands.*",
	                                                                "roleplay.commands.character.create");
	public static final List<String>    PERMISSIONS_OTHER = List.of("roleplay.commands.*",
	                                                             "roleplay.commands.character.create.other");

	private final       RolePlayManager rolePlayManager;

	public CmdCharacterCreate(RolePlayManager rolePlayManager) {
		this.rolePlayManager = rolePlayManager;
	}

	@Override
	public void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess,
	                     CommandManager.RegistrationEnvironment environment) {
		dispatcher.register(
			literal("rp").then(literal("character").then(literal("create")
                .requires(getRequirements())
                .executes(this)
                .then(argument("target", EntityArgumentType.player())
                    .executes(this::runOnOther))))
		);
	}

	@Override
	public Collection<String> getRequiredPermissions() {
		return PERMISSIONS;
	}

	@Override
	public int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		var cmdSource = context.getSource();
		var player = cmdSource.getPlayer();
		rolePlayManager.createCharacter(player);
		return SINGLE_SUCCESS;
	}

	public int runOnOther(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		var cmdSource = context.getSource();
		var cmdPlayer = cmdSource.getPlayerOrThrow();

		var playerSelector = context.getArgument("target", EntitySelector.class);
		var target = playerSelector.getPlayer(cmdSource);
		
		if (!cmdPlayer.equals(target)
			&& !(Perms.isOp(cmdPlayer) || Perms.hasAny(cmdPlayer, PERMISSIONS_OTHER))) {
			var msg = Text.literal("You cannot create characters for other players").formatted(Formatting.RED);
			throw new CommandSyntaxException(new SimpleCommandExceptionType(msg), msg);
		}

		rolePlayManager.createCharacter(target);
		return SINGLE_SUCCESS;
	}
}
