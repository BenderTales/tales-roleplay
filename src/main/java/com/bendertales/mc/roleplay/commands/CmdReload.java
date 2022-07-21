package com.bendertales.mc.roleplay.commands;

import java.util.Collection;
import java.util.List;

import com.bendertales.mc.roleplay.impl.RolePlayManager;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import static net.minecraft.server.command.CommandManager.literal;


public class CmdReload implements ModCommand {

	private static final Collection<String> PERMISSIONS = List.of("roleplay.commands.*", "roleplay.commands.reload");

	private final RolePlayManager rolePlayManager;

	public CmdReload(RolePlayManager rolePlayManager) {
		this.rolePlayManager = rolePlayManager;
	}

	@Override
	public void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess,
	                     CommandManager.RegistrationEnvironment environment) {
		dispatcher.register(
			literal("rp")
				.then(literal("reload")
			        .requires(getRequirements())
			        .executes(this))
		);
	}

	@Override
	public Collection<String> getRequiredPermissions() {
		return PERMISSIONS;
	}

	@Override
	public int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		rolePlayManager.reload();
		context.getSource().sendFeedback(Text.of("Roleplay reloaded"), true);
		return 0;
	}


}
