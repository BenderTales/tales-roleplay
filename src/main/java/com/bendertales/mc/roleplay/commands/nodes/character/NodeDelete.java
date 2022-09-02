package com.bendertales.mc.roleplay.commands.nodes.character;

import java.util.List;

import com.bendertales.mc.roleplay.commands.subcommands.character.CmdCharacterDelete;
import com.bendertales.mc.roleplay.impl.RolePlayManager;
import com.bendertales.mc.talesservercommon.commands.CommandNodeRequirements;
import com.bendertales.mc.talesservercommon.commands.TalesCommandNode;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.ServerCommandSource;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;


public class NodeDelete implements TalesCommandNode {

	private final List<String> permissions = List.of("roleplay.commands.*", "roleplay.commands.character.delete");
	private final CommandNodeRequirements requirements = CommandNodeRequirements.of(OP_SENIOR, permissions);

	private final CmdCharacterDelete cmdDelete;

	public NodeDelete(RolePlayManager rolePlayManager) {
		cmdDelete = new CmdCharacterDelete(rolePlayManager);
	}

	@Override
	public LiteralArgumentBuilder<ServerCommandSource> asBrigadierNode() {
		return literal("delete")
		       .requires(getRequirements().asPredicate())
		       .then(argument("player", EntityArgumentType.player())
	                .then(argument("characterIndex", IntegerArgumentType.integer(0))
	                    .executes(cmdDelete::run)));
	}

	@Override
	public CommandNodeRequirements getRequirements() {
		return requirements;
	}
}
