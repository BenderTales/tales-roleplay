package com.bendertales.mc.roleplay.commands.subcommands;

import fr.bendertales.mc.channels.api.ChatException;
import fr.bendertales.mc.channels.api.Messenger;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

import static com.mojang.brigadier.Command.SINGLE_SUCCESS;


public class ShortcutCmd {

	private final Messenger messageSender;
	private final Identifier channelId;

	public ShortcutCmd(Messenger messageSender, Identifier channelId) {
		this.messageSender = messageSender;
		this.channelId = channelId;
	}

	public int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		var cmdSource = context.getSource();
		var player = cmdSource.getPlayerOrThrow();
		var message = context.getArgument("message", String.class);

		try {
			messageSender.sendMessage(player, message, channelId);
		}
		catch (ChatException e) {
			var msg = Text.literal(e.getMessage()).formatted(Formatting.RED);
			throw new SimpleCommandExceptionType(msg).create();
		}
		return SINGLE_SUCCESS;
	}
}
