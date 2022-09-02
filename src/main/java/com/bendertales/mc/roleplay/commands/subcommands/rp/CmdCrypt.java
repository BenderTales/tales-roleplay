package com.bendertales.mc.roleplay.commands.subcommands.rp;

import com.bendertales.mc.roleplay.impl.RolePlayManager;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import static com.mojang.brigadier.Command.SINGLE_SUCCESS;


public class CmdCrypt {

	private final RolePlayManager rolePlayManager;

	public CmdCrypt(RolePlayManager rolePlayManager) {
		this.rolePlayManager = rolePlayManager;
	}

	public int encrypt(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
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
