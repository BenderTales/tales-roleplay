package com.bendertales.mc.roleplay.commands.nodes.character;

import java.util.List;

import com.bendertales.mc.roleplay.commands.subcommands.character.CmdCharacterReadability;
import com.bendertales.mc.roleplay.commands.type.ReadabilityArgumentType;
import com.bendertales.mc.roleplay.impl.RolePlayManager;
import com.bendertales.mc.talesservercommon.commands.CommandNodeRequirements;
import com.bendertales.mc.talesservercommon.commands.TalesCommandNode;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.ServerCommandSource;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;


public class NodeReadability implements TalesCommandNode {

	private final List<String> PERMISSIONS = List.of("roleplay.commands.*", "roleplay.commands.readability");
	private final CommandNodeRequirements requirements = CommandNodeRequirements.of(OP_SENIOR, PERMISSIONS);

	private final CmdCharacterReadability cmdReadability;

	public NodeReadability(RolePlayManager rolePlayManager) {
		cmdReadability = new CmdCharacterReadability(rolePlayManager);
	}

	@Override
	public LiteralArgumentBuilder<ServerCommandSource> asBrigadierNode() {
		var type = new ReadabilityArgumentType();

		return literal("readability")
		       .requires(getRequirements().asPredicate())
		       .then(argument("player", EntityArgumentType.player())
                    .then(argument("characterIndex", IntegerArgumentType.integer(0))
	                    .then(literal("default")
                            .then(argument("mode", type)
                                .executes(cmdReadability::playerDefault)))
	                    .then(argument("other-player", EntityArgumentType.player())
                            .then(literal("clear")
                                .executes(cmdReadability::clearPlayer))
                            .then(literal("set")
                                .then(argument("mode", type)
                                    .executes(cmdReadability::setPlayer))))));
	}

	@Override
	public CommandNodeRequirements getRequirements() {
		return requirements;
	}
}
