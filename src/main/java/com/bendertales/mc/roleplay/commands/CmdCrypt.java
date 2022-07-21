package com.bendertales.mc.roleplay.commands;

import java.util.Collection;
import java.util.List;

import com.bendertales.mc.roleplay.impl.RolePlayManager;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;


public class CmdCrypt implements ModCommand{

	private static final Collection<String> PERMISSIONS = List.of("roleplay.commands.*", "roleplay.commands.crypt");

	private final RolePlayManager rolePlayManager;

	public CmdCrypt(RolePlayManager rolePlayManager) {
		this.rolePlayManager = rolePlayManager;
	}

	@Override
	public Collection<String> getRequiredPermissions() {
		return PERMISSIONS;
	}

	@Override
	public void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess,
	                     CommandManager.RegistrationEnvironment environment) {

		var requirements = getRequirements();

		dispatcher.register(
			literal("rp")
				.then(literal("encrypt")
			        .requires(requirements)
			            .then(argument("message", StringArgumentType.greedyString())
							.executes(this))
						)
				.then(literal("decrypt")
			        .requires(requirements)
				        .then(argument("message", StringArgumentType.greedyString())
			                .executes(this::decrypt))
				        )
		);
	}

	@Override
	public int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		var cmdSource = context.getSource();
		var message = context.getArgument("message", String.class);

		var result = rolePlayManager.encryptText(message);
		cmdSource.sendFeedback(Text.literal(result), false);
		return SINGLE_SUCCESS;
	}

	public int decrypt(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		var cmdSource = context.getSource();
		var message = context.getArgument("message", String.class);

		var result = rolePlayManager.decryptText(message);
		cmdSource.sendFeedback(Text.literal(result), false);
		return SINGLE_SUCCESS;
	}


}
