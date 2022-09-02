package com.bendertales.mc.roleplay.commands.subcommands.rp;

import com.bendertales.mc.roleplay.impl.RolePlayManager;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;


public class CmdReload {

	private final RolePlayManager rolePlayManager;

	public CmdReload(RolePlayManager rolePlayManager) {
		this.rolePlayManager = rolePlayManager;
	}

	public int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		rolePlayManager.reload();
		context.getSource().sendFeedback(Text.of("Roleplay reloaded"), true);
		return 0;
	}
}
