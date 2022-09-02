package com.bendertales.mc.roleplay.commands.nodes.root;

import java.util.List;

import com.bendertales.mc.chatapi.api.Messenger;
import com.bendertales.mc.roleplay.commands.subcommands.ShortcutCmd;
import com.bendertales.mc.talesservercommon.commands.CommandNodeRequirements;
import com.bendertales.mc.talesservercommon.commands.TalesCommandNode;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.Identifier;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;


public class ShortcutNode implements TalesCommandNode {

	private final String name;
	private final ShortcutCmd shortcutCmd;
	private final CommandNodeRequirements requirements;

	public ShortcutNode(String name, Messenger messenger, Identifier channelId, List<String> permissions) {
		this.name = name;
		shortcutCmd = new ShortcutCmd(messenger, channelId);
		requirements = CommandNodeRequirements.of(OP_JUNIOR, permissions);
	}

	@Override
	public LiteralArgumentBuilder<ServerCommandSource> asBrigadierNode() {
		return literal(name)
		       .requires(getRequirements().asPredicate())
		       .then(argument("message", StringArgumentType.greedyString())
	                .executes(shortcutCmd::run));
	}

	@Override
	public CommandNodeRequirements getRequirements() {
		return requirements;
	}
}
